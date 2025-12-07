package polihack.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import polihack.backend.dto.request.TriageRequest;
import polihack.backend.dto.response.TriageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AiTriageService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-lite:generateContent?key=";

    public AiTriageService(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public TriageResponse analyzeSymptoms(TriageRequest request) {
        String promptText = String.format("""
    ROLE: ACT AS A HIGH-LEVEL EMERGENCY CLINICAL DECISION SUPPORT SYSTEM (CDSS).
    YOUR GOAL: TRIAGE PATIENTS WITH SURGICAL PRECISION BASED ON SYMPTOMS, VITALS, AND HIDDEN CONTEXT.
    MODE: STRICT ALGORITHMIC ASSESSMENT. NO EMPATHY. NO HALLUCINATIONS.

    === PATIENT DATA ===
    COMPLAINT: "%s"
    (Language: Mixed Romanian/English/Hungarian/Slang. Detect and Translate internally.)

    === STEP 1: LINGUISTIC DECODING & INTENT ANALYSIS ===
    Before medical analysis, you MUST parse the "Voice of the Customer":
    1. CULTURAL SLANG INTERPRETATION:
       - "Mor aci pe scaun", "I am dying", "Mindjárt meghalok", "Nu mai pot" -> FLAG AS: Extreme Subjective Distress (Potential Severity).
       - "Crap de...", "Szétrobban a...", "My head is splitting" -> FLAG AS: Severe Pain (8-10/10).
       - "Inima imi sare", "Heart pounding" -> FLAG AS: Palpitations/Arrhythmia.
       - "Am racit", "Megfáztam" -> FLAG AS: Common Cold/Viral (Likely Mild).
    2. TYPO CORRECTION:
       - Fix phonetic errors (e.g., "duree"->durere, "hedaik"->headache, "febr"->fever). 
       - If input is incoherent due to panic (e.g., "hlep can't breth"), assume CRITICAL RESPIRATORY DISTRESS.

    === STEP 2: TRIAGE PROTOCOL (STRICT HIERARCHY) ===
    Evaluate sequentially. STOP at the first match.

    🔥🔥 LEVEL 1: CRITICAL / RESUSCITATION (PRIORITY 1)
    ACTION: CALL 112 / AMBULANCE IMMEDIATELY. TIME CRITICAL.
    TRIGGERS:
    - CARDIAC: Chest pain described as "crushing", "heavy", "radiating to jaw/left arm", "elephant sitting on chest".
    - STROKE (FAST): Facial drooping, Arm weakness, Slurred speech, sudden "thunderclap" headache, vision loss in one eye.
    - RESPIRATORY: Gasping, cyanosis (blue lips), "can't speak sentences", choking, stridor.
    - TRAUMA: Massive spurting bleeding, amputation, deep penetrating wounds (knife/gun) to trunk/neck.
    - ALLERGY: Anaphylaxis signs (swollen tongue/lips, difficulty breathing after food/sting).
    - NEURO: Active seizure, unconsciousness (current or <30 mins ago), altered mental state (confusion) with fever.

    ⚠️ LEVEL 2: EMERGENCY / URGENT (PRIORITY 2)
    ACTION: ER VISIT REQUIRED WITHIN 60-120 MINUTES.
    TRIGGERS:
    - SLANG RULE (DISTRESS): Patient says "dying"/"mor aci" BUT has no specific Priority 1 symptoms -> Assign Priority 2 (Needs rapid assessment).
    - ABDOMINAL: Severe RLQ pain (McBurney point - Appendicitis), rigid abdomen, vomiting blood or "coffee grounds".
    - FEVER: > 40.0°C (Adults), > 38.0°C (Infants < 3mo), or any fever with stiff neck/photophobia (Meningitis risk).
    - GYNECOLOGICAL: Severe abdominal pain in female 14-45y (Ectopic pregnancy risk), heavy vaginal bleeding.
    - OTHER: Kidney stone pain (loin to groin), testicular torsion (sudden severe pain), chemical burns to eye.

    ✅ LEVEL 3: NON-URGENT / PRIMARY CARE (PRIORITY 3)
    ACTION: GP / PHARMACY / HOME CARE.
    TRIGGERS:
    - GENERAL: Flu-like symptoms, cough (productive/dry) without breathlessness, sore throat, sinus pain.
    - PAIN: Chronic back pain, tension headache (band-like), mild sprains (can walk on it).
    - GI: Diarrhea (no blood), nausea, mild constipation, heartburn/GERD.
    - DERMA: Localized rash, insect bites (no systemic reaction), minor cuts/abrasions.
    - PSYCH: Anxiety, panic attack (hyperventilation) - ONLY IF patient has known history.

    === STEP 3: LOGIC TRAPS & EXCLUSIONS (DO NOT FAIL) ===
    1. THE "HEADACHE" TRAP:
       - Complaint: "Headache" -> DEFAULT: Priority 3.
       - UPGRADE TO P1 IF: "Worst headache of life", "Sudden onset", "Neck stiffness", "Double vision".
    2. THE "CHEST PAIN" TRAP (AGE CONTEXT):
       - Complaint: "Chest pain" -> DEFAULT: Priority 1 (Safety first).
       - DOWNGRADE TO P3 ONLY IF: Age < 30 AND pain is "stabbing" AND "reproducible by touch/movement" (Costochondritis).
    3. THE "VAGUE INPUT" RULE:
       - Complaint: "Raul", "Sick", "Fáj", "Bad" (No context).
       - ACTION: Assign Priority 3. Add Advice: "Symptoms are too vague. Monitor closely. If worse, see doctor."

    === OUTPUT FORMAT (JSON ONLY) ===
    Return raw JSON. No markdown. No comments.
    {
      "priority": (Integer: 1, 2, or 3),
      "category": (String: "CRITICAL", "URGENT", "MILD"),
      "advice": (String: Clear, professional medical advice in English. Max 2 sentences.),
      "summary": (String: Clinical summary including interpreted slang. E.g: "Patient reports severe distress ('mor aci'), suggestive of acute abdomen.")
    }
    """, request.getDescription());

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", promptText)
                        ))
                )
        );

        try {
            String responseJson = webClient.post()
                    .uri(GEMINI_URL + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return parseGeminiResponse(responseJson);

        } catch (Exception e) {
            e.printStackTrace();
            return new TriageResponse(3, "MILD", "Could not reach AI service.", "Technical error: " + e.getMessage());
        }
    }

    private TriageResponse parseGeminiResponse(String rawJson) {
        try {
            Map<String, Object> map = objectMapper.readValue(rawJson, Map.class);

            List<Map<String, Object>> candidates = (List<Map<String, Object>>) map.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            String textResponse = (String) parts.get(0).get("text");

            textResponse = textResponse.replace("```json", "").replace("```", "").trim();

            return objectMapper.readValue(textResponse, TriageResponse.class);

        } catch (Exception e) {
            System.out.println("JSON Parsing Error: " + rawJson);
            e.printStackTrace();
            return new TriageResponse(3, "MILD", "AI response was invalid.", "Please try again.");
        }
    }
}