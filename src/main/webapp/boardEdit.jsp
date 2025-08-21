<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 수정</title>
    <link rel="stylesheet" href="css/boardEditStyle.css">
</head>
<body>
<%
	/* 방법2. 작성자와 동일한 지 확인 후 수정, 삭제 하기 */
	if (request.getParameter("error") != null) {
		out.println("<script> alert('수정 권한이 없습니다.'); history.go(-1); </script>");
	}
%>
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
        <section class="post-edit">
            <h2>게시글 수정</h2>
            <form action="boardEditOk.do" method="post">
            	<!-- 해당 글의 번호를 hidden로 숨겨놓고 form으로 보내기 -->
            	<input type="hidden" name="num" value="${boardDto.bnum }">
            	
            	<label for="writer">작성자:</label>
                <input type="text" id="writer" name="writer" value="${boardDto.memberid }" readonly><br>
            
                <label for="title">제목:</label>
                <input type="text" id="title" name="title" value="${boardDto.btitle }" required><br><br>

                <label for="content">내용:</label><br>
                <textarea id="content" name="content" rows="10" cols="30" required>${boardDto.bcontent }</textarea><br><br>
				
				<div class="button-container">
                <button type="submit" class="button">수정 완료</button>
                <a href="javascipt:history.go(-1)" class="button">취소</a>
                <a href="boardList.do" class="button">목록보기</a>
                </div>
            </form>
        </section>
    </main>

    <footer>
        <p>&copy; 2025 약국 게시판</p>
    </footer>
</body>
</html>
