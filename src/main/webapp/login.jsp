<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link rel="stylesheet" href="loginStyle.css">
</head>
<body>
    <header>
        <h1><a href="index.do">우리동네 행복 약국</a></h1>
    </header>

    <main>
        <h2>로그인</h2>
        <form action="loginOk.do" method="POST">
            <label for="username">아이디</label>
            <input type="text" id="username" name="username" required>

            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" required>
            
            <div class="wrongmsg">
            	<c:if test="${param.msg == 1 }">
            		<p>아이디 또는 비밀번호가 잘못 되었습니다.</p>
            	</c:if>
            	<c:if test="${param.msg == 2 }">
            		<p>로그인 한 유저만 글을 쓸 수 있습니다.</p>
            	</c:if>
            </div>

            <button type="submit">로그인</button>
        </form>
        
        <div class="links">
            <a href="#">회원가입</a> | <a href="#">비밀번호 찾기</a>
        </div>
    </main>

    <footer>
        <p>© 2025 약국 게시판</p>
    </footer>
</body>
</html>
