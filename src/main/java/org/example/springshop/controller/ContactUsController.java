package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.ContactUsRequestModel;
import org.example.springshop.model.dto.responsemodel.ContactUsResponseModel;
import org.example.springshop.service.ContactUsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/contactus")
public class ContactUsController {
    private final ContactUsService contactUsService;
    public ContactUsController(ContactUsService contactUsService) {
        this.contactUsService = contactUsService;
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ContactUsResponseModel> listContact() {
        return contactUsService.listContact();
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ContactUsResponseModel addContact(@RequestBody ContactUsRequestModel contactUsRequestModel) {
        return contactUsService.addContactUs(contactUsRequestModel);
    }
    @RequestMapping(value = "/unread", method = RequestMethod.GET)
    public List<ContactUsResponseModel> showUnreadContact() {
        return contactUsService.showUnreadContact();
    }
    @RequestMapping(value = "/read/{id}",method = RequestMethod.GET)
    public ContactUsResponseModel readContact(@PathVariable Long id){
        return contactUsService.showContact(id);
    }
    @DeleteMapping(value = "/delete/{id}")
    public String deleteContact(@PathVariable Long id){
        return contactUsService.deleteContact(id);
    }
}
