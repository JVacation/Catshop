call cat_defs.bat

echo Compile the CatShop system

javac  %FLAGS% -cp %JUNIT% catalogue\*.java middle\*.java dbAccess\*.java orders\*.java clients\*.java remote\*.java debug\*.java clients\backdoor\*.java clients\cashier\*.java clients\collection\*.java clients\customer\*.java clients\shopDisplay\*.java clients\warehousePick\*.java
