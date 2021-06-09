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

>2021/06/07
>   * 회원가입 구현 완료.
>   * form data가 제대로 넘어가지 않는 오류가 있었으나 input에 name을 정의하지 않고 그냥 넘겼기 때문에
>   * 발생한 오류로 해결 완료.
>   * 회원가입 시 모든 정보를 입력해야 가입할 수 있도록 조건 처리.
>   * 각 항목이 제대로 입력되지 않았을 때 overlap이 공백이 아닌 상태이므로 그것을 이용해 처리하려고 했지만
>   * 원하는 형태의 데이터를 갖고 오지 못해 각 항목별로 check 라는 input을 만들어 처리.
>   * 너무 지체되는것 같아서 이렇게 처리해두고 후에 리팩토링 할 것.
>   * 데이터 처리 중 한글이 깨지는 오류가 발생.
>   * web.xml에서 filter-mapping에서 <servlet-name>dispatcher</servlet-name>으로 작성했는데
>   * <url-pattern>/*</url-pattern>으로 변경하니 해결.
>   * 이전 시큐리티 공부할때는 같은 조건의 셋팅에서 문제가 없었는데 이번에 왜 문제가 생겼는지 원인 파악이 안됨.
>   * ####원인파악 꼭 할 것.

>2021/06/08
>   * admin 페이지 먼저 작성 시작.
>   * 상품 등록 구현 중 설계 수정.
>   * 처음에는 상품 리스트에서 상품 선택하면 모달창으로 보여주도록 설계했으나 썸네일 및 정보이미지도 보여야 하고
>     상품별 옵션이 다양할 수 있는데 모든 경우의 수에 대해 중복된 데이터를 직접 입력하며 추가해야 하는 부분에서
>     불편함이 있다고 판단.   
>     하나의 상품을 등록하고 누르면 모달창이 아닌 수정페이지로 이동하고 그 수정페이지에서 상품 정보를 수정하거나
>     사이즈 및 컬러, 재고만 변경해서 새로 추가할 수 있는 형태로 변경.
>   * exerd도 재설계하면서 제대로 수정되지 않은 부분이 있어서 수정.
>   * 썸네일을 여러장으로 구현하고 싶은데 대표 썸네일을 어떻게 처리할까 고민하다가 Product 테이블에 대표썸네일 컬럼을 만들고
>     대표썸네일을 따로 파일선택 하도록 하는 방향으로 진행.
>   * 이미지 업로드 구현중.
>   * 이미지 preview 생성 및 삭제 구현 중.

>2021/06/09
>   * 이미지 preview 생성 및 삭제 구현 완료.
>   * 등록 구현 중.
>   * form데이터가 컨트롤러로 제대로 넘어가지 않는 오류 발생.
>   * 해결중.