package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springshop.model.dto.requestmodel.ContactUsRequestModel;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_CONTACTUS")
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "SUBJECT")
    private String subject;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "SHOWED")
    private boolean showed;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder(builderClassName = "ContactUsClass", builderMethodName = "contactUsBuilder")
    public ContactUs(ContactUsRequestModel contactUsRequestModel, User user) {
        this.id = contactUsRequestModel.getId();
        this.subject = contactUsRequestModel.getSubject();
        this.message = contactUsRequestModel.getMessage();
        this.showed = false;
        this.user = user;
    }
}
