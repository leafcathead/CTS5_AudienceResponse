/***

This stored procedure creates a session and a user


***/
Use ARS_test
GO

CREATE OR ALTER PROCEDURE CREATE_SESSION (
	@newSessionID BIGINT OUTPUT,
	@newUserID BIGINT OUTPUT
)
AS
BEGIN

	BEGIN TRANSACTION
	DECLARE @randomPassword nvarchar(4)
	SET @randomPassword = SUBSTRING(CONVERT(varchar(255), NEWID()), 0, 5)

	WHILE (EXISTS (SELECT * FROM SessionRoom sr WHERE sr.SessionPassword = @randomPassword))
	BEGIN
		SET @randomPassword = SUBSTRING(CONVERT(varchar(255), NEWID()), 0, 5);
	END

	-- Password is guaranteed to be unique now.

	-- Insert into SessionUser first

	INSERT INTO SessionUser (DisplayName)
		VALUES('NewUser');

	SET @newUserID = @@IDENTITY

	UPDATE SessionUser
	SET DisplayName = CONCAT('User#', @newUserID)
	WHERE ID = @newSessionID

	-- Insert into SessionRoom now

	INSERT INTO SessionRoom (OwnerID, SessionPassword, [Timestamp])
		VALUES(@newUserID, @randomPassword, CURRENT_TIMESTAMP)

	SET @newSessionID = @@IDENTITY

	-- Update the session field on SessionUser

	UPDATE SessionUser
	SET SessionID = @newSessionID
	WHERE ID = @newUserID

	COMMIT TRANSACTION;

	RETURN 0;
END
GO

DECLARE @thing1 BIGINT;
DECLARE @thing2 BIGINT;
DECLARE @thing3 nvarchar(4);
EXECUTE CREATE_SESSION
	@newSessionID = @thing1 OUTPUT,
	@newUserID = @thing2 OUTPUT,
	@randomPassword = @thing3 OUTPUT

SELECT @thing1 as 'Session ID', @thing2 as 'User ID', @thing3 as 'Pass'
