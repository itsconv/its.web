package com.itsconv.web.security.service;

import com.itsconv.web.user.domain.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class UserPrincipal implements UserDetails {

    private final Long userSeq;
    private final String username;
    private final String password;
    private final String name;
    private final String useYn;
    private final String lockYn;
    private final String delYn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(UserRole.ADMIN.getAuthority()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !"Y".equals(lockYn);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "Y".equals(useYn) && !"Y".equals(delYn);
    }
}
