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
INSERT INTO gas_station (id, prodcd, area, uni_Id, price, poll_Div_Cd, os_Nm, van_Adr, new_Adr, gisXCoor, gisYCoor, updated_at)
VALUES
    (10001, 'B027', '서울', 'UNI_ID_1', 1500, 'SKE', 'SK에너지', '서울시 강남구 강남대로 123', '강남로 123번길', 37.4979, 127.0276, '2024-01-17'),
    (10002, 'D047', '서울', 'UNI_ID_2', 1600, 'GSC', 'GS칼텍스', '서울시 서초구 서초대로 456', '서초로 456번길', 37.4842, 127.0346, '2024-01-17'),
    (10003, 'B034', '서울', 'UNI_ID_3', 1550, 'HDO', '현대오일뱅크', '서울시 송파구 올림픽로 789', '올림픽로 789번길', 37.5150, 127.1065, '2024-01-17'),
    (10004, 'B027', '서울', 'UNI_ID_4', 1650, 'SOL', 'S-OIL', '서울시 마포구 양화로 321', '양화로 321번길', 37.5555, 126.9123, '2024-01-17'),
    (10005, 'B027', '서울', 'UNI_ID_5', 1550, 'RTE', '자영알뜰', '서울시 동작구 상도로 567', '상도로 567번길', 37.5021, 126.9389, '2024-01-17'),
    (10006, 'B034', '서울', 'UNI_ID_6', 1630, 'RTX', '고속도로 알뜰', '서울시 강서구 공항대로 987', '공항대로 987번길', 37.5598, 126.8025, '2024-01-17'),
    (10007, 'B027', '서울', 'UNI_ID_7', 1600, 'NHO', '농협알뜰', '서울시 중랑구 망우로 432', '망우로 432번길', 37.5903, 127.0754, '2024-01-17'),
    (10008, 'B034', '서울', 'UNI_ID_8', 1580, 'ETC', '자가상표', '서울시 강동구 천호로 654', '천호로 654번길', 37.5502, 127.1435, '2024-01-17'),
    (10009, 'B027', '서울', 'UNI_ID_9', 1570, 'E1G', 'E1', '서울시 강북구 삼양로 789', '삼양로 789번길', 37.6487, 127.0134, '2024-01-17'),
    (10010, 'B034', '서울', 'UNI_ID_10', 1650, 'SKG', 'SK가스2', '서울시 성북구 종암로 123', '종암로 123번길', 37.5931, 127.0354, '2024-01-17'),
    (10011, 'B027', '서울', 'UNI_ID_11', 1500, 'SKE', 'SK에너지2', '서울시 강남구 강남대로 123', '강남로 123번길', 37.4979, 127.0278, '2024-01-17'),
    (10012, 'D047', '서울', 'UNI_ID_12', 1600, 'GSC', 'GS칼텍스2', '서울시 서초구 서초대로 456', '서초로 456번길', 37.4842, 127.0346, '2024-01-17'),
    (10013, 'B034', '서울', 'UNI_ID_13', 1550, 'HDO', '현대오일뱅크2', '서울시 송파구 올림픽로 789', '올림픽로 789번길', 37.5150, 127.1065, '2024-01-17'),
    (10014, 'B027', '서울', 'UNI_ID_14', 1650, 'SOL', 'S-OIL2', '서울시 마포구 양화로 321', '양화로 321번길', 37.5555, 126.9123, '2024-01-17'),
    (10015, 'B027', '서울', 'UNI_ID_15', 1550, 'RTE', '자영알뜰2', '서울시 동작구 상도로 567', '상도로 567번길', 37.5021, 126.9389, '2024-01-17'),
    (10016, 'B034', '서울', 'UNI_ID_16', 1630, 'RTX', '고속도로 알뜰2', '서울시 강서구 공항대로 987', '공항대로 987번길', 37.5598, 126.8025, '2024-01-17'),
    (10017, 'B027', '서울', 'UNI_ID_17', 1600, 'NHO', '농협알뜰2', '서울시 중랑구 망우로 432', '망우로 432번길', 37.5903, 127.0754, '2024-01-17'),
    (10018, 'B034', '서울', 'UNI_ID_18', 1580, 'ETC', '자가상표2', '서울시 강동구 천호로 654', '천호로 654번길', 37.5502, 127.1435, '2024-01-17'),
    (10019, 'B027', '서울', 'UNI_ID_19', 1570, 'E1G', 'E12', '서울시 강북구 삼양로 789', '삼양로 789번길', 37.6487, 127.0134, '2024-01-17'),
    (10020, 'B034', '서울', 'UNI_ID_20', 1650, 'SKG', 'SK가스3', '서울시 성북구 종암로 123', '종암로 12번길', 37.5932, 127.0355, '2024-01-17');
