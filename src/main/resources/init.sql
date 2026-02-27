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

INSERT INTO its_popup_list (
    title,
    contents,
    start_date,
    end_date,
    use_yn,
    del_yn,
    position_x,
    position_y,
    size_w,
    size_h,
    redirect_url,
    create_date,
    last_update
) SELECT
    'JAVA 개발자 채용 팝업',
    '',
    '2024-11-25',
    '2027-11-25',
    'Y',
    'N',
    500,
    31,
    450,
    1045,
    NULL,
    NOW(),
    NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1
    FROM its_popup_list
    WHERE title = 'JAVA 개발자 채용 팝업'
      AND start_date = '2024-11-25'
      AND end_date = '2027-11-25'
);

INSERT INTO its_popup_list (
    title,
    contents,
    start_date,
    end_date,
    use_yn,
    del_yn,
    position_x,
    position_y,
    size_w,
    size_h,
    redirect_url,
    create_date,
    last_update
) SELECT
    'USB 데스크스위치',
    '',
    '2023-02-03',
    '2025-02-03',
    'Y',
    'N',
    25,
    31,
    450,
    400,
    NULL,
    NOW(),
    NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1
    FROM its_popup_list
    WHERE title = 'USB 데스크스위치'
      AND start_date = '2023-02-03'
      AND end_date = '2025-02-03'
);
