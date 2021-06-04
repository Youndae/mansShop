# Man's Shop 개인 프로젝트

### 목적
* 시큐리티를 적용해 관리자와 사용자의 권한을 분리
* 결제 API 적용
#
### 사용기술
* Spring 5.0.7
* Maven
* MyBatis
* JSP
* Spring Security
* Lombok
* Oracle
* Tomcat 9
* CSS
* JQuery
* Ajax
* 사용 IDE  :  IntelliJ
#
### 작업과정

>2021/05/20   
>    * 프로젝트 기본 셋팅 및 테스트   
>   * DB 설계
   
>2021/05/21
>   * DB 연결 테스트 및 확인
>   * Controller에 메서드 먼저 작성중   

>2021/05/27
>   * 클래스 다이어그램에 맞춰 메서드 작성 완료   

>2021/05/28
>   * 시큐리티 적용 테스트 위해 관리자 상품목록 페이지, 사용자 정보수정 페이지 약식으로 작성
>   * 작성 후 PropertyNotFoundException에러 발생했으나 해결.
>   * 상품 이미지 캡쳐랑 오류 해결하는데 너무 오래걸려서 시간 많이 지체되었으므로 좀 빠르게 작업해야함.
>   * 시큐리티 테스트 바로 하고 관리자파트 먼저 구현하는쪽으로.

>2021/05/31
>   * security 설정 중
>   * DB에 회원 데이터 넣으려고 테스트 작성했는데 passwordEncoder 오류.
>   * UnsatisfiedDependencyException발생.

>2021/06/01
>   * UnsatisfiedDependencyException 해결.
>   * PasswordEncoder는 security-context.xml에 정의해놓고 ContextConfiguration에 applicationContext.xml만 사용해서 오류 난 것.
>   * security 테스트 완료
>   * security 테스트하면서 권한에 따른 페이지 이동과 이전 페이지 등 테스트.
>   * 기존 AuthenticationSuccessHandler를 재정의하던 LoginSuccessHandler 대신
>   * SavedRequestAwareAuthenticationSuccessHandler를 상속받는 CustomAuthenticationSuccessHandler로 변경.
>   * 로그인 시 이전페이지로 이동하기 위함. 그리고 관리자는 로그인하면 바로 관리자 페이지로 이동.

>2021/06/03
>   * 회원가입 구현중.
>   * 아이디 중복체크 구현중에 ajax 부분 문제 발생.
>   * 403에러 출력되는 중이고 ajax success에 unused property 라는 오류가 발생.
>   * 오류내용이 제대로 출력되지 않고 accessError로 자꾸 넘어가서 
>   * security-context.xml에 customAccessDeniedHandler 주석처리.

>2021/06/04
>   * 403에러 해결.
>   * 권한문제였고 csrf를 같이 넘겨줘야 하는데 안넘겨줬기 때문에 발생한 오류.
>   * token과 header를 같이 넘겨주는 방법으로 해결함.
>   * 회원가입 정규표현식 작성.
>   * 아이디 중복체크, 비밀번호 확인 작동 확인.