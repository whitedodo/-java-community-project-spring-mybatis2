# Spring Framework 4.2.4 Releases에서 MyBatis, HikariCP, XML 설정방식(XML Mapper) 회원 AOP, 게시판 구현(방식2) root-context.xml (메인, 회원 기능, 다중 게시판 구현, 파일업로드)
(In Spring Framework 4.2.4 Releases, MyBatis, HikariCP, XML configuration method (XML Mapper) member AOP, bulletin board implementation (method 2) root-context.xml (main, member function, multiple bulletin board implementation, file upload)

### 기본 정보(Information)
##### 제작일자(Create date): 2020-10-21
##### 작성언어(Write language): Java
##### IDE: Eclipse IDE with Spring Tool Suite 4-4.7.2.
##### 제작자(Author): 도도(Dodo) / rabbit.white at daum dot net
##### 프레임워크(Framework): 
##### 라이브러리(Library): 
##### 1. Apache-Maven 3.6.3/1.16.0.2.20200610-1735 (https://maven.apache.org/)
##### (소프트웨어 프로젝트 관리 및 이해 도구)
##### 2. Spring Framework 4.2.4 RELEASES
##### 3. Spring-test 4.2.4 RELEASES
##### 4. https://mvnrepository.com/artifact/com.zaxxer/HikariCP
##### - HikariCP 3.4.5
##### 5. https://mvnrepository.com/artifact/org.mybatis/mybatis
##### - MyBatis 3.5.6
##### 6. https://mvnrepository.com/artifact/org.mybatis/mybatis-spring
##### - myBatis-spring
##### 7. https://mvnrepository.com/artifact/javax.servlet/jstl
##### 자바 버전(Java-Version): OpenJDK-14.0.2 (https://openjdk.java.net/) // Version 1.8
##### 8. amazon-corretto-8.265.01.1-windows-x64-jdk.zip
##### 9. Spring-TX
##### - https://mvnrepository.com/artifact/org.springframework/spring-tx
##### 10. Spring-AOP
##### 11. Aspectjweaver, Aspectj
##### 12. json-simple
##### 13. commons-fileupload
##### 14. commons-io

### 1. 소개(Description)
##### 1. 해당 프로젝트는 MyBatis, HikariCP, XML 설정방식(XML Mapper) 회원 AOP, 다중 게시판 구현 등을 (방식1) root-context.xml으로 구현하였다.
#####    (The project implemented MyBatis, HikariCP, XML Mapper member AOP, and multiple bulletin board implementations as (method 1) root-context.xml)
##### 2. 다중 게시판이 적용된 Spring Framework, MyBatis 기반으로 작성하였다.
#####    (It was written based on Spring Framework, MyBatis, where multiple bulletin boards were applied.)
##### 3. 다중 파일 업로드 기능을 추가하였다.
#####    (Added multi-file upload function.)
##### 4. JSON-Simple을 이용하여 json을 구현하였다.
#####    (Json was implemented using JSON-Simple.)
##### 5. 스프링 인터셉터를 활용하여 맴버십 보안 기능을 적용하였다.
#####    (Membership security function was applied using Spring Interceptor.)
##### 6. 스프링 인터셉터로 preHandler, postHandler로 페이지를 관리하였다.
#####    (Pages were managed with preHandler and postHandler with Spring Interceptor.)
##### 7. 파일업로드, 삭제, 다운로드 기능을 추가하였다.
#####    (File upload, delete, and download functions have been added.)
##### 8. 암호화 패키지 등을 추가시켰다.
#####    (Added encryption package, etc.)

### 2. 시연(Practice)
##### 1. Spring Framework 4.2.4, MyBatis, Oracle 11g, Member AOP, Board Community PJT - 1, https://www.youtube.com/watch?v=cnzzUlCQDCo, Accessed by 2020-10-25, Last Modified 2020-10-25.
##### 2. Spring Framework 4.2.4, MyBatis, Oracle 11g, Member AOP, Board Community PJT - 2, https://www.youtube.com/watch?v=zHDRc45peno, Accessed by 2020-10-25, Last Modified 2020-10-25.

### 3. 참고자료(References)
##### 1. MyBatis - 마이바티스 3 | 매퍼 XML 파일, https://mybatis.org/mybatis-3/ko/sqlmap-xml.html, Accessed by 2020-10-12, Last Modified 2020-07-10.
