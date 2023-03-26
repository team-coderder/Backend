<h6 align="center"> 그룹 일정 관리 웹서비스</h6>
<h2 align="center">ColorMeeting</h2>

![image](https://user-images.githubusercontent.com/100582309/226905677-11b85c2f-efec-4632-b0a0-e11fcd76eda7.png)
 - **[시연 영상]()<br>**
 - **[배포 사이트]()<br>**
 - **[팀 노션 주소](https://www.notion.so/coderder/Coderder-f4aec4bc4da242349797c0d6ebf9e766)**

<br><br>


## 1. 프로젝트 소개
매번 일정 잡을 때마다 고생이시죠? 이제는 한 번에 확인하세요.<br>
**그룹원들의 일정을 한 눈에 확인**하고, 그룹원들의 비는 시간들을 도합하여 **최적의 모임 시간을 추천**해주는 서비스

<br>

## 2. 주요 기능

<!-- - **회원 기능**<br> -->
- ### **회원 기능**
   <details>
       <summary> <b> 회원가입/로그인</b> </summary>
       <div markdown="1">
       쉽고 빠르게 계정을 만들 수 있습니다.
      </div>
   </details>
   <details>
       <summary> <b> 마이페이지</b> </summary>
       <div markdown="1">
           마이페이지에서 자신의 일정을 한 눈에 조회하고 관리할 수 있습니다.
      </div>
   </details>
  
- ### **일정 기능**
   <details>
       <summary> <b>개인 일정 기능</b> </summary>
       <div markdown="1">
       나의 개인 일정을 자유롭게 등록/수정/삭제할 수 있습니다. 개인 일정을 수정하면, 내가 속한 그룹원들도 실시간으로 확인할 수 있습니다. 내가 속한 다른 그룹원들은 어느 시간에 바쁘고 어느 시간에 괜찮은지 역시 확인할 수 있습니다.
      </div>
   </details>
   <details>
       <summary> <b>그룹 일정 기능</b> </summary>
       <div markdown="1">
       그룹 리더는 그룹 페이지에서 그룹 일정을 등록할 수 있습니다. (그룹 리더 외의 그룹원은 등록 불가능합니다.) 리더가 등록한 그룹 일정은 그룹원들 모두 각자의 개인 페이지에서도 확인할 수 있습니다.
      </div>
   </details>
   <details>
       <summary> <b>최적의 모임 시간 추천 기능</b> </summary>
       <div markdown="1">
       그룹원들이 각자 등록한 일정에 따라, 가장 많은 그룹원들이 되는 시간을 자동으로 추천해줍니다.
      </div>
   </details>
 
- ### **그룹 기능**
   <details>
       <summary> <b>그룹 관리 기능</b> </summary>
       <div markdown="1">
       누구나 그룹을 생성/수정/삭제할 수 있습니다. 그룹을 처음 생성한 유저가 그룹 리더가 됩니다.
      </div>
   </details>
   <details>
       <summary> <b>그룹 초대 기능</b> </summary>
       <div markdown="1">
       그룹에 다른 유저들을 초대할 때는, 초대장을 보낼 수 있습니다. 리더는 회원가입을 완료한 Coderder 유저들의 아이디를 검색하여 초대장을 보냅니다. 초대장을 받은 유저는 초대장을 수락하여 그룹에 일원이 될 수도 있고, 초대장을 거절할 수도 있습니다.
      </div>
   </details>
   
<br>
  
## 3. 설계
### 📜 기술 스택
|분류|기술|
| :-: |:- |
|Language|<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">|
|Framework|<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white">|
|Build Tool|<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">|
|DB|<img src="https://img.shields.io/badge/postgresql-4479A1?style=for-the-badge&logo=postgresql&logoColor=white">|
|Server|<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=AmazonAWS&logoColor=white"> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">|
|CI/CD|<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"> <img src="https://img.shields.io/badge/codedeploy-6DB33F?style=for-the-badge&logo=codedeploy&logoColor=white">|

### 🏰 아키텍쳐
<!-- <details>
<summary> <b>아키텍쳐 바로보기</b> </summary>
    <img src="https://user-images.githubusercontent.com/100582309/196345657-eaf613d7-01bc-4118-8df8-b08198704d5f.png"> 
</details> -->


<img src="https://user-images.githubusercontent.com/100582309/196345657-eaf613d7-01bc-4118-8df8-b08198704d5f.png"> 

### 📕 ERD 
<!-- <details>
<summary> <b>ERD 바로보기</b> </summary>
    <img src="https://user-images.githubusercontent.com/100582309/196347173-6b00b013-187e-4a4c-92b0-ab36f4a84779.png"> 
</details> -->

<img src="https://user-images.githubusercontent.com/100582309/196347173-6b00b013-187e-4a4c-92b0-ab36f4a84779.png"> 



<br><br>



<br>
  
## 9. 팀 소개
#### `Backend`


|이름|포지션|분담|@ Email|Github|
|------|------|------|------|------|
|[<img src="https://github.com/JungguKang.png" width="80">](https://github.com/JungguKang) <br> 강정구|BackEnd|검색(쿼리 최적화)<br>부하 테스트<br>캐싱<br/>||https://github.com/JungguKang|
|[<img src="https://github.com/JinJiyeon.png" width="80">](https://github.com/JinJiyeon) <br> 진지연|BackEnd|검색(쿼리 최적화)<br>부하 테스트<br>동시성 제어||https://github.com/JinJiyeon|
|[<img src="https://github.com/deingvelop.png" width="80">](https://github.com/deingvelop) <br> 송민진|BackEnd|검색(쿼리 최적화)<br>부하 테스트<br>로깅|m_6595@naver.com|https://github.com/deingvelop|

