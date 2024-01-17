-- Member 1
INSERT INTO member (id, user_id, password, username, nickname, email, role, issue_date, user_status)
VALUES (10001, 'user1', 'password1', 'John Doe', 'Johnny', 'john@example.com', 'USER', '2024-01-17', 'NORMAL');

-- Member 2
INSERT INTO member (id, user_id, password, username, nickname, email, role, issue_date, user_status)
VALUES (10002, 'user2', 'password2', 'Jane Doe', 'Janie', 'jane@example.com', 'USER', '2024-01-12', 'NORMAL');

-- Member 3
INSERT INTO member (id, user_id, password, username, nickname, email, role, issue_date, user_status)
VALUES (10003, 'user3', 'password3', 'Alice Smith', 'Alicia', 'alice@example.com', 'USER', '2024-01-10', 'NORMAL');

-- Member 4
INSERT INTO member (id, user_id, password, username, nickname, email, role, issue_date, user_status)
VALUES (10004, 'user4', 'password4', 'Bob Johnson', 'Bobby', 'bob@example.com', 'USER', '2024-01-14', 'NORMAL');

-- Member 5
INSERT INTO member (id, user_id, password, username, nickname, email, role, issue_date, user_status)
VALUES (10005, 'user5', 'password5', 'Eve Brown', 'Evie', 'eve@example.com', 'USER', '2024-01-18', 'NORMAL');

-- Gas Station
-- GasStation 테이블에 데이터 추가
INSERT INTO gas_station (uni_id, area, poll_div_cd, os_nm, van_adr, new_adr, gis_x_coor, gis_y_coor)
VALUES
    ('UNI_ID_1', '서울', 'SKE', 'SK에너지', '서울시 강남구 강남대로 123', '강남로 123번길', 37.4979, 127.0276),
    ('UNI_ID_2', '서울', 'GSC', 'GS칼텍스', '서울시 서초구 서초대로 456', '서초로 456번길', 37.4842, 127.0346),
    ('UNI_ID_3', '서울', 'HDO', '현대오일뱅크', '서울시 송파구 올림픽로 789', '올림픽로 789번길', 37.5150, 127.1065),
    ('UNI_ID_4', '서울', 'SOL', 'S-OIL', '서울시 마포구 양화로 321', '양화로 321번길', 37.5555, 126.9123),
    ('UNI_ID_5', '서울', 'RTE', '자영알뜰', '서울시 동작구 상도로 567', '상도로 567번길', 37.5021, 126.9389),
    ('UNI_ID_6', '서울', 'RTX', '고속도로 알뜰', '서울시 강서구 공항대로 987', '공항대로 987번길', 37.5598, 126.8025),
    ('UNI_ID_7', '서울', 'NHO', '농협알뜰', '서울시 중랑구 망우로 432', '망우로 432번길', 37.5903, 127.0754),
    ('UNI_ID_8', '서울', 'ETC', '자가상표', '서울시 강동구 천호로 654', '천호로 654번길', 37.5502, 127.1435),
    ('UNI_ID_9', '서울', 'E1G', 'E1', '서울시 강북구 삼양로 789', '삼양로 789번길', 37.6487, 127.0134),
    ('UNI_ID_10', '서울', 'SKG', 'SK가스2', '서울시 성북구 종암로 123', '종암로 123번길', 37.5931, 127.0354);

-- GasStationPrice 테이블에 데이터 추가
INSERT INTO gas_station_price (price_id, uni_id, prodcd, price, updated_at)
VALUES
    (1, 'UNI_ID_1', 'B027', 1500, '2024-01-17 12:00:00'),
    (2, 'UNI_ID_1', 'B027', 1600, '2024-01-17 13:00:00'),
    (3, 'UNI_ID_2', 'D047', 1600, '2024-01-17 12:00:00'),
    (4, 'UNI_ID_2', 'D047', 1000, '2024-01-17 13:00:00'),
    (5, 'UNI_ID_3', 'B034', 1550, '2024-01-17 12:00:00'),
    (6, 'UNI_ID_3', 'B034', 1450, '2024-01-17 13:00:00'),
    (7, 'UNI_ID_4', 'B027', 1650, '2024-01-17 12:00:00'),
    (8, 'UNI_ID_4', 'B027', 1650, '2024-01-17 12:00:00'),
    (9, 'UNI_ID_4', 'B034', 1750, '2024-01-17 13:00:00'),
    (10, 'UNI_ID_4', 'B034', 1650, '2024-01-17 12:00:00'),
    (11, 'UNI_ID_5', 'B027', 1150, '2024-01-17 12:00:00'),
    (12, 'UNI_ID_5', 'B027', 1250, '2024-01-17 13:00:00'),
    (13, 'UNI_ID_6', 'B034', 1630, '2024-01-17 12:00:00'),
    (14, 'UNI_ID_6', 'B027', 1630, '2024-01-17 12:00:00'),
    (15, 'UNI_ID_7', 'B027', 1620, '2024-01-17 12:00:00'),
    (16, 'UNI_ID_7', 'B034', 1630, '2024-01-17 12:00:00'),
    (17, 'UNI_ID_7', 'B047', 1650, '2024-01-17 12:00:00'),
    (18, 'UNI_ID_8', 'B034', 1580, '2024-01-17 12:00:00'),
    (19, 'UNI_ID_9', 'B027', 1570, '2024-01-17 12:00:00'),
    (20, 'UNI_ID_10', 'B034', 1650, '2024-01-17 12:00:00');