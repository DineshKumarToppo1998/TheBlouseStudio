package com.tbs.repository;

import com.tbs.dto.CustomerDto;
import com.tbs.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Customer findByName(String name);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))") //Case-insensitive search
    List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);


    @Query("SELECT NEW com.tbs.dto.CustomerDto(c.id, c.name, c.email, c.phone, c.address) FROM Customer c WHERE c.id = ?1")
    CustomerDto findByIdDto(Long id);

}
