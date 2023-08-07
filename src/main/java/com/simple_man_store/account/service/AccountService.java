package com.simple_man_store.account.service;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService{
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }
}
