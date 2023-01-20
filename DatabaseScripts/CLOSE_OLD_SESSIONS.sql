/**

	This Business Rule deletes sessions that have been open for over 24 hours.
	NOTE: Do not activate until after presentation.

**/

USE msdb
GO

EXEC msdb.dbo.sp_add_job  
   @job_name = N'CloseOldSessions',   
   @enabled = 0,   
   @description = N'Close sessions that have been open for over 24 hours' ; 

 EXEC msdb.dbo.sp_add_jobstep  
    @job_name = N'CloseOldSessions',   
    @step_name = N'Automatically close old sessions',   
    @subsystem = N'TSQL',   
    @command = 'DELETE FROM SessionRoom WHERE DATEDIFF(hour, SessionRoom.Timestamp, GETDATE())';

 EXEC msdb.dbo.sp_add_schedule  
    @schedule_name = N'Automatically close old sessions',   
    @freq_type = 4,  -- daily start
    @freq_interval = 1,
    @active_start_time = '235959' ;   -- start time 23:00:00

 EXEC msdb.dbo.sp_attach_schedule  
   @job_name = N'CloseOldSessions',  
   @schedule_name = N'Automatically close old sessions' ;

 EXEC msdb.dbo.sp_add_jobserver  
   @job_name = N'CloseOldSessions',  
   @server_name = @@servername ;
