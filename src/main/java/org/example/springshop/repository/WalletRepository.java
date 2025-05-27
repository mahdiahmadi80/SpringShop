package org.example.springshop.repository;

import org.example.springshop.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {



    Wallet findAllById(Long id);
}
