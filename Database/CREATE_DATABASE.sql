USE [master]
GO
/****** Object:  Database [ARS_test]    Script Date: 10/30/2022 6:01:04 PM ******/
CREATE DATABASE [ARS_test]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ARS_test', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\ARS_test.mdf' , SIZE = 81920KB , MAXSIZE = 512000KB , FILEGROWTH = 12%)
 LOG ON 
( NAME = N'ARS_testLog', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\ARS_testLog.ldf' , SIZE = 3072KB , MAXSIZE = 22528KB , FILEGROWTH = 17%)
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [ARS_test] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ARS_test].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ARS_test] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ARS_test] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ARS_test] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ARS_test] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ARS_test] SET ARITHABORT OFF 
GO
ALTER DATABASE [ARS_test] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [ARS_test] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ARS_test] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ARS_test] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ARS_test] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ARS_test] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ARS_test] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ARS_test] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ARS_test] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ARS_test] SET  ENABLE_BROKER 
GO
ALTER DATABASE [ARS_test] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ARS_test] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ARS_test] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ARS_test] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ARS_test] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ARS_test] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ARS_test] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ARS_test] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [ARS_test] SET  MULTI_USER 
GO
ALTER DATABASE [ARS_test] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ARS_test] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ARS_test] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ARS_test] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [ARS_test] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [ARS_test] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [ARS_test] SET QUERY_STORE = OFF
GO
USE [ARS_test]
GO
/****** Object:  User [TestUser]    Script Date: 10/30/2022 6:01:04 PM ******/
CREATE USER [TestUser] FOR LOGIN [TestUser] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [TestUser]
GO
USE [ARS_test]
GO
/****** Object:  Sequence [dbo].[hibernate_sequence]    Script Date: 10/30/2022 6:01:04 PM ******/
CREATE SEQUENCE [dbo].[hibernate_sequence] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
/****** Object:  Table [dbo].[BugReport]    Script Date: 10/30/2022 6:01:04 PM ******/
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
/****** Object:  Table [dbo].[Message]    Script Date: 10/30/2022 6:01:04 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Message](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[SessionID] [bigint] NULL,
	[MsgContent] [varchar](500) NOT NULL,
	[PosterID] [bigint] NULL,
	[ReplyTo] [char](12) NULL,
	[LIKES] [int] NULL,
	[IS_APPROVED] [bit] NULL,
	[MsgContents] [varchar](255) NOT NULL,
	[Timestamp] [datetime2](7) NOT NULL,
 CONSTRAINT [PK__Message__3214EC27ACCC4325] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SessionRoom]    Script Date: 10/30/2022 6:01:04 PM ******/
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
/****** Object:  Table [dbo].[SessionUser]    Script Date: 10/30/2022 6:01:04 PM ******/
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
/****** Object:  Table [dbo].[Student]    Script Date: 10/30/2022 6:01:04 PM ******/
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
ALTER TABLE [dbo].[Message] ADD  CONSTRAINT [DF__Message__IS_APPR__3E1D39E1]  DEFAULT ((0)) FOR [IS_APPROVED]
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
/****** Object:  StoredProcedure [dbo].[addStudent]    Script Date: 10/30/2022 6:01:04 PM ******/
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
/****** Object:  StoredProcedure [dbo].[CREATE_SESSION]    Script Date: 10/30/2022 6:01:04 PM ******/
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
/****** Object:  StoredProcedure [dbo].[FAVORITE_NUM]    Script Date: 10/30/2022 6:01:04 PM ******/
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
/****** Object:  StoredProcedure [dbo].[GET_SESSION_ROOM_ID_FROM_PASSWORD]    Script Date: 10/30/2022 6:01:04 PM ******/
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
			SET @sessionID = 2;
		END


	RETURN 0;
END
GO
/****** Object:  StoredProcedure [dbo].[JOIN_SESSION]    Script Date: 10/30/2022 6:01:04 PM ******/
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
/****** Object:  StoredProcedure [dbo].[MY_PROCEDURE]    Script Date: 10/30/2022 6:01:04 PM ******/
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
USE [master]
GO
ALTER DATABASE [ARS_test] SET  READ_WRITE 
GO
