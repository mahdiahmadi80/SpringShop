package org.example.springshop.controller;

import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.WalletRequestModel;
import org.example.springshop.model.dto.responsemodel.WalletResponseModel;
import org.example.springshop.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<WalletResponseModel> wallelList() {
        return walletService.walletList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Wallet showBalance(@PathVariable Long id) {
        return walletService.showBalance(id);
    }

    @RequestMapping(value = "/deposit/{id}", method = RequestMethod.POST)
    public void deposit(@PathVariable Long id, @RequestBody WalletRequestModel walletRequestModel) {

        walletService.deposit(id, walletRequestModel);
    }


}
