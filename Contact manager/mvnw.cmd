@REM Apache Maven Wrapper
@echo off
@REM Use the same logic as the shell script
if "%JAVA_HOME%"=="" (echo JAVA_HOME not set & exit /b 1)
@endlocal & setlocal enableextensions
setlocal
:execute
for /f "tokens=1* delims= " %%a in ("") do (
    for /f "tokens=*" %%c in ('powershell -noprofile "& { Get-Content -Raw "%~f0" }"') do (
        endlocal & setlocal enableextensions
        setlocal
        set "MVN_CMD=%%a"
    )
)
