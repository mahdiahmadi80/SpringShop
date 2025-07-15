package org.example.springshop.service;

import org.example.springshop.exception.walletException.BalanceException;
import org.example.springshop.exception.walletException.WalletNotFoundException;
import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.WalletRequestModel;
import org.example.springshop.model.dto.responsemodel.WalletResponseModel;
import org.example.springshop.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<WalletResponseModel> listWallet() {
        List<WalletResponseModel> walletResponseModels = new ArrayList<>();
        walletRepository.findAll().forEach(wallet -> {
            WalletResponseModel walletResponseModel = WalletResponseModel.builder().wallet(wallet).build();
            walletResponseModels.add(walletResponseModel);
        });
        return walletResponseModels;
    }

    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public WalletResponseModel infoWallet(Long id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
        return WalletResponseModel.builder().wallet(wallet).build();
    }

    public String depositWallet(Long id, WalletRequestModel walletRequestModel) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
        wallet.setBalance(walletRequestModel.getBalance() + wallet.getBalance());
        walletRepository.save(wallet);
        return "your wallet charging your new balance:" + wallet.getBalance();
    }


//    public WalletResponseModel deduceWallet(Long id, WalletRequestModel walletRequestModel) {
//        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
//
//        if (wallet.getBalance() - walletRequestModel.getBalance() != 0) {
//            wallet.setBalance(walletRequestModel.getBalance());
//        } else {
//            throw new BalanceException("balance not enough");
//        }
//
//        walletRepository.save(wallet);
//        return WalletResponseModel.builder().wallet(wallet).build();
//    }

    public String chargeWallet(Long id, WalletRequestModel walletRequestModel) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
        wallet.setBalance(walletRequestModel.getBalance() + wallet.getBalance());
        walletRepository.save(wallet);
        return "your wallet charge:" + walletRequestModel.getBalance() + "new balance:" + wallet.getBalance();

    }

    public WalletResponseModel findById(Long id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
        return WalletResponseModel.builder().wallet(wallet).build();
    }
}
