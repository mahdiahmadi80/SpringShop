package org.example.springshop.repository;

import org.example.springshop.model.ContactUs;
import org.example.springshop.model.dto.responsemodel.ContactUsResponseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {

    ContactUs findByShowed(boolean showed);
    @Query(value = "SELECT * from tbl_contactus where showed = false",nativeQuery = true)
    List<ContactUs> findByShowed();
}
