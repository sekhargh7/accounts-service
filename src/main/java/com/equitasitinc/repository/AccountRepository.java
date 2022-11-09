package com.equitasitinc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equitasitinc.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
