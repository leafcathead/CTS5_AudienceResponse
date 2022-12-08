/**

This stored procedure is ran when the session owner closes a session.

**/

CREATE OR ALTER PROCEDURE CLOSE_SESSION (
	@sessionID BIGINT
)
AS
BEGIN

	-- First validate parameter

	IF (NOT EXISTS (SELECT * FROM SessionRoom sr WHERE sr.ID = @sessionID))
	BEGIN
		RAISERROR('Session does not exist...', 14, 1);
		RETURN 1;
	END

	-- Begin deletion of everything.

	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE -- Want to make sure all data is deleted so we're being extreme in our isolation.

	BEGIN TRANSACTION

	-- Delete all panic responses
	DELETE FROM PanicResponse
	WHERE PanicResponse.SessionRoom = @sessionID

	-- Delete all messages and replies
	-- Need to use the SPROC for this so that it works right.

	DECLARE @mID BIGINT
	DECLARE @pID BIGINT

	DECLARE MESSAGE_CURSOR CURSOR FOR
	SELECT m.ID, m.PosterID
	FROM [Message] m
	WHERE m.SessionID = @sessionID AND m.ReplyTo IS NULL;
	OPEN MESSAGE_CURSOR
	FETCH NEXT FROM MESSAGE_CURSOR INTO @mID, @pID;
	WHILE @@FETCH_STATUS = 0
	BEGIN
		EXECUTE DELETE_MESSAGE
			@messageID = @mID,
			@posterID = @pID,
			@sessionID = @sessionID

		FETCH NEXT FROM MESSAGE_CURSOR INTO @mID, @pID;

	END;

	-- Delete all users

	DELETE FROM SessionUser
	WHERE SessionUser.SessionID = @sessionID

	-- Delete the session itself

	DELETE FROM SessionRoom
	WHERE SessionRoom.ID = @sessionID

	COMMIT TRANSACTION


	RETURN 0;
END