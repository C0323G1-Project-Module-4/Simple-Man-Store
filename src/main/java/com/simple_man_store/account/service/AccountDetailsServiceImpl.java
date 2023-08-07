package com.simple_man_store.account.service;


import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.model.AccountRole;
import com.simple_man_store.account.repository.IAccountRepository;
import com.simple_man_store.account.repository.IAccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AccountDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IAccountRepository IAccountRepository;

    @Autowired
    private IAccountRoleRepository IAccountRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = this.IAccountRepository.findByEmail(email);

        if (account == null) {
            System.out.println("User not found! " + email);
            throw new UsernameNotFoundException("Email " + email + " was not found in the database");
        }
        System.out.println("Found User: " + account);

        // [ROLE_USER, ROLE_ADMIN,..]
        List<AccountRole> accountRoles = this.IAccountRoleRepository.findByAccount(account);

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (accountRoles != null) {
            for (AccountRole role : accountRoles) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole().getName());
                grantList.add(authority);
            }
        }

        UserDetails userDetails = new User(account.getEmail(),
                account.getEncrytedPassword(), grantList);

        return userDetails;
    }


}
