package polihack.backend.dto.request;

public class MedicalVerificationRequest {

    private String fullName;
    private String cuim; //codul de medic unic
    private String specialization;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCuim() {
        return cuim;
    }

    public void setCuim(String cuim) {
        this.cuim = cuim;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
