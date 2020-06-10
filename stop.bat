@echo on
call setpath.bat

cd %XMAPPER2_HOME%

"%JAVA2_HOME%/bin/java" com.klnet.abis.manager.server.StopServer MANAGE 9998