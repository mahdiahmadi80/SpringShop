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
    public List<WalletResponseModel> listWallet() {
        return walletService.listWallet();
    }

    @RequestMapping(value = "/walletinfo/{id}", method = RequestMethod.GET)
    public WalletResponseModel infoWallet(@PathVariable Long id) {
        return walletService.infoWallet(id);
    }

    @RequestMapping(value = "/deposit/{id}", method = RequestMethod.POST)
    public String depositWallet(@PathVariable Long id, @RequestBody WalletRequestModel walletRequestModel) {
        return walletService.depositWallet(id, walletRequestModel);
    }

//    @RequestMapping(value = "/Deduction/{id}", method = RequestMethod.POST)
//    public WalletResponseModel deduceWallet(@PathVariable Long id, @RequestBody WalletRequestModel walletRequestModel) {
//        return walletService.deduceWallet(id, walletRequestModel);
//    }
    @RequestMapping(value = "/charge/{id}", method = RequestMethod.POST)
    public String chargeWallet(@PathVariable Long id, @RequestBody WalletRequestModel walletRequestModel) {
        return walletService.chargeWallet(id, walletRequestModel);
    }
//    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.POST)
//    public WalletResponseModel transactionsWallet(@PathVariable Long id, @RequestBody WalletRequestModel walletRequestModel) {
//        return walletService.deduceWallet(id, walletRequestModel);
//    }



}
