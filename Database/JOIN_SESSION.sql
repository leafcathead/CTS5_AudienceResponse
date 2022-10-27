/**

This stored procedure creates a new user and adds them to their specified session.


**/

CREATE OR ALTER PROCEDURE JOIN_SESSION (
	@sessionID BIGINT,
	@newUserID BIGINT OUTPUT
)
AS
BEGIN
	
	-- Create the new user
	INSERT INTO SessionUser (DisplayName, SessionID)
		VALUES('New User', @sessionID)

	SET @newUserID = @@IDENTITY;

	UPDATE SessionUser
	SET DisplayName = CONCAT('User#', @newUserID)
	WHERE ID = @newUserID


	RETURN 0;
END
