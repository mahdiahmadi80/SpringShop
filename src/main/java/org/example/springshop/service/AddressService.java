package org.example.springshop.service;

import org.example.springshop.model.dto.responsemodel.AddressResponseModel;
import org.example.springshop.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressResponseModel> listAddress() {
        List<AddressResponseModel> addressResponseModels = new ArrayList<>();
        addressRepository.findAll().forEach(address -> {
            AddressResponseModel addressResponseModel = AddressResponseModel.builder().address(address).build();
            addressResponseModels.add(addressResponseModel);
        });
        return addressResponseModels;
    }
}
