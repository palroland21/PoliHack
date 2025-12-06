package polihack.backend.dto.response;

import lombok.Builder;

@Builder
public class RescuerOfferDTO {
    private Long id;
    private String type;
    private String title;
    private String details;
    private String date;

    public RescuerOfferDTO(Long id, String type, String title, String details, String date) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.details = details;
        this.date = date;
    }

    public RescuerOfferDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
