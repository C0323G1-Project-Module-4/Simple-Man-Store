package com.simple_man_store.account.service;

import com.simple_man_store.account.model.AccountRole;
import com.simple_man_store.account.repository.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountRoleService implements IAccountRoleService{
    @Autowired
    private AccountRoleRepository accountRoleRepository;
    @Override
    public AccountRole create(AccountRole accountRole) {
        return accountRoleRepository.save(accountRole);
    }
}
