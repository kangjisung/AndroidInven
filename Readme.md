<<<<<<< HEAD
### 20170326
>KitKat 디바이스에서 어플이 켜지지 않던 버그를 픽스

### 20170301-1
>데이터베이스 초기화 후 베타 테스트

### 20170306
>도메인 주소 변경

### 20170301
>마일리지를 사용하려 할때 그 양을 알 수 없었던 문제를 해결

### 20170228-5
>서버에서 매장의 공지를 다운로드하여 출력할 수 있음
>테스트용 마일리지 사용 내역을 서버에 업로드할 수 있음

### 20170228-4
>네트워크 모듈 엔진에서 최대 접속 대기 시간 옵션 추가

### 20170228-3
>테스트용 매장의 현제 마일리지 누적 현황을 출력할 수 있다

### 20170228-2
>네트워크 모듈을 소스내 어디서든 쓸 수 있게끔 변경

### 20170228-1
>매장에 고객을 등록하는 기능 구현

### 20170228
>서비스에 고객의 정보를 업로드하는 기능 구현

### 20170225-1
>설정의 팝업들을 다일로그로 변경

### 20170225
>서버에 등록되어 있는 매장의 리스트를 다운받아 클라이언트 데이터베이스에 저장해 놓는 솔류션을 구현하였다

### 20170211
>서버 이전 및 팝업창 열기 버그 픽스

### 20170204-5
>각 매장별 마일리지 총 누적량 불러오는 기능

### 20170204-4
>마일리지변동량 업데이트

### 20170204-3
>고객이 등록된 매장의 리스트를 불러오는 기능 추가

### 20170204-2
>고객이 상점에서 탈퇴하는 솔류션 추가

### 20170204-1
>고객이 상점에 등록되는 솔류션 추가

### 20170204
>네트워크 모듈 생성 및 json 데이터 파싱

### 20170126-1
>좀더 유심히 살펴본 결과 환경설정의 각 아이템들을 누를때 팝업을 못띄우는 버그가 존재하였다
>해당 이슈를 픽스

### 20170126
>디자인 팀에서 작업할 때 어플리케이션 실행 중지 오류가 발견되어 테스트겸 빌드를 해봤지만 이상 증세를 파악하지 못했다

### 20170122
>고객앱 클라이언트 데이터베이스 연동 작업 오프라인 기능 이용 가능하도록 구현

### 20170120
>클라이언트 데이터베이스를 연동하여 새로운 매장을 등록할 때의 매장 리스트를 조회하는 기능을 리펙토링 하였다

### 20170119
>디자인 파트 레이아웃 xml오류 수정 및 최소 -> 취소 오타 수정

### 20170118
>현우파트 데이터베이스 작업
>1.데이터베이스를 하나하나 2차원 배열을 살리는 방향으로 설계하여 집어 넣어줌 (코드참조)
>2.그렇게하여 가지고 있는 데이터베이스를 현우가 설계한 반복문을 통해서 리턴값으로 넘겨줌 

### 20170113
>클라이언트 데이터베이스의 값을 반환하는 코드 작성중
=======
﻿### 20170127
>xml 파일 삭제 테스트
>>>>>>> origin/store-app

### 20170126
>메인 엑티비티의 딜레이를 위한 runnable 실행 부분을 머지 하면서 지워버려 에러가 났었다
>해당 부분을 픽스

<<<<<<< HEAD
### 20170112-1
>아니 쓰지도 않는 변수를 왜 써놔서 날 헷깔리게 하는건데

### 20170112
>안드로이드 빌드 툴 버전 25.0.0 으로 다시 변경

### 20170108-1
>클라이언트 데이터베이스에 테스트용 데이터를 적용시켜 로드가 잘 되는지 테스트를 하였다

### 20170108
>클라이언트 데이터베이스 연동 준비중

### 20170107
>매장 삭제시 확인 다일로그를 띄어 주는 걸로 변경

### 20170106-1
>매장 삭제 레이아웃 텍스트값들 string.xml로 이전

### 20170106
>매장 선택 아이템 레이아웃 변경 후 디자이너 확인 요청

### 20161230
>매장 검색할 때 기본적인 매장 리스트가 보이지 않는 버그 픽스

### 20161230
>매장 추가 후 등록된 매장 리스트 화면에 추가된 매장을 보여줄 수 있도록 갱신하는 내용을 추가하였다

### 20161229-9
>원하는 매장 선택 기능 추가

### 20161229-8
>매장검색기능 추가

### 20161229-7
>등록한 매장 삭제기능 추가

### 20161229-6
>매장 삭제 중 intent로 값이 전달되지 않았던 문제를 픽스

### 20161229-5
>매장 삭제 기능 구현중

