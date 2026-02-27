package com.itsconv.web.security.service;

import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.user.domain.User;
import com.itsconv.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (user.isLocked()) {
            throw new LockedException(ErrorCode.USER_LOCKED.getMessage());
        }

        return UserPrincipal.builder()
                .userSeq(user.getSeq())
                .username(user.getUserId())
                .password(user.getPw())
                .name(user.getName())
                .useYn(user.getUseYn())
                .lockYn(user.getLockYn())
                .delYn(user.getDelYn())
                .build();
    }
}
