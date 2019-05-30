heroku config:get DATABASE_URL -a bridging-for-tomorrow > tmpFile 
set /p DATABASE_URL= < tmpFile  
del tmpFile
