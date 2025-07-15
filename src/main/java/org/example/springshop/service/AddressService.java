package org.example.springshop.service;

import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.Address;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.AddressRequestModel;
import org.example.springshop.model.dto.responsemodel.AddressResponseModel;
import org.example.springshop.repository.AddressRepository;
import org.example.springshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public List<AddressResponseModel> listAddress() {
        List<AddressResponseModel> addressResponseModels = new ArrayList<>();
        addressRepository.findAll().forEach(address -> {
            AddressResponseModel addressResponseModel = AddressResponseModel.builder().address(address).build();
            addressResponseModels.add(addressResponseModel);
        });
        return addressResponseModels;
    }

    public AddressResponseModel addAddress(AddressRequestModel addressRequestModel) {
        User user = userRepository.findById(addressRequestModel.getUserId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Address address = Address.addressBuilder().addressRequestModel(addressRequestModel).user(user).build();
        addressRepository.save(address);
        return AddressResponseModel.builder().address(address).build();
    }

    public AddressResponseModel editAddress(Long id, AddressRequestModel addressRequestModel) {
        Address updateAddress = addressRepository.findById(id).orElseThrow();

        updateAddress.setCountry(addressRequestModel.getCountry());
        updateAddress.setCity(addressRequestModel.getCity());
        updateAddress.setNumber(addressRequestModel.getNumber());
        updateAddress.setPostNumber(addressRequestModel.getPostNumber());

        addressRepository.save(updateAddress);
        return AddressResponseModel.builder().address(updateAddress).build();
    }
    public String deleteAddress(Long id) {
        addressRepository.deleteById(id);
        return "address is deleted";
    }
}