### 20161229-4
>가상의 데이터베이스와 연동하여 등록된 매장 리스트를 볼수 있으며, 그 매장에 대한 정보와 공지를 볼 수 있다

### 20161229-3
>매장 검색 부분에서 검색내용에 뭔가를 쓸때마다 발생한 이벤트를 잡아냈다
>확인 버튼을 준비중으로 변경
>토스트 메시지 삭제

### 20161229-2
>스탬프 사용 버튼을 고객이 실수로 눌러버리는 불상사를 방지하기 위한 잠금장치를 구현하였다

### 20161229-1
>공지를 눌렀을 때 해당 공지의 내용을 팝업에 로드해 출력하는 솔류션 추가

### 20161229
>새로운 레이아웃 아이디 이름들 변경

### 20161228-6
>공지사항 작업전 샘플 추가

### 20161228-5
>위도 경도 데이터가 넘어가지 않았던 이유는 getStringExtra함수가 Double형을 가져오지 않기때문이였다
>따라서 intent로 보낼때 string으로 변환하여 보냄으로써 문제를 해결했다

### 20161228-4
>지도 연동을 하였지만 좌표를 전달하는데 문제가 생겨 원하는 위치에 표시를 하지 못한다

### 20161228-3
>매장 정보탭에 추가적인 데이터를 전송할 수 있게끔 리펙토링 하였다
>레이아웃의 마음에 안들는 아이디 값을 변경하였다
>매장에 바로 전화할 수 있는 기능을 추가하였다
>전화 관련 보안 예외 처리를 하였다

### 20161228-2
>상점 추가 부분 널 에러

### 20161228-1
>인터넷 연결 체크 기능 추가

### 20161228
>고객 디바이스에서 gmail계정을 추출해 오고 안드로이드 버전에 따라서 권한 요청을 하는 것 까지 구현

### 20161226-4
>학원에서 작업하기 전 해야할 일을 oven에서 레이아웃 디자인한걸 토데로 주석을 약간 추가

### 20161226-3
>공지 예시 삭제, 햄버거 매뉴 이미지 비활성화

### 20161226-2
>테스트용 상점 한개 추가

### 20161226-1
>등록된 매장중 한가지를 선택하면 그 매장에대한 정보를 로드해서 각 레이아웃에 맞게 데이터를 전달한다

### 20161226
>리사이클뷰를 최대한 재활용하여 등록된 상점들의 리스트를 출력하는 솔류션을 구현하였다

### 20161225-9
>매장 공지를 출력하기 위해 구현한 리사이클뷰를 재사용하기 위해 리펙토링하는중

### 20161225-8
>매장 선택 레이아웃의 기본 표시 데이터 변경

### 20161225-7
>레이아웃 디폴트 표시 데이터 편집

### 20161225-6
>현제 나의 스탬프를 보는 화면에서 쿠폰<->스탬프를 전환하는 버튼 이벤트를 추가하였다

### 20161225-5
>셋팅 버튼을 누르면 스낵바가 뜨도록 추가하여 어디서 이벤트를 처리해야할 지 알 수 있게끔 해놨다

### 20161225-4
>처음으로 보여주는 탭 번호 변경, 등록된 상점 클릭하면 화면이동

### 20161225-3
>매장 선택화면에서 각 버튼 이벤트 추가

### 20161225-2
>공지리스트 변수 명 변경

### 20161225-1
>상점 리스트를 보는 레이아웃의 각 버튼이나 이미지뷰 등의 아이디를 유지보수 하기 쉬운 이름으로 변경하여 난독화를 줄였다

### 20161225
>메리크리스마스 ㅠㅜ

### 20161212
>ex13예제의 데이터베이스 이동 부분을 디버깅 시에는 무조건 새로운 데이터베이스 파일로 옮기도록 수정하였다
=======
### 20170125-1
>매장 리스트를 불러오는 메소드에서 json데이터를 파싱한 후 값을 추출해 내는 예제를 구현
>>>>>>> origin/store-app

### 20170125
>json 파싱 전 예제 데이터 불러오는 함수 구현

### 20170123-3
>http통신 테스트 완료 

### 20170123-2
>안드로이드 정책상의 httpclient삭제 부분을 다시 살림
>프로젝트 gradle sync가 필요

### 20170123-1
>통신 모듈에 파라미터를 넘겨주는 기능 추가

### 20170123
>인터넷 통신 권한 부여

### 20170122
>점주앱 디자인 프로젝트를 기존 작업하던 파트에서 가져왔다

### 20170126
> 새제품 추가, 제품관리 플로팅버튼, 스탬프 발송에 체크박스 선택 부분 일부, 고객 버튼 누르고 있으면 컨텍스트메뉴 구현, 컨텍스트 메뉴의 수정 누르면 수정할 수 있는 창 구현
> 디자인만 구현
