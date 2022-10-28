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
			SET @sessionID = 2;
		END


	RETURN 0;
END

SELECT *
FROM SessionRoom

DECLARE @thing1 BIGINT

EXECUTE GET_SESSION_ROOM_ID_FROM_PASSWORD
	@sessionPassword = '1DDF',
	@sessionID = @thing1

SELECT @thing1

SELECT * FROM SessionRoom sr WHERE sr.SessionPassword = '1DDF'