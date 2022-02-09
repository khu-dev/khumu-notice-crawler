
# khumu-notice-crawler

``khumu-notice-crawler``는 쿠뮤에서 게시하기 위한 교내 공지사항을 수집하는 배치 프로그램이다. 구조화된 작업을 위해 ``Spring-Batch`` framework를 활용하고 있고, html 파싱은 ``Jsoup``라이브러리를 활용하여 작업하고 있다.

## Table of Contents
- [Technologies](#technologies)
- [Acknowledgements](#acknowledgements)
- [Author](#authors)

## Crawling List

- 경희대 예술 디자인 대학
- 경희대 컴퓨터공학과
- 경희대 전자공학과
- 경희대 전자정보대학
- 경희대 외국어 대학
- 경희대 LINC+ 사업단
- 경희대 학생지원센터(장학)
- 경희대 SW중심대학사업단

## Technologies

- Java Version 8
- Spring-Batch Version 4.3.3
- Jsoup Version 1.13.1
- MySql DB

## DataBase
![[MySQL_Data_Table]](diagramdraw.png)

## Acknowledgements

 - [Spring Batch Official Documents](https://docs.spring.io/spring-batch/docs/4.3.x/reference/html/)
 - [Spring Batch Official API-Doc](https://docs.spring.io/spring-batch/docs/4.3.x/api/index.html)
 - [Jsoup Official Documents](https://jsoup.org/apidocs/)

## API

- `/api` : 공통 API URI
  - `/announcements` : 공지사항 관련 URI
    - `user` : 이용자의 이름을 넣으면 팔로우한 공지사항 불러오기
    - `author` : 공지사항 게시자 이름으로 공지사항 불러오기
    - `date` : 공지사항 업로드 시간으로 공지사항 불러오기

## Authors

Created by [@Marhead](https://github.com/Marhead) - Feel free to contact me!
