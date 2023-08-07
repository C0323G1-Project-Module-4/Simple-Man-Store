package com.simple_man_store.account.service;


import com.simple_man_store.account.model.Role;
import com.simple_man_store.account.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findById(int id) {
        return roleRepository.findById(id).get();
    }
}
