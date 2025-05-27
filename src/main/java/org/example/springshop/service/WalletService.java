package org.example.springshop.service;

import org.example.springshop.exception.walletException.WalletNotFoundException;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.WalletRequestModel;
import org.example.springshop.model.dto.responsemodel.WalletResponseModel;
import org.example.springshop.repository.WalletRepository;
import org.example.springshop.service.serviceint.WalletInt;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service

public class WalletService implements WalletInt {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

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
    public Wallet walletAdd(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public WalletResponseModel showBalance(Long id) {

        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("wallet not found"));

        return WalletResponseModel.builder().wallet(wallet).build();
    }

    @Override
    public void deposit(Long id, WalletRequestModel walletRequestModel) {

        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("wallet not found"));

        wallet.setBalance(walletRequestModel.getBalance());

        walletRepository.save(wallet);

    }

    @Override
    public Wallet findById(Long id) {
        return walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
    }
}
