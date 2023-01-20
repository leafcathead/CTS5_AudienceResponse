USE master;
GO
IF DB_ID ('ARS_test') IS NOT NULL
Drop database [ARS_test];
GO
-- Get the SQL Server data path
DECLARE @data_path nvarchar(256);
SET @data_path = (SELECT SUBSTRING(physical_name, 1, CHARINDEX(N'master.mdf', LOWER(physical_name)) - 1)
                  FROM master.sys.master_files
                  WHERE database_id = 1 AND file_id = 1);

Print @data_path
EXECUTE ('CREATE DATABASE ARS_test
ON
PRIMARY  
	(NAME = ARS_test,
	FILENAME = '''+ @data_path + 'ARS_test.mdf'',
	SIZE = 80MB,
	MAXSIZE = 500MB,
	FILEGROWTH = 12%)
LOG ON 
	(NAME = ARS_testLog,
	FILENAME = '''+ @data_path + 'ARS_testLog.ldf'',
	SIZE = 3MB,
	MAXSIZE = 22MB,
	FILEGROWTH = 17%)'
);
Go