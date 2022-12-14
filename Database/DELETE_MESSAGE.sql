USE [DB_ARS]
GO
/****** Object:  StoredProcedure [dbo].[DELETE_MESSAGE]    Script Date: 12/16/2022 12:48:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**

This SPROC deletes a message from the database.

Created by: Connor Peper

**/

ALTER   PROCEDURE [dbo].[DELETE_MESSAGE] (
	@messageID BIGINT,
	@posterID BIGINT,
	@sessionID BIGINT
)
AS
BEGIN
	
	-- Validate parameters
	IF (NOT EXISTS (SELECT * FROM [Message] m WHERE m.ID = @messageID AND m.PosterID = @posterID AND m.SessionID = @sessionID))
	BEGIN
		RAISERROR('Poster or message does not belong to that session or they do not exist.', 14, 1);
		RETURN 1;
	END

	-- Create a temporary table to store the reply chain IDs

	

	CREATE TABLE #ReplyChain (DeleteID BIGINT)

	INSERT INTO #ReplyChain (DeleteID)
		VALUES(@messageID);

	DECLARE DELETE_CURSOR CURSOR FOR
	SELECT *
	FROM #ReplyChain
	OPEN DELETE_CURSOR
	FETCH NEXT FROM DELETE_CURSOR INTO @messageID;
	WHILE @@FETCH_STATUS = 0
	BEGIN
		INSERT INTO #ReplyChain
		SELECT m.ID
		FROM [Message] m
		WHERE m.ReplyTo = @messageID

		FETCH NEXT FROM DELETE_CURSOR INTO @messageID
	END

	BEGIN TRANSACTION

	DELETE FROM MessageLikes
	WHERE EXISTS (SELECT * FROM #ReplyChain rc WHERE rc.DeleteID = MessageLikes.MessageID)

	-- Validation complete, begin deletion.
	DELETE FROM [Message]
	WHERE EXISTS (SELECT * FROM #ReplyChain rc WHERE rc.DeleteID = Message.ID) 

	COMMIT TRANSACTION

	RETURN 0;
END