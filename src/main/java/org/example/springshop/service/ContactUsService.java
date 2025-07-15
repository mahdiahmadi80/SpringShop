package org.example.springshop.service;

import org.example.springshop.exception.contactusException.MessageNotFoundException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.ContactUs;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.ContactUsRequestModel;
import org.example.springshop.model.dto.responsemodel.ContactUsResponseModel;
import org.example.springshop.repository.ContactUsRepository;
import org.example.springshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactUsService {
    private final ContactUsRepository contactUsRepository;
    private final UserRepository userRepository;

    public ContactUsService(ContactUsRepository contactUsRepository, UserRepository userRepository) {
        this.contactUsRepository = contactUsRepository;
        this.userRepository = userRepository;
    }

    public ContactUsResponseModel addContactUs(ContactUsRequestModel contactUsRequestModel) {
        User user = userRepository.findById(contactUsRequestModel.getUser()).orElseThrow(()->new UserNotFoundException("user not found"));
        ContactUs contactUs = ContactUs.contactUsBuilder().contactUsRequestModel(contactUsRequestModel).user(user).build();

        contactUsRepository.save(contactUs);
        return ContactUsResponseModel.builder().contactUs(contactUs).build();
    }

    public List<ContactUsResponseModel> listContact() {
        List<ContactUsResponseModel> contactUsResponseModels = new ArrayList<>();
        contactUsRepository.findAll().forEach(contact -> {
            ContactUsResponseModel contactUsResponseModel = ContactUsResponseModel.builder().contactUs(contact).build();
            contactUsResponseModels.add(contactUsResponseModel);
        });
        return contactUsResponseModels;
    }


    public ContactUsResponseModel showContact(Long id) {
        ContactUs contactUs = contactUsRepository.findById(id).orElseThrow(()->new MessageNotFoundException("this message not found"));
        contactUs.setShowed(true);
        contactUsRepository.save(contactUs);
        return ContactUsResponseModel.builder().contactUs(contactUs).build();

    }

    public List<ContactUsResponseModel> showUnreadContact() {
        List<ContactUsResponseModel> contactUsResponseModels = new ArrayList<>();
        contactUsRepository.findByShowed().forEach(contact -> {
            ContactUsResponseModel contactUsResponseModel = ContactUsResponseModel.builder().contactUs(contact).build();
            contactUsResponseModels.add(contactUsResponseModel);
        });
        return contactUsResponseModels;
    }

    public String deleteContact(Long id) {
        contactUsRepository.deleteById(id);
        return "message deleted";
    }
}
