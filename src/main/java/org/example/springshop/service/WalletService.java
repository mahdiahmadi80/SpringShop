package org.example.springshop.service;

import org.example.springshop.exception.ExceptionMessage;
import org.example.springshop.exception.walletException.WalletException;
import org.example.springshop.model.Order;
import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.WalletRequestModel;
import org.example.springshop.model.dto.responsemodel.WalletResponseModel;
import org.example.springshop.repository.WalletRepository;
import org.springframework.http.ResponseEntity;
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

    public void createWallet(User user) {
        Wallet wallet = Wallet.userWalletClass().user(user).build();
        walletRepository.save(wallet);
    }

    public WalletResponseModel infoWallet(Long id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletException(ExceptionMessage.walletNotFound));
        return WalletResponseModel.builder().wallet(wallet).build();
    }

    public String depositWallet(Long id, WalletRequestModel walletRequestModel) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletException(ExceptionMessage.walletNotFound));
        wallet.setBalance(walletRequestModel.getBalance() + wallet.getBalance());
        walletRepository.save(wallet);
        return "your wallet charging your new balance:" + wallet.getBalance();
    }

    public void deduceWallet(Order order, Long totalAmount) {
        Wallet wallet = walletRepository.findWalletByUserId(order.getUser()).orElseThrow(() -> new WalletException(ExceptionMessage.walletNotFound));
        wallet.setBalance(wallet.getBalance() - totalAmount);
        if (wallet.getBalance() < 0) {
            throw new WalletException(ExceptionMessage.lowBalance);
        }
        walletRepository.save(wallet);
        WalletResponseModel.builder().wallet(wallet).build();
    }

    public ResponseEntity<String> chargeWallet(Long id, WalletRequestModel walletRequestModel) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletException(ExceptionMessage.walletNotFound));
        wallet.setBalance(walletRequestModel.getBalance() + wallet.getBalance());
        walletRepository.save(wallet);
        return ResponseEntity.ok("your wallet charge: " + walletRequestModel.getBalance() + " new balance:" + wallet.getBalance());
    }

//    public WalletResponseModel findById(Long id) {
//        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new WalletException(ExceptionMessage.walletNotFound));
//        return WalletResponseModel.builder().wallet(wallet).build();
//    }
}
