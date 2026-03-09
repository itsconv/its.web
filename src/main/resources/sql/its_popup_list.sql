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
