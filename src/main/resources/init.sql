INSERT INTO its_user_info (
    user_id,
    user_name,
    user_pw,
    user_memo,
    create_date,
    last_update,
    fail_count,
    lock_yn,
    del_yn,
    use_yn
) VALUES (
    'admin',
    '관리자',
    '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u',
    '초기 관리자 계정',
    NOW(),
    NOW(),
    0,
    'N',
    'N',
    'Y'
);
