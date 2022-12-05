/**

Definitions for the two new panic button tables

**/

USE DB_ARS

CREATE TABLE PanicButton (
	ID BIGINT PRIMARY KEY IDENTITY(1,1),
	PanicType CHAR(4) NOT NULL UNIQUE,
	[Desc] NVARCHAR(150)
)
GO

CREATE TABLE PanicResponse (
	ID BIGINT PRIMARY KEY IDENTITY(1,1),
	PanicButtonPushed BIGINT REFERENCES PanicButton(ID) NOT NULL,
	Panicker BIGINT REFERENCES SessionUser(ID) NOT NULL,
	SessionRoom BIGINT REFERENCES SessionRoom(ID) NOT NULL,
	LogTime DATETIME DEFAULT CURRENT_TIMESTAMP
)