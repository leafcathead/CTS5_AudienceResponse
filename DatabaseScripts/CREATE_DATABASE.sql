USE [master]
GO
/****** Object:  Database [DB_ARS]    Script Date: 11.01.2023 14:14:36 ******/
CREATE DATABASE [DB_ARS]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DB_ARS', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\DB_ARS.mdf' , SIZE = 81920KB , MAXSIZE = 512000KB , FILEGROWTH = 12%)
 LOG ON 
( NAME = N'DB_ARSLog', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\DB_ARSLog.ldf' , SIZE = 3072KB , MAXSIZE = 22528KB , FILEGROWTH = 17%)
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [DB_ARS] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DB_ARS].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DB_ARS] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DB_ARS] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DB_ARS] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DB_ARS] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DB_ARS] SET ARITHABORT OFF 
GO
ALTER DATABASE [DB_ARS] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [DB_ARS] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DB_ARS] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DB_ARS] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DB_ARS] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DB_ARS] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DB_ARS] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DB_ARS] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DB_ARS] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DB_ARS] SET  ENABLE_BROKER 
GO
ALTER DATABASE [DB_ARS] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DB_ARS] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DB_ARS] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DB_ARS] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DB_ARS] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DB_ARS] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DB_ARS] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DB_ARS] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [DB_ARS] SET  MULTI_USER 
GO
ALTER DATABASE [DB_ARS] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DB_ARS] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DB_ARS] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DB_ARS] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DB_ARS] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DB_ARS] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [DB_ARS] SET QUERY_STORE = OFF
GO
USE [DB_ARS]
GO
USE [DB_ARS]
GO
/****** Object:  Sequence [dbo].[hibernate_sequence]    Script Date: 11.01.2023 14:14:36 ******/
CREATE SEQUENCE [dbo].[hibernate_sequence] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
/****** Object:  Table [dbo].[BugReport]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BugReport](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Category] [char](50) NOT NULL,
	[BugText] [char](500) NULL,
	[Status] [char](50) NULL,
	[Timestamp] [smalldatetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Message]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Message](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[SessionID] [bigint] NULL,
	[MsgContent] [varchar](500) NOT NULL,
	[PosterID] [bigint] NULL,
	[ReplyTo] [bigint] NULL,
	[LIKES] [int] NOT NULL,
	[IsApproved] [bit] NULL,
	[Timestamp] [datetime2](7) NOT NULL,
 CONSTRAINT [PK__Message__3214EC27ACCC4325] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MessageLikes]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MessageLikes](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[LikerID] [bigint] NULL,
	[MessageID] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PanicButton]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PanicButton](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PanicType] [char](4) NOT NULL,
	[ButtonDesc] [nvarchar](150) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[PanicType] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PanicResponse]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PanicResponse](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[PanicButtonPushed] [bigint] NOT NULL,
	[Panicker] [bigint] NOT NULL,
	[SessionRoom] [bigint] NOT NULL,
	[LogTime] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SessionRoom]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SessionRoom](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[OwnerID] [bigint] NULL,
	[SessionPassword] [char](25) NOT NULL,
	[Timestamp] [smalldatetime] NOT NULL,
 CONSTRAINT [PK__SessionR__3214EC27F47508B2] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SessionUser]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SessionUser](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[DisplayName] [nvarchar](20) NOT NULL,
	[SessionID] [bigint] NULL,
 CONSTRAINT [PK__SessionU__3214EC27D7017263] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Message] ADD  CONSTRAINT [DF_Message_LIKES]  DEFAULT ((0)) FOR [LIKES]
GO
ALTER TABLE [dbo].[Message] ADD  CONSTRAINT [DF__Message__IS_APPR__3E1D39E1]  DEFAULT ((0)) FOR [IsApproved]
GO
ALTER TABLE [dbo].[Message] ADD  CONSTRAINT [DF_Message_Timestamp]  DEFAULT (getdate()) FOR [Timestamp]
GO
ALTER TABLE [dbo].[PanicResponse] ADD  DEFAULT (getdate()) FOR [LogTime]
GO
ALTER TABLE [dbo].[Message]  WITH CHECK ADD  CONSTRAINT [FK__Message__PosterI__3D2915A8] FOREIGN KEY([PosterID])
REFERENCES [dbo].[SessionUser] ([ID])
GO
ALTER TABLE [dbo].[Message] CHECK CONSTRAINT [FK__Message__PosterI__3D2915A8]
GO
ALTER TABLE [dbo].[Message]  WITH CHECK ADD  CONSTRAINT [FK__Message__Session__3C34F16F] FOREIGN KEY([SessionID])
REFERENCES [dbo].[SessionRoom] ([ID])
GO
ALTER TABLE [dbo].[Message] CHECK CONSTRAINT [FK__Message__Session__3C34F16F]
GO
ALTER TABLE [dbo].[MessageLikes]  WITH CHECK ADD FOREIGN KEY([LikerID])
REFERENCES [dbo].[SessionUser] ([ID])
GO
ALTER TABLE [dbo].[MessageLikes]  WITH CHECK ADD FOREIGN KEY([MessageID])
REFERENCES [dbo].[Message] ([ID])
GO
ALTER TABLE [dbo].[PanicResponse]  WITH CHECK ADD FOREIGN KEY([PanicButtonPushed])
REFERENCES [dbo].[PanicButton] ([ID])
GO
ALTER TABLE [dbo].[PanicResponse]  WITH CHECK ADD FOREIGN KEY([Panicker])
REFERENCES [dbo].[SessionUser] ([ID])
GO
ALTER TABLE [dbo].[PanicResponse]  WITH CHECK ADD FOREIGN KEY([SessionRoom])
REFERENCES [dbo].[SessionRoom] ([ID])
GO
ALTER TABLE [dbo].[SessionRoom]  WITH CHECK ADD  CONSTRAINT [FK__SessionRo__Owner__3864608B] FOREIGN KEY([OwnerID])
REFERENCES [dbo].[SessionUser] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SessionRoom] CHECK CONSTRAINT [FK__SessionRo__Owner__3864608B]
GO
/****** Object:  StoredProcedure [dbo].[addStudent]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROCEDURE [dbo].[addStudent] (
	@displayName NVARCHAR(20)
)
AS
BEGIN
	
	--Checks to make sure name is unique
	IF (EXISTS (SELECT * FROM Student s WHERE s.DisplayName = @displayName))
		BEGIN
			RAISERROR('Display name is not unique', 10, 10);
			RETURN 1;
		END

	IF ('francis' = @displayName)
		BEGIN
			RAISERROR('Testing error code handling', 10, 2);
			THROW 237820, 'Testing error code handling', 1;
			--SELECT 'Gamer fuel'
			RETURN 2;
		END

	INSERT INTO Student (DisplayName)
		VALUES(@displayName)

	RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[CLOSE_SESSION]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**

This stored procedure is ran when the session owner closes a session.

**/

