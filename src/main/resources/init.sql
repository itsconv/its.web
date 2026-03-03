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
