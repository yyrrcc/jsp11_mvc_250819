<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>약국</title>
    <link rel="stylesheet" href="css/indexStyle.css">
    <!--  기본 indexStyle.css / 공통 만들고 싶다-->
</head>
<body>
    <header class="header">
        <h1>우리동네 행복 약국</h1>
        <nav>
            <ul>
                <li><a href="index.do">홈</a></li>
                <li><a href="#">약국소개</a></li>
                <li><a href="boardList.do">자유게시판</a></li>
                <li><a href="#">고객센터</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <section class="intro">
            <h2>우리동네 행복 약국 홈페이지에 오신 것을 환영합니다</h2>
            <p>약국에 관한 다양한 정보와 팁을 공유할 수 있는 게시판입니다. <br/>
            약에 관한 궁금한 점을 질문하거나, 약국 운영에 관한 이야기를 나누세요.</p>
            <!-- <a href="login.do" class="button">로그인</a>  -->

			<c:choose>
				<c:when test="${not empty sessionScope.sessionId }">
	            	<a href="boardList.do" class="button">게시판 바로가기</a>
	            </c:when>
	            <c:otherwise>
	            	<a href="login.do" class="button">로그인</a>
	            </c:otherwise>
            </c:choose>
        </section>
    </main>

    <footer>
        <p>&copy; 2025 약국 게시판</p>
    </footer>
</body>
</html>
