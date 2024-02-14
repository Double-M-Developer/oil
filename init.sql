SET GLOBAL event_scheduler = ON;

CREATE EVENT IF NOT EXISTS clean_expired_tokens
ON SCHEDULE EVERY 1 DAY
COMMENT 'Removes expired or revoked tokens every day.'
DO
BEGIN
DELETE FROM token
WHERE expired = TRUE OR revoked = TRUE;
END;