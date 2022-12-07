/**

Types of Panic Buttons

Created by: Connor Peper

Users cannot create their own panic buttons so this should all be done manually.

**/

USE DB_ARS

-- Talking too fast

INSERT INTO PanicButton (PanicType, [Desc])
	VALUES('2FST', 'Push this button if the speaker is speaking too quickly and you would like them to slow down.')


-- Talking too quietly

INSERT INTO PanicButton (PanicType, [Desc])
	VALUES('2QIT','Push this button if the speaker is speaking too quietly and you would lie them to speak up.')

-- Repeat!

INSERT INTO PanicButton (PanicType, [Desc])
	VALUES('REPT', 'Push this button if you would like the speaker to repeat what he has just said.')

-- Explain Further

INSERT INTO PanicButton (PanicType, [Desc])
	VALUES('EXPL','Push this button if you would like the speaker to go into more detail about the current topic.')

-- Please wait

INSERT INTO PanicButton(PanicType, [Desc])
	VALUES('WAIT', 'Push this button if you want the speaker to temporarily stop speaking so that you can catch up.')

-- General Panic

INSERT INTO PanicButton (PanicType, [Desc])
	VALUES('GENP','Push this button if your concern does not fall into the realm of the other buttons.')

