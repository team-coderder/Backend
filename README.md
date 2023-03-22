<h6 align="center"> 그룹 일정 관리 웹서비스</h6>
<h2 align="center">ColorMeeting</h2>

![image](https://user-images.githubusercontent.com/100582309/226905677-11b85c2f-efec-4632-b0a0-e11fcd76eda7.png)
 - **[시연 영상]()<br>**
 - **[배포 사이트]()<br>**
 - **[팀 노션 주소](https://www.notion.so/coderder/Coderder-f4aec4bc4da242349797c0d6ebf9e766)**

<br><br>


## 1.프로젝트 소개
매번 일정 잡을 때마다 고생이시죠? 이제는 한 번에 확인하세요.<br>
**그룹원들의 일정을 한 눈에 확인**하고, 그룹원들의 비는 시간들을 도합하여 **최적의 모임 시간을 추천**해주는 서비스

### ✅ 주요 기능
- **회원 기능**<br>
  회원가입/로그인/로그아웃 및 마이페이지를 통해, 자신만의 일정을 관리하고 조회할 수 있습니다.
  
- **개인 일정 기능**<br>
  나의 개인 일정을 자유롭게 등록/수정/삭제할 수 있습니다. 개인 일정을 수정하면, 내가 속한 그룹원들도 실시간으로 확인할 수 있습니다. 내가 속한 다른 그룹원들은 어느 시간에 바쁘고 어느 시간에 괜찮은지 역시 확인할 수 있습니다.
  
- **그룹 일정 기능**<br>
  그룹 리더는 그룹 페이지에서 그룹 일정을 등록할 수 있습니다. (그룹 리더 외의 그룹원은 등록 불가능합니다.) 리더가 등록한 그룹 일정은 그룹원들 모두 각자의 개인 페이지에서도 확인할 수 있습니다.
  
- **모임 추천 기능**<br>
  그룹원들이 각자 등록한 일정에 따라, 가장 많은 그룹원들이 되는 시간을 자동으로 추천해줍니다.

- **그룹 관리 기능**<br>
  누구나 그룹을 생성/수정/삭제할 수 있습니다. 그룹을 처음 생성한 유저가 그룹 리더가 됩니다.
  
- **그룹 초대 기능**<br>
  그룹에 다른 유저들을 초대할 때는, 초대장을 보낼 수 있습니다. 리더는 회원가입을 완료한 Coderder 유저들의 아이디를 검색하여 초대장을 보냅니다. 초대장을 받은 유저는 초대장을 수락하여 그룹에 일원이 될 수도 있고, 초대장을 거절할 수도 있습니다.
  
  
### 👥 팀 소개
#### `Backend`
<a href="https://github.com/JungguKang" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=Spring&message=강정구 &color=08CE5D&style=for-the-badge&>"/></a>
<a href="https://github.com/JinJiyeon" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=Spring&message=진지연 &color=08CE5D&style=for-the-badge&>"/></a>
<a href="https://github.com/deingvelop" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=Spring&message=송민진 &color=08CE5D&style=for-the-badge&>"/></a>


### 📜 기술스택
|분류|기술|
| :-: |:- |
|Language|<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">|
|Framework|<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white">|
|Build Tool|<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">|
|DB|<img src="https://img.shields.io/badge/postgresql-4479A1?style=for-the-badge&logo=postgresql&logoColor=white">|
|Server|<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=AmazonAWS&logoColor=white"> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">|
|CI/CD|<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"> <img src="https://img.shields.io/badge/codedeploy-6DB33F?style=for-the-badge&logo=codedeploy&logoColor=white">|


<br><br>


<br><br>







## 🏰 아키텍쳐
<!-- <details>
<summary> <b>아키텍쳐 바로보기</b> </summary>
    <img src="https://user-images.githubusercontent.com/100582309/196345657-eaf613d7-01bc-4118-8df8-b08198704d5f.png"> 
</details> -->


<img src="https://user-images.githubusercontent.com/100582309/196345657-eaf613d7-01bc-4118-8df8-b08198704d5f.png"> 

<br><br>



## 📕 ER 다이어그램     
    
<!-- <details>
<summary> <b>ERD 바로보기</b> </summary>
    <img src="https://user-images.githubusercontent.com/100582309/196347173-6b00b013-187e-4a4c-92b0-ab36f4a84779.png"> 
</details> -->

<img src="https://user-images.githubusercontent.com/100582309/196347173-6b00b013-187e-4a4c-92b0-ab36f4a84779.png"> 

<br><br>



## ✨ 기술적인 도전

1. Github actions + Codedeploy를 사용하여 자동화 배포 적용

2. (2차 배포) PostgreSQL의 ORDBMS 특징을 활용하여 DB에 List형태로 데이터를 삽입하여 table의 row 수 감축

3. (2차 배포) Apache Kapka를 통한 멤버 초대시 알림 Push 기능 구현

4. (2차 배포) JWT를 이용한 로그인/회원가입 기능에 캐싱 도입

<br />
