package org.example.springshop.repository;

import org.example.springshop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

  @Query(value = "select * from TBL_ADDRESS where USER_ID like %:user%",nativeQuery = true)
    Optional<Address> findAddressByUserId(Long user);
}
