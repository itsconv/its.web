INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 100, NULL, 'MAIN', 'TRADING_ROOM', '통합트레이딩룸', NULL, 1, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 101, 100, 'SUB', 'TRADING_ROOM_BUSINESS_OVERVIEW', '비즈니스개요', NULL, 1, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_BUSINESS_OVERVIEW'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 102, 100, 'SUB', 'TRADING_ROOM_WEY_SOLUTION', 'WEY 솔루션', NULL, 2, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_WEY_SOLUTION'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 103, 100, 'SUB', 'TRADING_ROOM_BT_SOLUTION', 'BT 솔루션', NULL, 3, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_BT_SOLUTION'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 104, 100, 'SUB', 'TRADING_ROOM_ARCHITECTURE', '구성도', NULL, 4, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_ARCHITECTURE'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 105, 100, 'SUB', 'TRADING_ROOM_SUCCESS_CASE', '구축 성공 사례', NULL, 5, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_SUCCESS_CASE'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 106, 100, 'SUB', 'TRADING_ROOM_CUSTOMERS', '주요고객사', NULL, 6, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_CUSTOMERS'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 201, 102, 'TAB', 'TRADING_ROOM_WEY_IP_REMOTE', 'WEY-IP Remote', NULL, 1, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_WEY_IP_REMOTE'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 202, 102, 'TAB', 'TRADING_ROOM_WDP', 'WDP', NULL, 2, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_WDP'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 203, 102, 'TAB', 'TRADING_ROOM_WEY_SMART_TOUCH_KEYBOARD', 'WEY SMART Touch 키보드', NULL, 3, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_WEY_SMART_TOUCH_KEYBOARD'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 204, 102, 'TAB', 'TRADING_ROOM_UDS_USB_DESK_SWITCH', 'UDS USB 데스크 스위치', NULL, 4, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_UDS_USB_DESK_SWITCH'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 301, 103, 'TAB', 'TRADING_ROOM_BT_DEALER_BOARD_SYSTEM', 'BT딜러보드시스템', NULL, 1, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_BT_DEALER_BOARD_SYSTEM'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 302, 103, 'TAB', 'TRADING_ROOM_RECORDING_SOLUTION', '레코딩솔루션', NULL, 2, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_RECORDING_SOLUTION'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 303, 103, 'TAB', 'TRADING_ROOM_TICKER_BOARD', '전광판/티거보드', NULL, 3, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_TICKER_BOARD'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 304, 103, 'TAB', 'TRADING_ROOM_MOTION_DESK_MONITOR_ARM', '모션데스크/모니터암', NULL, 4, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_MOTION_DESK_MONITOR_ARM'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 305, 103, 'TAB', 'TRADING_ROOM_CABLE_AND_FACILITIES', '케이블/부대설비', NULL, 5, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'TRADING_ROOM_CABLE_AND_FACILITIES'
);

-- AI관제센터 메뉴
INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
) VALUES
(400, NULL, 'MAIN', 'AI_MONITORING_CENTER', 'AI관제센터', NULL, 2, 'Y', 'N', NOW(), NOW());

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
) VALUES
(401, 400, 'SUB', 'AI_MONITORING_CENTER_BUSINESS_OVERVIEW', '비즈니스개요', NULL, 1, 'Y', 'N', NOW(), NOW()),
(402, 400, 'SUB', 'AI_MONITORING_CENTER_DISASTER_SOLUTION', '재난안전 상황관제 솔루션', NULL, 2, 'Y', 'N', NOW(), NOW()),
(403, 400, 'SUB', 'AI_MONITORING_CENTER_ARCHITECTURE', '구성도', NULL, 3, 'Y', 'N', NOW(), NOW()),
(404, 400, 'SUB', 'AI_MONITORING_CENTER_SUCCESS_CASE', '구축 서공 사례', NULL, 4, 'Y', 'N', NOW(), NOW()),
(405, 400, 'SUB', 'AI_MONITORING_CENTER_CUSTOMERS', '주요 고객사', NULL, 5, 'Y', 'N', NOW(), NOW());

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
) VALUES
(501, 402, 'TAB', 'AI_MONITORING_CENTER_WEY_IP_REMOTE', 'WEY-IP Remote', NULL, 1, 'Y', 'N', NOW(), NOW()),
(502, 402, 'TAB', 'AI_MONITORING_CENTER_WDP', 'WDP', NULL, 2, 'Y', 'N', NOW(), NOW()),
(503, 402, 'TAB', 'AI_MONITORING_CENTER_WEY_SMART_TOUCH_KEYBOARD', 'WEY SMART Touch 키보드', NULL, 3, 'Y', 'N', NOW(), NOW()),
(504, 402, 'TAB', 'AI_MONITORING_CENTER_UDS_USB_DESK_SWITCH', 'UDS USB 데스크 스위치', NULL, 4, 'Y', 'N', NOW(), NOW()),
(505, 402, 'TAB', 'AI_MONITORING_CENTER_DISPLAY_BOARD', '전광판', NULL, 5, 'Y', 'N', NOW(), NOW()),
(506, 402, 'TAB', 'AI_MONITORING_CENTER_MOTION_DESK_SPACE_USE', '모션데스크 (공간활용)', NULL, 6, 'Y', 'N', NOW(), NOW()),
(507, 402, 'TAB', 'AI_MONITORING_CENTER_CABLE_AND_FACILITIES', '케이블/부대설비', NULL, 7, 'Y', 'N', NOW(), NOW());

