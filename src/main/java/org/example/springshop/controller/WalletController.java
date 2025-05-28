package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.WalletRequestModel;
import org.example.springshop.model.dto.responsemodel.WalletResponseModel;
import org.example.springshop.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {


    private final WalletService walletService;
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<WalletResponseModel> wallelList() {
        return walletService.walletList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WalletResponseModel showBalance(@PathVariable Long id) {
        return walletService.showBalance(id);
    }

    @RequestMapping(value = "/deposit/{id}", method = RequestMethod.POST)
    public void deposit(@PathVariable Long id, @RequestBody WalletRequestModel walletRequestModel) {
        walletService.deposit(id, walletRequestModel);
    }


}
