package com.itsconv.web.user.service;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.user.domain.User;
import com.itsconv.web.user.repository.UserRepository;
import com.itsconv.web.user.service.dto.command.UserDeleteCommand;
import com.itsconv.web.user.service.dto.command.UserRegisterCommand;
import com.itsconv.web.user.service.dto.command.UserUpdateCommand;
import com.itsconv.web.user.service.dto.result.UserDetailView;
import com.itsconv.web.user.service.dto.result.UserListView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final int MAX_LOGIN_FAIL_COUNT = 5;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserRegisterCommand command) {
        boolean existsUser = userRepository.findByUserId(command.userId()).isPresent();
        if (existsUser) {
            throw new BusinessException(ErrorCode.COMMON_CONFLICT);
        }

        User user = User.create(
                command.userId(),
                command.name(),
                passwordEncoder.encode(command.password()),
                command.memo()
        );
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(UserUpdateCommand command) {
        User user = findUserEntity(command.userId());

        user.updateProfile(command.name(), command.memo());

        // 사용자가 비밀번호를 입력한 경우에만 비밀번호 업데이트
        if (command.password() != null && !command.password().isBlank()) {
            user.updatePassword(passwordEncoder.encode(command.password()));
        }
    }

    @Transactional
    public void deleteUser(UserDeleteCommand command) {
        User user = findUserEntity(command.userId());
        user.delete();
    }

    @Transactional(readOnly = true)
    public UserListView findUserList() {
        List<UserDetailView> users = userRepository.findAll().stream()
                .map(UserDetailView::from)
                .toList();
        return new UserListView(users);
    }

    @Transactional(readOnly = true)
    public UserDetailView findUser(String userId) {
        User user = findUserEntity(userId);
        return UserDetailView.from(user);
    }

    @Transactional
    public void updateLastLogin(String userId) {
        User user = findUserEntity(userId);
        user.clearFailCountAndUpdateLastLogin(LocalDateTime.now());
    }

    @Transactional
    public void increaseFailCount(String userId) {
        userRepository.findByUserId(userId)
                .ifPresent(user -> user.increaseFailCountAndLockIfNeeded(MAX_LOGIN_FAIL_COUNT));
    }

    private User findUserEntity(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