CREATE   PROCEDURE [dbo].[CLOSE_SESSION] (
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
GO
/****** Object:  StoredProcedure [dbo].[CREATE_SESSION]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[CREATE_SESSION] (
	@newSessionID BIGINT OUTPUT,
	@newUserID BIGINT OUTPUT,
	@randomPassword nvarchar(4) OUTPUT
)
AS
BEGIN

	BEGIN TRANSACTION

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
	WHERE ID = @newUserID

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
/****** Object:  StoredProcedure [dbo].[DELETE_MESSAGE]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**

This SPROC deletes a message from the database.

Created by: Connor Peper

**/

CREATE   PROCEDURE [dbo].[DELETE_MESSAGE] (
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
GO
/****** Object:  StoredProcedure [dbo].[FAVORITE_NUM]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[FAVORITE_NUM] (
   @useless int = 0,
   @favoriteNum INT OUTPUT
)
AS
BEGIN
	SET @favoriteNum = 10

	RETURN 1;
END
GO
/****** Object:  StoredProcedure [dbo].[FLIP_VISIBILITY]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**

This SPROC flips the visibility of a message.

Created by: Connor Peper

**/

CREATE   PROCEDURE [dbo].[FLIP_VISIBILITY] (
	@messageID BIGINT,
	@posterID BIGINT,
	@sessionID BIGINT
)
AS
BEGIN

	--Validate parameters.
	IF (NOT EXISTS(SELECT * FROM [Message] m WHERE m.ID = @messageID AND m.PosterID = @posterID AND m.SessionID = @sessionID))
	BEGIN
		RAISERROR('User does not belong to that session or message does not exist...', 14, 1);
		RETURN 1;
	END

	-- Validation complete, begin update
	UPDATE [Message]
	SET IsApproved = IsApproved ^ 1
	WHERE ID = @messageID

	RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[GET_PANIC_RESPONSES]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE   PROCEDURE [dbo].[GET_PANIC_RESPONSES] (
	@sessionID BIGINT
)
AS
BEGIN

	SELECT *
	FROM PanicResponse pr
	WHERE pr.SessionRoom = @sessionID


	RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[GET_SESSION_ROOM_ID_FROM_PASSWORD]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[GET_SESSION_ROOM_ID_FROM_PASSWORD] (
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
			RAISERROR('Session does not exist...', 14, 3);
		END


	RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[INSERT_MESSAGE]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE     PROCEDURE [dbo].[INSERT_MESSAGE](

 @SessionID  BIGINT,
 @PosterID   BIGINT,
 @MsgContent NVARCHAR(500),
 @MessageID  BIGINT OUTPUT

  
)
AS
BEGIN 
     IF (NOT EXISTS (SELECT* FROM SessionRoom r WHERE r.ID = @SessionID  ) )
       BEGIN
       RAISERROR ('Session does not exists' , 16 ,1)
       RETURN 1;



       END
        IF (NOT EXISTS (SELECT* FROM SessionUser m WHERE m.ID = @PosterID  ) )
        BEGIN
        RAISERROR ('Poster does not exists' , 16 ,2)
        RETURN 2;

     




     END
     IF (NOT EXISTS (SELECT* FROM SessionUser u WHERE u.SessionID = @SessionID AND u.ID = @PosterID ) )
       BEGIN
       RAISERROR ('Poster does not belong to that session' , 16 ,3)
       RETURN 3;

         END
         INSERT INTO [MESSAGE] (SessionID, MsgContent, PosterID, [Timestamp])
         VALUES (@SessionID, @MsgContent, @PosterID,CURRENT_TIMESTAMP)
         SET @MessageID = @@IDENTITY
         RETURN 0; 
         
END 
         
   
GO
/****** Object:  StoredProcedure [dbo].[INSERT_PANIC]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**

This SPROC adds a panic responses

Created by: Connor Peper

**/

CREATE   PROCEDURE [dbo].[INSERT_PANIC] (
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
GO
/****** Object:  StoredProcedure [dbo].[INSERT_REPLY]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**
This Stored Procedure handles the insertion of replies into the database.

Created by: Connor Peper
**/

CREATE   PROCEDURE [dbo].[INSERT_REPLY] (
	@posterID BIGINT,
	@sessionID BIGINT,
	@replyToID BIGINT,
	@msgContent NVARCHAR(500),
	@newMessageID BIGINT OUTPUT
)
AS
BEGIN

	-- Validation of parameters.
	
	-- Check to see if the Poster belongs to that session
	IF (NOT EXISTS (SELECT * 
					FROM SessionUser su
					JOIN SessionRoom sr ON sr.ID = su.SessionID
					WHERE su.ID = @posterID AND su.SessionID = @sessionID))
	BEGIN
		RAISERROR('User does not belong to that session or user or session does not exist', 14, 1)
		RETURN 1;
	END

	-- Check Message exists within that session
	IF (NOT EXISTS (SELECT *
					FROM [Message] m
					WHERE m.SessionID = @sessionID AND m.ID = @replyToID))
	BEGIN
		RAISERROR('Message does not belong to that session or does not exist.', 14, 2);
		RETURN 2;
	END
			
	-- Validation complete, commence insert.
	
	INSERT INTO [Message] (SessionID, MsgContent, PosterID, ReplyTo, [Timestamp])
		VALUES(@sessionID, @msgContent, @posterID, @replyToID, CURRENT_TIMESTAMP)

	SET @newMessageID = @@IDENTITY

	RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[JOIN_SESSION]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[JOIN_SESSION] (
	@sessionID BIGINT,
	@newUserID BIGINT OUTPUT
)
AS
BEGIN
	
	BEGIN TRANSACTION

	-- Create the new user
	INSERT INTO SessionUser (DisplayName, SessionID)
		VALUES('New User', @sessionID)

	SET @newUserID = @@IDENTITY;

	UPDATE SessionUser
	SET DisplayName = CONCAT('User#', @newUserID)
	WHERE ID = @newUserID

	COMMIT TRANSACTION

	RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[LIKE_MESSAGE]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/*
	This stored procedure acts both as insert and delete from the MessageLikes table

*/

CREATE   PROCEDURE [dbo].[LIKE_MESSAGE] (
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
GO
/****** Object:  StoredProcedure [dbo].[MY_PROCEDURE]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE   PROCEDURE [dbo].[MY_PROCEDURE]
AS
BEGIN


  -- PROCEDURE CODE HERE
  RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[RETRIEVE_MESSAGES]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/***
This stored procedure retrieves all the messages given sessionID
***/

CREATE   PROCEDURE [dbo].[RETRIEVE_MESSAGES] (
	@sessionID BIGINT
)
AS
BEGIN

	if(NOT EXISTS(SELECT * FROM SessionRoom WHERE ID = @sessionID))
		BEGIN
			RAISERROR('Session does not exist!', 16, 7)
			RETURN 7
		END

	SET NOCOUNT ON;

	-- get Messages according to given sessionID

	SELECT * 
	FROM [MESSAGE] m 
	WHERE SessionID = @sessionID
	ORDER BY m.Timestamp

	/** Alternative for ease of reading:
	SELECT m.ID, m.ReplyTo, m.MsgContent, m.LIKES, m.IsApproved, m.PosterID, su.DisplayName
	FROM [Message] m
	JOIN SessionUser su ON m.PosterID = su.ID
	JOIN SessionRoom sr ON m.SessionID = sr.ID
	WHERE m.SessionID = @sessionID
	ORDER BY m.Timestamp DESC
	
	**/

--	RETURN 0

END

GO
/****** Object:  StoredProcedure [dbo].[SUBMIT_PANIC_RESPONSE]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**

This SPROC adds a panic responses

Created by: Connor Peper

**/

CREATE   PROCEDURE [dbo].[SUBMIT_PANIC_RESPONSE] (
	@userID BIGINT,
	@sessionID BIGINT,
	@panicButtonAbbrev CHAR(4)
)
AS
BEGIN

	-- Validate parameters

	IF (NOT EXISTS (SELECT * FROM SessionUser su WHERE su.ID = @userID AND su.SessionID = @sessionID))
	BEGIN
		RAISERROR('User does not belong to that session.',14,1);
		RETURN 1;
	END

	-- Check that panic button abbrev is correct

	IF (NOT EXISTS (SELECT * FROM PanicButton pb WHERE pb.PanicType = @panicButtonAbbrev))
	BEGIN
		RAISERROR('Invalid panic button...',14,2);
		RETURN 2;

	END

	DECLARE @panicButtonID BIGINT
	SET @panicButtonID = (SELECT pb.ID FROM PanicButton pb WHERE pb.PanicType = @panicButtonAbbrev) -- Allowed because pb.PanicType is UNIQUE.

	-- Validation complete. Remove the old response and then insert the new one.

	BEGIN TRANSACTION

	DELETE FROM PanicResponse -- Ensures that only one response per user at a time.
	WHERE PanicResponse.Panicker = @userID

	INSERT INTO PanicResponse (PanicButtonPushed, Panicker, SessionRoom)
		VALUES(@panicButtonID, @userID, @sessionID)

	COMMIT TRANSACTION
	

	RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[UPDATE_DISPLAY_NAME]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**

This SPROC updates a users display name.

Created by: Connor Peper

**/

CREATE   PROCEDURE [dbo].[UPDATE_DISPLAY_NAME] (
	@userID BIGINT,
	@sessionID BIGINT,
	@newName NVARCHAR(20)
)
AS
BEGIN
	-- Note to self! Update the return of GetMessages to return the display name!

	-- Validate parameters

	IF (NOT EXISTS (SELECT * FROM SessionUser su WHERE su.ID = @userID AND su.SessionID = @sessionID))
	BEGIN
		RAISERROR('User does not belong to that session...', 14, 1);
		RETURN 1;
	END

	-- Check for forbidden words?

	-- Validation complete, perform update on the display name.

	UPDATE SessionUser
	SET DisplayName = @newName
	WHERE ID = @userID


	RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[UPDATE_MESSAGE]    Script Date: 11.01.2023 14:14:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**

This Stored Procedure will update the message content of a given message ID

Created by: Connor Peper
**/

CREATE   PROCEDURE [dbo].[UPDATE_MESSAGE] (
	@messageID BIGINT,
	@posterID BIGINT,
	@sessionID BIGINT,
	@newBody NVARCHAR(500)
)
AS
BEGIN
	
	-- Parameter validation

	-- Checks to make sure the poster, session, and message exist

	IF (NOT EXISTS (SELECT * FROM [Message] m WHERE m.ID = @messageID AND m.PosterID = @posterID AND m.SessionID = @sessionID))
	BEGIN
		RAISERROR('Poster does not belong to that session or the message does not exist.', 16, 1)
		RETURN 1;
	END

	-- Validation complete. Time to do the update

	UPDATE [Message]
	SET [Message].MsgContent = @newBody
	WHERE [Message].ID = @messageID

	UPDATE [Message]
	SET [Message].[Timestamp] = GETDATE()
	WHERE [Message].ID = @messageID

	RETURN 0;
END
GO


-- Insert some data

/**

Types of Panic Buttons

Created by: Connor Peper

Users cannot create their own panic buttons so this should all be done manually.

**/

USE DB_ARS

-- Talking too fast

INSERT INTO PanicButton (PanicType, [ButtonDesc])
	VALUES('2FST', 'Push this button if the speaker is speaking too quickly and you would like them to slow down.')


-- Talking too quietly

INSERT INTO PanicButton (PanicType, [ButtonDesc])
	VALUES('2QIT','Push this button if the speaker is speaking too quietly and you would lie them to speak up.')

-- Repeat!

INSERT INTO PanicButton (PanicType, [ButtonDesc])
	VALUES('REPT', 'Push this button if you would like the speaker to repeat what he has just said.')

-- Explain Further

INSERT INTO PanicButton (PanicType, [ButtonDesc])
	VALUES('EXPL','Push this button if you would like the speaker to go into more detail about the current topic.')

-- Please wait

INSERT INTO PanicButton(PanicType, [ButtonDesc])
	VALUES('WAIT', 'Push this button if you want the speaker to temporarily stop speaking so that you can catch up.')

-- General Panic

INSERT INTO PanicButton (PanicType, [ButtonDesc])
	VALUES('GENP','Push this button if your concern does not fall into the realm of the other buttons.')



USE [master]
GO
ALTER DATABASE [DB_ARS] SET  READ_WRITE 
GO

