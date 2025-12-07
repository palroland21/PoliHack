# ResQ (PoliHack) — Crisis Response PWA

ResQ is a **Progressive Web App (PWA)** designed for crisis situations when traditional emergency services may be overloaded or unreachable. It activates **local community resources** by matching people who need help (**Victims**) with those who can provide it (**Rescuers** — individuals or companies), prioritizing **proximity** and **severity**.

> ⚠️ **Disclaimer:** ResQ is not a substitute for official emergency services. If you can reach emergency services, do it first. This platform is meant to help when access is delayed or impossible.

---

## Key Features

### Role-based experience
- **Victim dashboard**: create and track help requests.
- **Rescuer dashboard**: browse, filter, and accept nearby requests.
- **Authentication & authorization** for both roles (separate access paths and permissions).

### Map + GPS (Leaflet)
- Automatic location via **GPS**
- Manual fallback: pick a point on the map / enter a location
- Requests are visualized on a **Leaflet map** for fast decision-making.

### AI-powered medical triage (Gemini)
When a Victim selects **Medical**, they describe symptoms in plain language. The backend calls a **Gemini triage assistant** that returns a **severity score** (1/2/3) used for prioritization.

> Tip: Keep the mapping consistent across backend + UI (e.g., 1=Low, 2=Medium, 3=Critical).

### Requests by category
Victims can request:
- **Medical**
- **Resources**, such as:
  - Water / Food
  - Transport (evacuation)
  - Shelter (housing)

### Payments (Stripe)
- **One-time donations**
- **Subscriptions** (support/sustainability)
- Webhooks to validate payment events and persist subscription/donation status.

---

## How It Works (User Journey)

### Victim flow
1. Choose **“I’m a Victim”**
2. Provide location (GPS or manual)
3. Select category: **Medical** or **Resources**
4. If **Medical**: describe symptoms → AI returns severity (1/2/3)
5. Submit request → it becomes visible to Rescuers nearby (prioritized by distance + urgency)

### Rescuer flow
1. Choose **“I’m a Rescuer”** → sign up / sign in
2. See requests on **map + list**
3. Filter by what you can provide (medical / transport / shelter / resources)
4. Accept a request and proceed

---

## Tech Stack (High-Level)

- **Frontend:** Vue (PWA UI, dashboards, Leaflet map, Stripe checkout)
- **Backend:** Java (API, auth, Gemini triage, Stripe webhooks,PostgreSQL as database)
- **Folders:**
  - `frontend/`
  - `backend/`

---

## Getting Started (Local Development)

> Commands may vary depending on your exact setup. The repository is split into `frontend/` and `backend/`.

### 1) Clone
```bash
git clone https://github.com/palroland21/PoliHack.git
cd PoliHack
