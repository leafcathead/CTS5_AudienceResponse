USE [DB_ARS]
GO
/****** Object:  StoredProcedure [dbo].[CLOSE_SESSION]    Script Date: 12/16/2022 1:27:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**

This stored procedure is ran when the session owner closes a session.

**/

ALTER   PROCEDURE [dbo].[CLOSE_SESSION] (
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

	DECLARE MESSAGE_CURSOR CURSOR LOCAL FOR
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
	CLOSE MESSAGE_CURSOR
	DEALLOCATE MESSAGE_CURSOR

	-- Delete all users

	DELETE FROM SessionUser
	WHERE SessionUser.SessionID = @sessionID

	-- Delete the session itself

	DELETE FROM SessionRoom
	WHERE SessionRoom.ID = @sessionID

	COMMIT TRANSACTION


	RETURN 0;
END