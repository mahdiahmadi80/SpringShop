package org.example.springshop.service;

import org.example.springshop.exception.walletException.BalanceException;
import org.example.springshop.exception.walletException.NotEnoughBalanceException;
import org.example.springshop.exception.walletException.WalletNotFoundException;
import org.example.springshop.model.Order;
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

    public void createWallet(User user) {
        Wallet wallet = Wallet.userWalletClass().user(user).build();
        walletRepository.save(wallet);
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

    public WalletResponseModel deduceWallet(Order order, Long totalAmount) {
        Wallet wallet = walletRepository.findWalletByUserId(order.getUser()).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
        wallet.setBalance(wallet.getBalance() - totalAmount);
        if (wallet.getBalance() < 0) {
            throw new BalanceException("your balance not enough charging your wallet please");
        }
        walletRepository.save(wallet);
        return WalletResponseModel.builder().wallet(wallet).build();
    }
    public void checkWallet(User user, Long totalAmount) {
        Wallet wallet = walletRepository.findWalletByUserId(user.getId()).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
        Long walletBalance = wallet.getBalance() - totalAmount;
        if (walletBalance < 0) {
            throw new NotEnoughBalanceException("your balance not enough");
        }
    }
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
