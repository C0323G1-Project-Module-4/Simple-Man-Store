package com.simple_man_store.account.repository;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.model.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRoleRepository extends JpaRepository<AccountRole,Integer> {
    List<AccountRole> findByAccount(Account account);
}
