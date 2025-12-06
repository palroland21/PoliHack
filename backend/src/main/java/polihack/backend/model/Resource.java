package polihack.backend.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import polihack.backend.model.enums.AvailabilityStatus;
import polihack.backend.model.enums.ResourceType;

import java.util.Set;

@Entity
@Table(name = "resources")
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(targetClass = ResourceType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "resource_types", joinColumns = @JoinColumn(name = "resource_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private Set<ResourceType> resourceTypes;

    @Column(name = "quantity", nullable = false)
    private String quantity;

    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;

    @Column(name = "additional_details", length = 1000)
    private String additionalDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rescuer_id", nullable = false)
    private Rescuer rescuer;

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Set<ResourceType> getResourceTypes() { return resourceTypes; }
    public void setResourceTypes(Set<ResourceType> resourceTypes) { this.resourceTypes = resourceTypes; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getAdditionalDetails() { return additionalDetails; }
    public void setAdditionalDetails(String additionalDetails) { this.additionalDetails = additionalDetails; }

    public Rescuer getRescuer() { return rescuer; }
    public void setRescuer(Rescuer rescuer) { this.rescuer = rescuer; }
}