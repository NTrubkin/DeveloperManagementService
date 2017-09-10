package com.company.service;

import com.company.dao.api.AccountDAO;
import com.company.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Реализует сервис аутентификации через базу данных
 */
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public UserDetails loadUserByUsername(String nickname) {
        return new UserDetails() {
            /**
             * Возвращает роли пользователя
             * @return
             */
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Account account = accountDAO.read(nickname);
                if (account == null) {
                    throw new UsernameNotFoundException("There is no account with nickname '" + nickname + "' in database");
                }

                List<SimpleGrantedAuthority> auths = new ArrayList<>();
                auths.add(new SimpleGrantedAuthority(account.getRole().getCode()));
                return auths;
            }

            @Override
            public String getPassword() {
                Account account = accountDAO.read(nickname);
                if (account == null) {
                    throw new UsernameNotFoundException("There is no account with nickname '" + nickname + "' in database");
                }
                return account.getPasshash();
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
