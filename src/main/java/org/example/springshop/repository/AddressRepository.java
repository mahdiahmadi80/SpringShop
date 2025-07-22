package org.example.springshop.repository;

import org.example.springshop.model.Address;
import org.example.springshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select a from Address a where a.userId.id =:user")
    Optional<Address> findAddressByUserId(Long user);

    Optional<Address> findByUserId(User userId);
}
