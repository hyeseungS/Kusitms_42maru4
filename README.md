# :newspaper: 개인화된 뉴스 추천 서비스, 뉴스마루

### :pushpin: 프로젝트 기간 : 2022년 3월 5일 ~ 2022년 3월 18일

### :pushpin: Team : 42MARU X 호두마루 (4조)
- <p> API 개발, 화면 구성, 서버 구축, DB 구축 및 관리 : 신혜승, 한지혜 </p>
- <p> 뉴스 키워드 추출 : 조선희, 최혜영 </p>
- <p> 워드 클라우드를 통한 뉴스 키워드 시각화 : 최민경 </p>

### :pushpin: Description
현대사회는 엄청난 정보의 홍수속에서 개인이 원하는 정보를 찾기 위해서 많은 시간이 필요합니다.
42MARU는 질의의도를 의미적으로 이해하고 방대한 비정형 데이터에서 ‘단 하나의 정답’만을 제공하는 QA(Question Answering) 및 TA(Text Analytics) 플랫폼을 통해 이러한 이슈를 해결하고 있습니다.  

이에 따라, **42MARU X 호두마루**는 **기계독해/요약모델 및 TextRank/공공 인공지능 오픈 API**를 활용하여 뉴스 키워드 추출과 함께 개인화된 뉴스 추천 서비스를 제공하고자 합니다.  

### :pushpin: Development Environment & Language
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Python-3766AB?style=flat-square&logo=Python&logoColor=white"/></a>
<img src="https://img.shields.io/badge/R-276DC3?style=flat-square&logo=R&logoColor=white"/></a>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=HTML5&logoColor=white"/></a> 
<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=CSS3&logoColor=white"/></a>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Flask-000000?style=flat-square&logo=Flask&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat-square&logo=Amazon%20AWS&logoColor=white"/></a>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/></a>

### :pushpin: URL
www.newsmaru.tk

### :pushpin: Feature
- 로그인/회원가입
- 뉴스 등록
- 뉴스 보기
  - 해시태그 검색
  - 카테고리별 필터
  - 뉴스 정렬
- 뉴스 상세보기
  - 요약 모델
  - 키워드 추출(해시태그)
  - 워드 클라우드
  - 스크랩
  - 성별/연령별 스크랩 통계

- MY 뉴스
  - 회원별 조회/스크랩 목록 조회
  - 회원별 추천 뉴스(성별에 따라)
  - 조회 목록 삭제

### :pushpin: Usage
요약모델과 키워드 추출을 통해 사용자가 방대한 뉴스 내용을 정리하여 보다 쉽게 이해할 수 있으며,
워드 클라우드를 통한 뉴스 키워드 시각화를 통해 사용자가 뉴스를 한눈에 볼 수 있습니다.
또한, 키워드 검색, 카테고리 필터, 정렬, 조회/스크랩 및 통계, 회원별 추천 목록 등 다양한 기능을 통해 개인화된 뉴스를 접할 수 있습니다.

### :pushpin: Reference

공공 인공지능 오픈 API : https://aiopen.etri.re.kr/guide_wiseNLU.php
