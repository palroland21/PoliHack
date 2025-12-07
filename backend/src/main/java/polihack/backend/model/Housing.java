package polihack.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import polihack.backend.model.enums.AvailabilityStatus;
import polihack.backend.model.enums.HousingFacility;
import polihack.backend.model.enums.HousingType;
import polihack.backend.model.enums.StatusType;

import java.util.Set;

@Entity
@Table(name = "housing")
@NoArgsConstructor
public class Housing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "housing_type", nullable = false)
    private HousingType housingType;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "available_period")
    private String availablePeriod;

    @ElementCollection(targetClass = HousingFacility.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "housing_facilities", joinColumns = @JoinColumn(name = "housing_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "facility")
    private Set<HousingFacility> facilities;

    @Column(name = "additional_notes", length = 1000)
    private String additionalNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rescuer_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Rescuer rescuer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.FREE;

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Set<HousingFacility> getFacilities() {
        return facilities;
    }
    public void setFacilities(Set<HousingFacility> facilities) {
        this.facilities = facilities;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public HousingType getHousingType() { return housingType; }
    public void setHousingType(HousingType housingType) { this.housingType = housingType; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getAvailablePeriod() { return availablePeriod; }
    public void setAvailablePeriod(String availablePeriod) { this.availablePeriod = availablePeriod; }
    public String getAdditionalNotes() { return additionalNotes; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }
    public Rescuer getRescuer() { return rescuer; }
    public void setRescuer(Rescuer rescuer) { this.rescuer = rescuer; }
}