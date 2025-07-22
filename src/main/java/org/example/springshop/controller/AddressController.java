package org.example.springshop.controller;
import org.example.springshop.model.dto.requestmodel.AddressRequestModel;
import org.example.springshop.model.dto.requestmodel.UserRequestModel;
import org.example.springshop.model.dto.responsemodel.AddressResponseModel;
import org.example.springshop.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping(value = "/address")
public class AddressController {

    final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<AddressResponseModel> listAddress() {
        return addressService.listAddress();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AddressResponseModel addAddress(@RequestBody UserRequestModel userRequestModel) {
        return addressService.addAddress(userRequestModel);
    }

    @RequestMapping(value = "/edit/**", method = RequestMethod.POST)
    public AddressResponseModel editAddress( @RequestBody AddressRequestModel addressRequestModel) {
        return addressService.editAddress(addressRequestModel);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        return addressService.deleteAddress(id);
    }
}
