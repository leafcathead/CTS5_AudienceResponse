USE [DB_ARS]
GO
/****** Object:  StoredProcedure [dbo].[INSERT_PANIC]    Script Date: 1/11/2023 1:40:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**

This SPROC adds a panic responses

Created by: Connor Peper

**/

ALTER   PROCEDURE [dbo].[INSERT_PANIC] (
	@Panicker BIGINT,
	@SessionRoom BIGINT,
	@PanicButtonPushed CHAR(4)
)
AS
BEGIN

	-- Validate parameters

	IF (NOT EXISTS (SELECT * FROM SessionUser su WHERE su.ID = @panicker AND su.SessionID = @SessionRoom))
	BEGIN
		RAISERROR('User does not belong to that session.',14,1);
		RETURN 1;
	END

	-- Check that panic button abbrev is correct

	IF (NOT EXISTS (SELECT * FROM PanicButton pb WHERE pb.PanicType = @PanicButtonPushed))
	BEGIN
		RAISERROR('Invalid panic button...',14,2);
		RETURN 2;

	END

	DECLARE @panicButtonID BIGINT
	SET @panicButtonID = (SELECT pb.ID FROM PanicButton pb WHERE pb.PanicType = @PanicButtonPushed) -- Allowed because pb.PanicType is UNIQUE.

	-- Validation complete. Remove the old response and then insert the new one.

	BEGIN TRANSACTION

	DECLARE @NewID BIGINT

	DELETE FROM PanicResponse -- Ensures that only one response per user at a time.
	WHERE PanicResponse.Panicker = @Panicker

	INSERT INTO PanicResponse (PanicButtonPushed, Panicker, SessionRoom)
		VALUES(@PanicButtonID, @Panicker, @SessionRoom)

	SET @NewID = @@IDENTITY


	COMMIT TRANSACTION
	

	RETURN 0;
END