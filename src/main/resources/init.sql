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

INSERT INTO history_period (
    display_order,
    end_period,
    start_period,
    create_date, 
    id, 
    last_update, 
    last_update_id
) VALUES 
(0, '2010', '1999', NOW(), 1, NOW(),'admin');
(1, '2020', '2011', NOW(), 2, NOW(), 'admin');
(2, 'Now', '2021', NOW(), 3, NOW(), 'admin');