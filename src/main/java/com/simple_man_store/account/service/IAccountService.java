package com.simple_man_store.account.service;

import com.simple_man_store.account.dto.AccountDto;
import com.simple_man_store.account.model.Account;


public interface IAccountService {
    Account findByEmail(String email);
    void save(AccountDto accountDto);

    boolean checkOldPass(String email, String confirmOldPassword);
    boolean checkNewPass(String email, String newPassword);

    void changePassword(String email, String newPassword);
    void sendEmail(String to, String subject, String body);
    String sendEmailAndReturnCode(String to);
}
