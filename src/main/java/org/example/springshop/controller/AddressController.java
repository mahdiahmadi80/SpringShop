package org.example.springshop.controller;

import org.example.springshop.model.dto.responsemodel.AddressResponseModel;
import org.example.springshop.service.AddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/address")
public class AddressController {
    final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "/list")
    public List<AddressResponseModel> listAddress() {
        return addressService.listAddress();
    }
}
