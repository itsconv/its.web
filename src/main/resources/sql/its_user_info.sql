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
) ON DUPLICATE KEY UPDATE
    user_name = VALUES(user_name),
    user_pw = VALUES(user_pw),
    user_memo = VALUES(user_memo),
    create_date = VALUES(create_date),
    last_update = VALUES(last_update),
    fail_count = VALUES(fail_count),
    lock_yn = VALUES(lock_yn),
    del_yn = VALUES(del_yn),
    use_yn = VALUES(use_yn);

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
) VALUES
('admin01', '관리자01', '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u', '더미 관리자01', NOW(), NOW(), 0, 'N', 'N', 'Y'),
('admin02', '관리자02', '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u', '더미 관리자02', NOW(), NOW(), 0, 'N', 'N', 'Y'),
('admin03', '관리자03', '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u', '더미 관리자03', NOW(), NOW(), 0, 'N', 'N', 'Y'),
('admin04', '관리자04', '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u', '더미 관리자04', NOW(), NOW(), 0, 'N', 'N', 'Y'),
('admin05', '관리자05', '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u', '더미 관리자05', NOW(), NOW(), 0, 'N', 'N', 'Y'),
('admin06', '관리자06', '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u', '더미 관리자06', NOW(), NOW(), 0, 'N', 'N', 'Y'),
('admin07', '관리자07', '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u', '더미 관리자07', NOW(), NOW(), 0, 'N', 'N', 'Y'),
('admin08', '관리자08', '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u', '더미 관리자08', NOW(), NOW(), 0, 'N', 'N', 'Y'),
('admin09', '관리자09', '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u', '더미 관리자09', NOW(), NOW(), 0, 'N', 'N', 'Y'),
('admin10', '관리자10', '$2b$10$rj72KYIeSPA23EZLCYVe1OdyBZ16VBlewmG9s83vODlleENOLmH5u', '더미 관리자10', NOW(), NOW(), 0, 'N', 'N', 'Y')
ON DUPLICATE KEY UPDATE
    user_name = VALUES(user_name),
    user_pw = VALUES(user_pw),
    user_memo = VALUES(user_memo),
    create_date = VALUES(create_date),
    last_update = VALUES(last_update),
    fail_count = VALUES(fail_count),
    lock_yn = VALUES(lock_yn),
    del_yn = VALUES(del_yn),
    use_yn = VALUES(use_yn);
