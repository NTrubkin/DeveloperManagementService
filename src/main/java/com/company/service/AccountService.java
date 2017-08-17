package com.company.service;

import com.company.dao.AccountDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccountService implements UserDetailsService, Serializable {

    @Autowired
    private transient AccountDAOImpl accountDAO;

    @Override
    public UserDetails loadUserByUsername(String nickname) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> auths = new ArrayList<>();
                auths.add(new SimpleGrantedAuthority(accountDAO.read(nickname).getRole().getCode()));
                return auths;
            }

            @Override
            public String getPassword() {
                return accountDAO.read(nickname).getPasshash();
            }

            @Override
            public String getUsername() {
                return nickname;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
