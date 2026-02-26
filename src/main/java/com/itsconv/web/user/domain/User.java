package com.itsconv.web.user.domain;

import com.itsconv.web.common.domain.BaseTimeEntity;
import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "its_user_info",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_id", columnNames = "user_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    private Long seq;

    @Column(name = "user_id", nullable = false, length = 50, unique = true)
    private String userId;

    @Column(name = "user_name", length = 50)
    private String name;

    @Column(name = "user_pw", nullable = false, length = 200)
    private String pw;

    @Lob
    @Column(name = "user_memo", columnDefinition = "TEXT")
    private String memo;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "fail_count", nullable = false)
    private Integer failCount = 0;

    @Column(name = "lock_yn", nullable = false, length = 1)
    private String lockYn = "N";

    @Column(name = "del_yn", nullable = false, length = 1)
    private String delYn = "N";

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn = "Y";

    @Builder
    private User(
            String userId,
            String name,
            String pw,
            String memo,
            java.time.LocalDateTime lastLogin,
            Integer failCount,
            String lockYn,
            String delYn,
            String useYn
    ) {
        this.userId = userId;
        this.name = name;
        this.pw = pw;
        this.memo = memo;
        this.lastLogin = lastLogin;
        this.failCount = failCount;
        this.lockYn = lockYn;
        this.delYn = delYn;
        this.useYn = useYn;
    }

    public static User create(String userId, String name, String pw, String memo) {
        return User.builder()
                .userId(userId)
                .name(name)
                .pw(pw)
                .memo(memo)
                .failCount(0)
                .lockYn("N")
                .delYn("N")
                .useYn("Y")
                .build();
    }

    public boolean isDeleted() {
        return "Y".equals(this.delYn);
    }

    public boolean isDisabled() {
        return !"Y".equals(this.useYn);
    }

    public boolean isLocked() {
        return "Y".equals(this.lockYn);
    }

    public void validateLoginable() {
        if (isDeleted()) {
            throw new BusinessException(ErrorCode.USER_DELETED);
        }
        if (isDisabled()) {
            throw new BusinessException(ErrorCode.USER_DISABLED);
        }
        if (isLocked()) {
            throw new BusinessException(ErrorCode.USER_LOCKED);
        }
    }

    public void increaseFailCountAndLockIfNeeded(int maxFailCount) {
        this.failCount += 1;
        if (failCount >= maxFailCount) {
            this.lockYn = "Y";
        }
    }

    public void clearFailCountAndUpdateLastLogin(LocalDateTime loginAt) {
        this.failCount = 0;
        this.lastLogin = loginAt;
    }
}
