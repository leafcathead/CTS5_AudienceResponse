/**


Used for brainstorming the ideas for the new tables


**/



CREATE TABLE PanicButton (
	ID BIGINT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	PanicType CHAR(4) UNIQUE NOT NULL,
	PanicDesc NVARCHAR(100)
)
GO

CREATE TABLE PanicResponse (
	ID BIGINT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	PanicType BIGINT REFERENCES PanicButton(ID) NOT NULL,
	Panicker BIGINT REFERENCES SessionUser(ID) NOT NULL,
	SessionRoom BIGINT REFERENCES SessionRoom(ID) NOT NULL,
	LogTime dateTime2(7) DEFAULT CURRENT_TIMESTAMP -- To check for cooldown?
)
GO