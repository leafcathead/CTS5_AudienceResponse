USE [DB_ARS]
GO
/****** Object:  StoredProcedure [dbo].[LIKE_MESSAGE]    Script Date: 12/19/2022 2:21:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/*
	This stored procedure acts both as insert and delete from the MessageLikes table

*/

ALTER   PROCEDURE [dbo].[LIKE_MESSAGE] (
	@messageID BIGINT,
	@likerID BIGINT,
	@liked BIT OUTPUT
)
AS
BEGIN

	BEGIN TRANSACTION

	IF (NOT EXISTS (SELECT * FROM [SessionUser] su JOIN [Message] m ON m.SessionID = su.SessionID WHERE su.ID = @likerID AND m.ID = @messageID))
	BEGIN
		-- Not in the same session
		RAISERROR('User cannot like a message in a session they do not belong in...', 14, 1)
	--	ROLLBACK TRANSACTION
		RETURN 1;
	END

	IF (EXISTS (SELECT * FROM MessageLikes ml WHERE ml.LikerID = @likerID AND ml.MessageID = @messageID))
	BEGIN
		-- THIS MEANS THAT WE NEED TO UNLIKE
		SET @liked = 0;
		DELETE FROM MessageLikes
		WHERE MessageLikes.LikerID = @likerID AND MessageLikes.MessageID = @messageID

		UPDATE [Message]
		SET [Message].LIKES = [Message].LIKES - 1
		WHERE [Message].ID = @messageID
	END
	ELSE
	BEGIN
		-- WE NEED TO INSERT THE LIKED MESSAGE
		SET @liked = 1;
		INSERT INTO MessageLikes (LikerID, MessageID)
			VALUES (@likerID, @messageID)

		UPDATE [Message]
		SET [Message].LIKES = [Message].LIKES + 1
		WHERE [Message].ID = @messageID
	END

	COMMIT TRANSACTION


END;