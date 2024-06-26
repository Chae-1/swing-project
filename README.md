# 프로젝트 개요
프로젝트 명 : BookLink
프로젝트 기간 : 2024-05-20 ~ 2024-05-31

팀장 : 채형일

팀원 : 김호영

## **개요**
기존 도서 이커머스에서는 고객이 주문한 도서에 대한 사후 서비스를 제공해주지 않다는 점, 그리고 도서를 구매하지 않은 고객이 그 도서를 평가할 수 있었던 문제점 등 부가적인 개선사항이 존재합니다. 도서 이커머스의 이런 문제점을 개선하고자 프로젝트를 제안하게 됐습니다.

## **차별점**

### **1. 커뮤니티 기능 제공**

- **목적**: 여러 도서에 대한 정보를 교환할 수 있는 커뮤니티를 제공하여 사용자들이 도서에 대해 자유롭게 의견을 나누고, 구매자들로부터 즉각적인 피드백을 받을 수 있도록 함.

### **2. 리뷰 평가 기능 수정**

- **문제점**: 도서를 구매하지 않은 사용자도 평점 및 리뷰를 작성할 수 있는 기능이 오류로 판단됨.
- **개선**: 이 기능을 수정하여 책 구매자의 평점만 리뷰에 반영할 수 있도록 변경함으로써 리뷰의 신뢰성을 높임.

### **3. 사용자 선호 카테고리 조회 시스템 기능 제공**

- **문제점**: 선택된 단일 카테고리의 도서 정보만 보여지게 되어 사용자가 원하는 여러 카테고리의 도서 정보를 확인하기 어려움.
- **개선**: 사용자가 직접 관심 있는 도서 카테고리를 설정할 수 있는 기능을 추가하여, 개인의 선호도에 맞춘 도서 정보를 볼 수 있도록 함.

### 사용 기술
- Java 8+, JDBC, Swing 
- Oracle Database, SQL, PL/SQL

## 구현 
- 사용자 등록 및 로그인
    - 사용자 계정 관리
        - 로그인/ 로그아웃
        - 마이페이지
        - 프로필 ( 차별점 ( 사용자 선호 도서 카테고리 기능 ))
## 구현 목표
- 사용자 등록 및 로그인 V
    - 사용자 계정 관리 V
        - 로그인/ 로그아웃 V
        - 마이페이지 V
        - 프로필 ( 차별점 ( 사용자 선호 도서 카테고리 기능 )) X
- 도서 기능
    - 도서 리스트 조회 V
    - 도서 상세내용 V
    - 도서 검색기능 V
- 도서 상품 관련 기능
    - 도서 주문 V
    - 도서 리뷰 ( 차별점 ( 도서 구매 후 리뷰작성 기능 ) ) V
    - 추천 도서 ( 차별점 ( 사용자 선호 도서 카테고리 기능 )) V
    - 관련 서적 구매 이용자에 대한 실시간 채팅 기능 (차별점 ( 시간 여유 있을 때)) X
    - 도서 주문
    - 도서 리뷰 ( 차별점 ( 도서 구매 후 리뷰(별점) 작성 기능 ) )