-- AI컨텍센터 메뉴
INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 600, NULL, 'MAIN', 'AI_CONTACT_CENTER', 'AI컨텍센터', NULL, 3, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 601, 600, 'SUB', 'AI_CONTACT_CENTER_BUSINESS_OVERVIEW', '비즈니스개요', NULL, 1, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_BUSINESS_OVERVIEW'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 602, 600, 'SUB', 'AI_CONTACT_CENTER_AVAYA_IPCC_SOLUTION', 'AVAYA IPCC 솔루션', NULL, 2, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_AVAYA_IPCC_SOLUTION'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 603, 600, 'SUB', 'AI_CONTACT_CENTER_ABLE_IPCC_SOLUTION', 'ABLE IPCC 솔루션', NULL, 3, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_ABLE_IPCC_SOLUTION'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 604, 600, 'SUB', 'AI_CONTACT_CENTER_INHOUSE_SOLUTION', '자사 개발 솔루션', NULL, 4, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_INHOUSE_SOLUTION'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 605, 600, 'SUB', 'AI_CONTACT_CENTER_CUSTOMERS', '주요 고객사', NULL, 5, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_CUSTOMERS'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 701, 602, 'TAB', 'AI_CONTACT_CENTER_WEY_IP_REMOTE', 'WEY-IP Remote', NULL, 1, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_WEY_IP_REMOTE'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 702, 602, 'TAB', 'AI_CONTACT_CENTER_WDP', 'WDP', NULL, 2, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_WDP'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 703, 602, 'TAB', 'AI_CONTACT_CENTER_WEY_SMART_TOUCH_KEYBOARD', 'WEY SMART Touch 키보드', NULL, 3, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_WEY_SMART_TOUCH_KEYBOARD'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 704, 602, 'TAB', 'AI_CONTACT_CENTER_UDS_USB_DESK_SWITCH', 'UDS USB 데스크 스위치', NULL, 4, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_UDS_USB_DESK_SWITCH'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 705, 602, 'TAB', 'AI_CONTACT_CENTER_DISPLAY_BOARD', '전광판', NULL, 5, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_DISPLAY_BOARD'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 706, 602, 'TAB', 'AI_CONTACT_CENTER_MOTION_DESK_SPACE_USE', '모션데스크 (공간활용)', NULL, 6, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_MOTION_DESK_SPACE_USE'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 707, 602, 'TAB', 'AI_CONTACT_CENTER_CABLE_AND_FACILITIES', '케이블/부대설비', NULL, 7, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'AI_CONTACT_CENTER_CABLE_AND_FACILITIES'
);

-- UC솔루션 메뉴
INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 900, NULL, 'MAIN', 'UC_SOLUTION', 'UC솔루션', NULL, 4, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 901, 900, 'SUB', 'UC_SOLUTION_BUSINESS_OVERVIEW', '비즈니스개요', NULL, 1, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_BUSINESS_OVERVIEW'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 902, 900, 'SUB', 'UC_SOLUTION_TEAMS_PHONE', 'Teams Phone', NULL, 2, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_TEAMS_PHONE'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 903, 900, 'SUB', 'UC_SOLUTION_MEETING_SOLUTION', '회의솔루션소개', NULL, 3, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_MEETING_SOLUTION'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 904, 900, 'SUB', 'UC_SOLUTION_SUICIDE_PREVENTION_SYSTEM', '자살예방 시스템', NULL, 4, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_SUICIDE_PREVENTION_SYSTEM'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 905, 900, 'SUB', 'UC_SOLUTION_CUSTOMERS', '주요 고객사', NULL, 5, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_CUSTOMERS'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 1001, 902, 'TAB', 'UC_SOLUTION_CLOUD_PBX', '클라우드 PBX', NULL, 1, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_CLOUD_PBX'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 1002, 903, 'TAB', 'UC_SOLUTION_VIDEO_CONFERENCE_LOGITECH', '화상회의 솔루션 (로지텍)', NULL, 1, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_VIDEO_CONFERENCE_LOGITECH'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 1003, 903, 'TAB', 'UC_SOLUTION_WIRELESS_PRESENTATION_AIRMEDIA', '무선 프레젠테이션 (에어미디어)', NULL, 2, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_WIRELESS_PRESENTATION_AIRMEDIA'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 1004, 903, 'TAB', 'UC_SOLUTION_AUTO_MINUTES_SOLUTION', '자동희의록 솔루션', NULL, 3, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_AUTO_MINUTES_SOLUTION'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 1005, 904, 'TAB', 'UC_SOLUTION_PUBLIC_DATA_METADATA', '공공데이터를 메타데이터 재생성', NULL, 1, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_PUBLIC_DATA_METADATA'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 1006, 904, 'TAB', 'UC_SOLUTION_AI_WITH_METADATA', '메타데이터를 활용한 AI 활용', NULL, 2, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_AI_WITH_METADATA'
);

INSERT INTO its_menu_list (
    menu_id,
    parent_menu_id,
    menu_depth,
    menu_code,
    menu_name,
    menu_path,
    sort_order,
    use_yn,
    del_yn,
    create_date,
    last_update
)
SELECT 1007, 904, 'TAB', 'UC_SOLUTION_AI_CONSULTING_ASSISTANT', 'AI를 이용한 상담 어시스턴트', NULL, 3, 'Y', 'N', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM its_menu_list WHERE menu_code = 'UC_SOLUTION_AI_CONSULTING_ASSISTANT'
);
