package org.example.springshop.service;

import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.WalletRequestModel;
import org.example.springshop.model.dto.responsemodel.WalletResponseModel;
import org.example.springshop.repository.WalletRepository;
import org.example.springshop.service.serviceint.WalletInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service

public class WalletService implements WalletInt {
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public List<WalletResponseModel> walletList() {
        List<WalletResponseModel> walletResponseModels = new ArrayList<>();
        walletRepository.findAll().forEach(wallet -> {
            WalletResponseModel walletResponseModel = WalletResponseModel.builder().wallet(wallet).build();
            walletResponseModels.add(walletResponseModel);
        });
        return walletResponseModels;
    }

    @Override
    public Wallet showBalance(Long id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow();
        return wallet;
    }

    @Override
    public void deposit(Long id, WalletRequestModel walletRequestModel) {
        Wallet wallet = walletRepository.findById(id).orElseThrow();

        wallet.setBalance(walletRequestModel.getBalance());
        walletRepository.save(wallet);

    }

    @Override
    public Wallet findById(Long id) {
        return walletRepository.findById(id).orElseThrow();
    }
}
