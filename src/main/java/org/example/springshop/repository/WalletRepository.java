package org.example.springshop.repository;

import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findWalletByUserId(User userId);
}
