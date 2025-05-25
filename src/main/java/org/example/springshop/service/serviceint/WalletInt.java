package org.example.springshop.service.serviceint;

import org.example.springshop.model.Wallet;
import org.example.springshop.model.dto.requestmodel.WalletRequestModel;
import org.example.springshop.model.dto.responsemodel.WalletResponseModel;

import java.util.List;

public interface WalletInt {
List<WalletResponseModel> walletList();

    Wallet showBalance(Long id);

     void deposit(Long id ,WalletRequestModel walletRequestModel);
     Wallet findById(Long id);
}
