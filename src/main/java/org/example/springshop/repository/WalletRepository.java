package org.example.springshop.repository;

import org.example.springshop.model.User;
import org.example.springshop.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    @Query(value = "select * from tbl_wallet where user_id like %:user%",nativeQuery = true)
    Optional<Wallet> findWalletByUserId(Long user);

    Optional<Wallet> findWalletByUserId(User userId);
    Wallet findAllById(Long id);
}
