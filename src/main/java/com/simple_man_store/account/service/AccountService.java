package com.simple_man_store.account.service;

import com.simple_man_store.account.dto.AccountDto;
import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.repository.IAccountRepository;
import com.simple_man_store.account.util.EncrytedPasswordUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendEmailAndReturnCode(String email) {
        // Sinh mã ngẫu nhiên
        String code = generateRandomCode(6); // Mã có độ dài 6
        // Tạo nội dung email
        String body = "Mã xác nhận của bạn là: " + code;
        // Cấu hình subject
        String subject = "Thay đổi mật khẩu";
        sendEmail(email, subject, body);
        return code;
    }

    private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }


    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public void save(AccountDto accountDto) {
        String pass = EncrytedPasswordUtils.encrytePassword(accountDto.getPassword());
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
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
