# backend_webflux
This project is based on webflux and we make simple CRUD board. 

### 📚 What we use

* Spring boot 2.1.2.RELEASE
* Gradle
* JDK 11
* MongoDB
* Docker
* Swagger

### 🎈 Build
Make sure you have installed the following software:
* Gradle
* JDK 11
* Docker for Desktop

Clone the source codes into your local system.
```` bash
git clone https://github.com/roobib/backend_webflux.git
````

There is a **docker-compose.yml** file in the project folder.

Run the following command to start up a MonogoDB servicce.

```` bash
   docker-compose up
````

## 🧾 Development progress

[개발 계획 노션 페이지 바로가기](https://sleepy-cartwheel-ace.notion.site/28bec245cd274a62978daa2faf0f2cd5?v=dc25530ae50a4adf8d3c8410305f55af)


## ⚒️ 커밋 컨벤션

### Structure
```` bash
   type: Short (50 chars or less) summary of changes (#issue)
   ex. feat: Summarize changes in around 50 characters or less (#1)
````

* 반드시 **영어**로 작성하고 **50자**를 넘기지 말 것
* 첫 글자는 **대문자**로 작성할 것
* 문장의 끝에는 **마침표**를 적지 말 것
* 문장을 **동사 원형**으로 시작하는 **명령문** 형태로 작성할 것

|type|usage|
|------|---|
|feat|새로운 기능 추가|
|fix|버그 수정|
|docs|문서 수정|
|style|코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우|
|refactor|코드 리펙토링|
|test|테스트 코드, 리펙토링 테스트 코드 추가|
|chore|빌드 업무 수정, 패키지 매니저 수정|
|comment|주석 추가|
