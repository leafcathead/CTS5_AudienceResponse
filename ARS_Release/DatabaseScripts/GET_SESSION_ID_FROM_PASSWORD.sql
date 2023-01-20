/**

	Gets SessionRoom ID from the password

**/

CREATE OR ALTER PROCEDURE GET_SESSION_ROOM_ID_FROM_PASSWORD (
	@sessionPassword nvarchar(4),
	@sessionID BIGINT OUTPUT
)
AS
BEGIN

	IF EXISTS (SELECT * FROM SessionRoom sr WHERE sr.SessionPassword = @sessionPassword)
		BEGIN
			SET @sessionID = (SELECT sr.ID FROM SessionRoom sr WHERE sr.SessionPassword = @sessionPassword)
		END
	ELSE
		BEGIN
			-- Later versions will raise an error here instead.
			SET @sessionID = 0;
		END


	RETURN 0;
END

