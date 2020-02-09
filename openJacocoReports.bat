
cd /d %~dp0
call mvn test
start chrome "%~dp0\target\site\jacoco\index.html"