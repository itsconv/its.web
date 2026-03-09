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
    '팝업 샘플 01', '팝업 샘플 01 내용', '2026-01-01', '2026-12-31', 'Y', 'N', 10, 10, 300, 180, NULL, NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM its_popup_list WHERE title = '팝업 샘플 01' AND start_date = '2026-01-01' AND end_date = '2026-12-31')
UNION ALL
SELECT '팝업 샘플 02', '팝업 샘플 02 내용', '2026-02-01', '2026-12-31', 'Y', 'N', 20, 20, 300, 180, NULL, NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM its_popup_list WHERE title = '팝업 샘플 02' AND start_date = '2026-02-01' AND end_date = '2026-12-31')
UNION ALL
SELECT '팝업 샘플 03', '팝업 샘플 03 내용', '2026-03-01', '2026-12-31', 'Y', 'N', 30, 30, 300, 180, NULL, NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM its_popup_list WHERE title = '팝업 샘플 03' AND start_date = '2026-03-01' AND end_date = '2026-12-31')
UNION ALL
SELECT '팝업 샘플 04', '팝업 샘플 04 내용', '2026-04-01', '2026-12-31', 'Y', 'N', 40, 40, 300, 180, NULL, NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM its_popup_list WHERE title = '팝업 샘플 04' AND start_date = '2026-04-01' AND end_date = '2026-12-31')
UNION ALL
SELECT '팝업 샘플 05', '팝업 샘플 05 내용', '2026-05-01', '2026-12-31', 'Y', 'N', 50, 50, 300, 180, NULL, NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM its_popup_list WHERE title = '팝업 샘플 05' AND start_date = '2026-05-01' AND end_date = '2026-12-31')
UNION ALL
SELECT '팝업 샘플 06', '팝업 샘플 06 내용', '2026-06-01', '2026-12-31', 'Y', 'N', 60, 60, 300, 180, NULL, NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM its_popup_list WHERE title = '팝업 샘플 06' AND start_date = '2026-06-01' AND end_date = '2026-12-31')
UNION ALL
SELECT '팝업 샘플 07', '팝업 샘플 07 내용', '2026-07-01', '2026-12-31', 'Y', 'N', 70, 70, 300, 180, NULL, NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM its_popup_list WHERE title = '팝업 샘플 07' AND start_date = '2026-07-01' AND end_date = '2026-12-31')
UNION ALL
SELECT '팝업 샘플 08', '팝업 샘플 08 내용', '2026-08-01', '2026-12-31', 'Y', 'N', 80, 80, 300, 180, NULL, NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM its_popup_list WHERE title = '팝업 샘플 08' AND start_date = '2026-08-01' AND end_date = '2026-12-31')
UNION ALL
SELECT '팝업 샘플 09', '팝업 샘플 09 내용', '2026-09-01', '2026-12-31', 'Y', 'N', 90, 90, 300, 180, NULL, NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM its_popup_list WHERE title = '팝업 샘플 09' AND start_date = '2026-09-01' AND end_date = '2026-12-31')
UNION ALL
SELECT '팝업 샘플 10', '팝업 샘플 10 내용', '2026-10-01', '2026-12-31', 'Y', 'N', 100, 100, 300, 180, NULL, NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM its_popup_list WHERE title = '팝업 샘플 10' AND start_date = '2026-10-01' AND end_date = '2026-12-31');

--- 연혁 
INSERT INTO history_period (
    display_order,
    end_period,
    start_period,
    create_date,
    id,
    last_update,
    last_update_id
) VALUES
(0, '2010', '1999', NOW(), 1, NOW(),'admin'),
(1, '2020', '2011', NOW(), 2, NOW(), 'admin'),
(2, 'Now', '2021', NOW(), 3, NOW(), 'admin');

INSERT INTO history_year (
	display_order,
	history_year, 
	create_date, 
	id, 
	last_update, 
	period_id, 
	create_id, 
	last_update_id
) VALUES 
(0, '1999', '2026-02-28 12:38:01', 1, '2026-02-28 12:38:07', 1, NULL, NULL),
(1, '2002', '2026-02-28 12:40:29', 2, '2026-02-28 12:40:31', 1, NULL, NULL),
(2, '2003', '2026-02-28 12:40:48', 3, '2026-02-28 12:40:49', 1, NULL, NULL),
(3, '2005', '2026-02-28 12:41:07', 4, '2026-02-28 12:41:08', 1, NULL, NULL),
(4, '2006', '2026-02-28 12:41:15', 5, '2026-02-28 12:41:16', 1, NULL, NULL);


INSERT INTO history_item (
	display_order, 
	create_date, 
	id, 
	last_update, 
	year_id, 
	content, 
	create_id, 
	last_update_id
) VALUES 
(0, '2026-02-28 12:41:31', 1, '2026-02-28 12:41:33', 1, '아이티에스정보통신 설립', NULL, NULL),
(1, '2026-02-28 12:43:42', 2, '2026-02-28 12:43:42', 1, 'Nortel 교환기 비즈니스 오픈', NULL, NULL),
(2, '2026-02-28 12:43:58', 3, '2026-02-28 12:43:58', 1, 'BT(British Telecom) 딜링 시스템 오픈', NULL, NULL),
(0, '2026-02-28 12:44:20', 4, '2026-03-01 00:06:24', 2, 'The Executive Centre Korea PBX 구축 및 운영 유지보수', NULL, 'admin');


-- BOARD_TYPE = DATA 더미 데이터 20건
INSERT INTO its_board (
    board_type,
    title,
    contents,
    create_id,
    last_update_id,
    view_count,
    create_date,
    last_update
) VALUES
('DATA', 'DATA 더미 게시글 01', 'DATA 타입 게시글 더미 내용 01', 'admin', 'admin', 0, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 02', 'DATA 타입 게시글 더미 내용 02', 'admin', 'admin', 3, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 03', 'DATA 타입 게시글 더미 내용 03', 'admin', 'admin', 5, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 04', 'DATA 타입 게시글 더미 내용 04', 'admin', 'admin', 1, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 05', 'DATA 타입 게시글 더미 내용 05', 'admin', 'admin', 7, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 06', 'DATA 타입 게시글 더미 내용 06', 'admin', 'admin', 2, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 07', 'DATA 타입 게시글 더미 내용 07', 'admin', 'admin', 0, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 08', 'DATA 타입 게시글 더미 내용 08', 'admin', 'admin', 9, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 09', 'DATA 타입 게시글 더미 내용 09', 'admin', 'admin', 4, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 10', 'DATA 타입 게시글 더미 내용 10', 'admin', 'admin', 6, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 11', 'DATA 타입 게시글 더미 내용 11', 'admin', 'admin', 1, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 12', 'DATA 타입 게시글 더미 내용 12', 'admin', 'admin', 8, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 13', 'DATA 타입 게시글 더미 내용 13', 'admin', 'admin', 11, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 14', 'DATA 타입 게시글 더미 내용 14', 'admin', 'admin', 3, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 15', 'DATA 타입 게시글 더미 내용 15', 'admin', 'admin', 0, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 16', 'DATA 타입 게시글 더미 내용 16', 'admin', 'admin', 13, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 17', 'DATA 타입 게시글 더미 내용 17', 'admin', 'admin', 2, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 18', 'DATA 타입 게시글 더미 내용 18', 'admin', 'admin', 5, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 19', 'DATA 타입 게시글 더미 내용 19', 'admin', 'admin', 1, NOW(), NOW()),
('DATA', 'DATA 더미 게시글 20', 'DATA 타입 게시글 더미 내용 20', 'admin', 'admin', 10, NOW(), NOW());
