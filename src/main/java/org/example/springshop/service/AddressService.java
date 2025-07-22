package org.example.springshop.service;

import org.example.springshop.exception.Address.AddressException;
import org.example.springshop.exception.ExceptionMessage;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.Address;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.AddressRequestModel;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.example.springshop.model.dto.responsemodel.AddressResponseModel;
import org.example.springshop.repository.AddressRepository;
import org.example.springshop.repository.UserRepository;
import org.springframework.http.ResponseEntity;
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

    public AddressResponseModel addAddress(UserRequestModel userRequestModel) {
        User user = userRepository.findById(userRequestModel.getId()).orElseThrow(() -> new UserNotFoundException(ExceptionMessage.userNotFound));
        Address address = Address.addressBuilder().userRequestModel(userRequestModel).user(user).build();

        addressRepository.save(address);
        return AddressResponseModel.builder().address(address).build();
    }

    public AddressResponseModel editAddress(AddressRequestModel addressRequestModel) {
        Address updateAddress = addressRepository.findById(addressRequestModel.getId()).orElseThrow(() -> new AddressException(ExceptionMessage.addressNotFound));
        updateAddress.setCountry(addressRequestModel.getCountry());
        updateAddress.setCity(addressRequestModel.getCity());
        updateAddress.setNumber(addressRequestModel.getNumber());
        updateAddress.setPostNumber(addressRequestModel.getPostNumber());
        addressRepository.save(updateAddress);
        return AddressResponseModel.builder().address(updateAddress).build();
    }

    public ResponseEntity<String> deleteAddress(Long id) {
        addressRepository.deleteById(id);
        return ResponseEntity.ok(ExceptionMessage.deleteSuccessful);
    }
}
