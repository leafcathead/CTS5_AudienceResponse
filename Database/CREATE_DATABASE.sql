USE [master]
GO
/****** Object:  Database [DB_ARS]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  Sequence [dbo].[hibernate_sequence]    Script Date: 11/18/2022 1:18:00 PM ******/
CREATE SEQUENCE [dbo].[hibernate_sequence] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
/****** Object:  Table [dbo].[BugReport]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  Table [dbo].[Message]    Script Date: 11/18/2022 1:18:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Message](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[SessionID] [bigint] NULL,
	[MsgContent] [varchar](500) NOT NULL,
	[PosterID] [bigint] NULL,
	[ReplyTo] [char](12) NULL,
	[LIKES] [int] NOT NULL,
	[IsApproved] [bit] NULL,
	[Timestamp] [datetime2](7) NOT NULL,
 CONSTRAINT [PK__Message__3214EC27ACCC4325] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SessionRoom]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  Table [dbo].[SessionUser]    Script Date: 11/18/2022 1:18:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SessionUser](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[DisplayName] [char](12) NOT NULL,
	[SessionID] [bigint] NULL,
 CONSTRAINT [PK__SessionU__3214EC27D7017263] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Student]    Script Date: 11/18/2022 1:18:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Student](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[DisplayName] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
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
ALTER TABLE [dbo].[SessionRoom]  WITH CHECK ADD  CONSTRAINT [FK__SessionRo__Owner__3864608B] FOREIGN KEY([OwnerID])
REFERENCES [dbo].[SessionUser] ([ID])
GO
ALTER TABLE [dbo].[SessionRoom] CHECK CONSTRAINT [FK__SessionRo__Owner__3864608B]
GO
ALTER TABLE [dbo].[SessionUser]  WITH CHECK ADD  CONSTRAINT [FKge1q1ygerj3sh1etcuif0m68g] FOREIGN KEY([SessionID])
REFERENCES [dbo].[SessionRoom] ([ID])
GO
ALTER TABLE [dbo].[SessionUser] CHECK CONSTRAINT [FKge1q1ygerj3sh1etcuif0m68g]
GO
/****** Object:  StoredProcedure [dbo].[addStudent]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  StoredProcedure [dbo].[CREATE_MESSAGE]    Script Date: 11/18/2022 1:18:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/***
This stored procedure creates a reply
***/

CREATE   PROCEDURE [dbo].[CREATE_MESSAGE] (
	@posterID BIGINT,
	@sessionID BIGINT,
	@repliedToMessageID BIGINT,
	@message nvarchar(500),
	@newMessageID BIGINT OUTPUT
)
AS
BEGIN

	-- Session and user existing check

	BEGIN TRANSACTION

	if(NOT EXISTS (SELECT * FROM SessionRoom WHERE SessionRoom.ID = @sessionID))
		BEGIN
			RAISERROR('Session does not exist!', 16, 1)
			RETURN 1
		END

	if(NOT EXISTS (SELECT * FROM SessionUser WHERE SessionUser.ID = @posterID) OR
		NOT EXISTS(SELECT * FROM SessionUser WHERE SessionUser.ID = @repliedToMessageID))
		BEGIN
			RAISERROR('User does not exist!', 16, 2)
			RETURN 2
		END

	if((SELECT SessionID FROM SessionUser WHERE ID = @posterID) <> @sessionID)
		BEGIN
			RAISERROR('User does not belong to this session!', 16, 3)
			RETURN 3
		END

	if((SELECT SessionID FROM SessionUser WHERE ID = @repliedToMessageID) <> @sessionID)
		BEGIN
			RAISERROR('Replied User does not belong to this session!', 16, 4)
			RETURN 4
		END

	-- Insert into Message

	INSERT INTO [Message] (SessionID, MsgContent, PosterID, ReplyTo, [Timestamp])
		VALUES(@sessionID, @message, @posterID, @repliedToMessageID, CURRENT_TIMESTAMP)

	SET @newMessageID = @@IDENTITY;

	COMMIT TRANSACTION;

	RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[CREATE_SESSION]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  StoredProcedure [dbo].[FAVORITE_NUM]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  StoredProcedure [dbo].[GET_SESSION_ROOM_ID_FROM_PASSWORD]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  StoredProcedure [dbo].[INSERT_MESSAGE]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  StoredProcedure [dbo].[INSERT_REPLY]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  StoredProcedure [dbo].[JOIN_SESSION]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  StoredProcedure [dbo].[MY_PROCEDURE]    Script Date: 11/18/2022 1:18:00 PM ******/
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
/****** Object:  StoredProcedure [dbo].[RETRIEVE_MESSAGES]    Script Date: 11/18/2022 1:18:00 PM ******/
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

	SELECT * FROM [MESSAGE] m WHERE SessionID = @sessionID;

--	RETURN 0

END

GO
USE [master]
GO
ALTER DATABASE [DB_ARS] SET  READ_WRITE 
GO
