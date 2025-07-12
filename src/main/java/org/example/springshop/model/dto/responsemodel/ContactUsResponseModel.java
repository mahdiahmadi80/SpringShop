package org.example.springshop.model.dto.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.ContactUs;

@Getter
@NoArgsConstructor
public class ContactUsResponseModel {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("message")
    private String message;
    @JsonProperty("showed")
    private Boolean showed;
    @JsonProperty("user_id")
    private String userName;

    @Builder
    public ContactUsResponseModel(ContactUs contactUs) {
        this.id = contactUs.getId();
        this.subject = contactUs.getSubject();
        this.message = contactUs.getMessage();
        this.showed = contactUs.isShowed();
        this.userName = contactUs.getUser().getName();
    }
}
