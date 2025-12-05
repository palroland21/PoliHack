package polihack.backend.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import polihack.backend.model.enums.AssistanceType;

import java.util.List;

@Entity
@Table(name="RESCUER")
@NoArgsConstructor
public class Rescuer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "pid", nullable = false, unique = true, length = 13)
    private String PID;

    @Column(name = "telephone", nullable = false, unique = true, length = 10)
    private String telephone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(targetClass = AssistanceType.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "rescuer_assistance_types",
            joinColumns = @JoinColumn(name = "rescuer_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "assistance_type")
    private List<AssistanceType> assistanceTypeList;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AssistanceType> getAssistanceTypeList() {
        return assistanceTypeList;
    }

    public void setAssistanceTypeList(List<AssistanceType> assistanceTypeList) {
        this.assistanceTypeList = assistanceTypeList;
    }
}
