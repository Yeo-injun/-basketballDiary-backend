# basketballDiary

2020/01/08 - 이성주

회원가입 기능 개발 시작.
  - 현재 Member(Entity, Controller, Service, Repository,saveMember.xml) 생성
  - Jpa 를 사용하려 하였으나 학습량이 많아보여 mybatis로 순회
  - DB는 mysql 사용 , AWS RDS 를 이용하여 데이터 저장 및 관리

2022/01/15 - 이성주

회원가입 기능 구현 및 회원조회 기능 구현
- User(Controller,Service,Repository) 구현
- 문제점 : JPA를 사용했을 경우 Repository에서 회원을 생성 후 바로 sequence 값을 return 시킬 수 있었다. 그러나 mybatis를 사용할시 어떻게 구현해야 될지 몰랐음  
  -> 해결 : selectKey 를 사용하여 회원 저장 후 sequence 값 return
- 문제점 : 회원조회 api를 구현하였지만, 현재 로그인 된 User의 id 값을 가져와야 한다. 그러나 로그인 기능이 구현(x) 또한, postman을 사용하여 어떻게 session 값을 유지시킬 수 있을지 몰랐음  
  -> 해결 : ~~로그인 기능 구현하기 시작~~
  
2022/01/16 - 이성주

회원가입 및 회원조회 테스트 코드 구현
- Junit5를 사용하여 테스트 코드 작성
- 문제점 : 실제 운영 디비(mysql) 대신 테스트는 H2 in memory 방식을 사용하려 했으나, mybatis 에서는 어떻게 설정을 해야될지 찾지를 못함  
  -> 해결 : 결국 해결은 못하고 개발단계에서는 운영 디비를 사용하기로 함

2022/01/18 - 이성주

로그인 처리 테스트 확인 및 회원조회 성공
- postman을 사용하여 JSESSIONID 값이 쿠키에 저장되는 것을 확인한 후 회원조회 성공
- 인터셉터(Interceptor) 와 어노테이션(Annotation)을 사용한 권한 처리 미구현
- LoginUserServiceTest 구현 : Mock 을 사용하여 테스트 진행(MockHttpSession) , 아직 테스트 코드를 작성하는 것이 익숙하지 않음

2022/01/19 - 이성주

로그인과 권한 처리 Interceptor 추가

2022/01/21 - 이성주

- 문제점 : 현재 화면 접근 권한을 enum과 어노테이션을 사용해서 정의해주고 있다. 그러나 enum 값이 String 형으로 저장되어 있어, 넓은 권한을 가진 회원이 접근이 가능해야 할 화면에도 불구하고 접근하지 못하는 현상이 발생한다.  
  -> 해결 : enum에 저장되어 있는 필드값을 String형이 아닌 정수형으로 교체, 그러면 부등호 연산(<,>)을 통해 범위를 설정해 줄 수 있게 된다.
