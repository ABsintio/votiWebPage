javac -cp ".;.\src\java\lib\*" -d .\WEB-INF\classes .\src\java\src\*.java
jar cfv .\WEB-INF\lib\servlet.jar .\WEB-INF\classes\*.class