package com.simple_man_store.account.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "role")
    private Set<Account> accountSet;

    public Role() {
    }

    public Role(Integer id, String name, Set<Account> accountSet) {
        this.id = id;
        this.name = name;
        this.accountSet = accountSet;
    }

    public Role(String name, Set<Account> accountSet) {
        this.name = name;
        this.accountSet = accountSet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getAccountSet() {
        return accountSet;
    }

    public void setAccountSet(Set<Account> accountSet) {
        this.accountSet = accountSet;
    }
}
