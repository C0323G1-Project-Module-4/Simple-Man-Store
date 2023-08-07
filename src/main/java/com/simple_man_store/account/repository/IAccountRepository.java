package com.simple_man_store.account.repository;

import com.simple_man_store.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account,Integer> {
    Account findByEmail(String email);
}
