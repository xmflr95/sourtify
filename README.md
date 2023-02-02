# sourtify
Java program that finds the method name of a JavaScript object  
JavaScript 객체 메서드명 검색을 위한 자바 프로그램입니다.

## Build
1. Navigate to the folder that holds your class files:
```sh
${Project_Path}/sourtify/src/sourtify
```
2. Compile your class:
```sh
javac -encoding utf-8 Sourtify.java
```
3. Navigate to the `src` folder
```sh
cd ../
```
4. Create a `sourtify.jar` file:
```sh
jar cvfm sourtify.jar .\META-INF\MANIFEST.MF .\sourtify\Sourtify.class
```
5. Run
```sh
java -jar sourtify.jar
```

## Run
1. Import and run projects into Eclipse and Intellij. Or Build Project by jar
2. Follow the process
```sh
>>> 1. Input JS File Path
{Files you want to scan}
>>> 2. Input Keyword
{Object you watn to scan}
```

* Result (test.js)
```bash
>>> 1. Input JS File Path
C:\sourtify\test.js
<<< Input JS File Path : C:\sourtify\test.js
>>> 2. Input Keyword
MyObject
<<< Input Keyword : MyObject
>>> SEARCH START >>>

<> Check Method End Point <>
funcName : method1
funcName : method2
funcName : currency
funcName : bitCoin
funcName : protoFunction
funcName : protoFunction2

funcCount : 6

>>> END >>>
```

The method names of 'MyObject' can be found.

