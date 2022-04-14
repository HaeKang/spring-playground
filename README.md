# Spring vs Spring Boot
![image](https://user-images.githubusercontent.com/41337631/163389315-aa78cdb2-6660-42c0-87e6-227f294c8f98.png)

### Spring (Spring Famework) 
 : Spring represented a fresh start after the “winter” of traditional J2EE  
  #### Dependency Injection(DI) - 의존성 주입  
  DI를 통해 객체간 결합도 낮추어 코드 재사용성 향상  
  어떤 객체(B)를 사용하는 주체(A)가 B를 직접 생성하는 것이 아닌, 객체를 외부(Spring)에서 생성해서 A에 주입시켜주는 방식
  
  #### IoC (Invertion of Control) - 제어 역행
  스프링이 개발자의 코드를 호출 -> 스프링에게 제어를 위임
  
  #### AOP (Aspect-Oriented Programming) - 관점지향 프로그래밍
  여러 모듈에서 공통적으로 사용하는 기능을 분리하여 관리
  
  

### Spring Boot 
 : Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".  
 Spring Boot를 통해 스프링 기반 애플리케이션을 쉽게 만들 수 있음  
 스프링 프레임워크를 사용하기 위한 많은 설정을 자동화해줌 (Spring Boot Starter Dependency)  
 
 기존의 web.xml + root-context.xml + servlet-context.xml => application.yml 으로 통합  
![image](https://user-images.githubusercontent.com/41337631/163397885-7b5e5144-3035-46d9-ad7c-0805986a1f8c.png)
 ![image](https://user-images.githubusercontent.com/41337631/163396491-1ccc29b0-2231-4160-a828-dca65c28e316.png)

 
 
 #### Easy dependency Management
Spring Boot Starter를 통해 대부분의 dependency 관리 가능 ~> 개발자는 dependency 관리와 호환버전에 대해 신경x  
따라서 실행환경 등의 인프라 관련은 신경쓸 필요 없이 바로 개발 시작  
ex) JPA가 필요 ~>  'spring-boot-starter-data-jpa' 한줄만 추가해주면 됨.  
 ![image](https://user-images.githubusercontent.com/41337631/163394860-be7959b2-1e33-4880-b0d6-bc9d37326b66.png) ![image](https://user-images.githubusercontent.com/41337631/163395359-2f83c8a9-7119-4ae5-b876-fa3270476896.png)

 
 #### Embedded Tomcat & Make jar, Not War
 스프링 부트 내부에 Tomcat이 포함되어 있어 별도로 설치, 버전 관리 불필요
 이전 : 서버 새로 구축 -> 기존 프로젝트와 동일한 Tomcat 버전 설치 -> Tomcat 관련 설정 xml 값들 수정 -> build툴 설치 등등 작업 필요  
 스프링부트 : JDK 설치 -> java -jar xxx.jar 실행하면 웹서버 구축 완료
 
 

 

# Reference
https://spring.io/
