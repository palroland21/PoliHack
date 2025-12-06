package polihack.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CompanyRegisterResponse {
    private Long id;
    private String companyName;
    private String cui;
    private String registrationNumber;
    private String headquartersAddress;
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String username;

    public CompanyRegisterResponse(Long id, String companyName, String cui, String registrationNumber, String headquartersAddress, String contactPerson, String phoneNumber, String email, String username) {
        this.id = id;
        this.companyName = companyName;
        this.cui = cui;
        this.registrationNumber = registrationNumber;
        this.headquartersAddress = headquartersAddress;
        this.contactPerson = contactPerson;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getHeadquartersAddress() {
        return headquartersAddress;
    }

    public void setHeadquartersAddress(String headquartersAddress) {
        this.headquartersAddress = headquartersAddress;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}