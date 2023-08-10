package com.simple_man_store.account.service;

import com.simple_man_store.account.dto.AccountDto;
import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.repository.IAccountRepository;
import com.simple_man_store.account.util.EncrytedPasswordUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService{

    @Autowired
    private IAccountRepository accountRepository;
    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public void save(AccountDto accountDto) {
        String pass = EncrytedPasswordUtils.encrytePassword(accountDto.getPassword());
        Account account = new Account();
        BeanUtils.copyProperties(accountDto,account);
        account.setEncrytedPassword(pass);
        accountRepository.save(account);
    }

    @Override
    public boolean checkOldPass(String email, String confirmOldPassword) {
        Account account = accountRepository.findByEmail(email);
        String confirmOldPass = EncrytedPasswordUtils.encrytePassword(confirmOldPassword);
        String oldPass = account.getEncrytedPassword();
        return oldPass.equals(confirmOldPass);
    }

    @Override
    public boolean checkNewPass(String email, String newPassword) {
        Account account = accountRepository.findByEmail(email);
        String newPass = EncrytedPasswordUtils.encrytePassword(newPassword);
        String oldPass = account.getEncrytedPassword();
        return !newPass.equals(oldPass);
        // Nếu mật khẩu mới giống mật khẩu cũ --> return true

    }

    @Override
    public void changePassword(String email, String newPassword) {
        Account account = accountRepository.findByEmail(email);
        String newPass = EncrytedPasswordUtils.encrytePassword(newPassword);
        account.setEncrytedPassword(newPass);
        accountRepository.save(account);
    }


}
