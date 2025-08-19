<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 상세보기</title>
    <link rel="stylesheet" href="boardDetail.css">
</head>
<body>
    <header class="header">
        <h1>약국 게시판</h1>
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
    	<h2>${boardDto.btitle }</h2>
        <div class="post-detail">
            <p class="author">${boardDto.bnum }번째 게시글 | 조회수 : ${boardDto.bhit } | 작성 시간: ${boardDto.bdate }</p>
            <p><strong>작성자:</strong> ${boardDto.memberid }</p>
            <p><strong>글 내용:</strong><br>
            ${boardDto.bcontent }</p>
        </div>
        <div class="button-container">
            <a href="boardList.do" class="button">목록보기</a>
            <a href="boardEdit.do" class="button">수정</a>
            <a href="#" class="button">삭제</a>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 약국 게시판</p>
    </footer>
</body>
</html>
