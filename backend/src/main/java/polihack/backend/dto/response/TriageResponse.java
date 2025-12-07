package polihack.backend.dto.response;

public class TriageResponse {
    private int priority;
    private String category;
    private String advice;
    private String summary;

    public TriageResponse() {
    }

    public TriageResponse(int priority, String category, String advice, String summary) {
        this.priority = priority;
        this.category = category;
        this.advice = advice;
        this.summary = summary;
    }

    public TriageResponse(String category, String advice, String summary) {
        this.category = category;
        this.advice = advice;
        this.summary = summary;
        // set priority based on category
        if ("CRITICAL".equalsIgnoreCase(category) || "Urgent".equalsIgnoreCase(category) || "Severe".equalsIgnoreCase(category) || "1".equals(category)) {
            this.priority = 1;
        } else if ("URGENT".equalsIgnoreCase(category) || "Moderate".equalsIgnoreCase(category) || "2".equals(category)) {
            this.priority = 2;
        } else {
            this.priority = 3;
        }
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
