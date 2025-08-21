<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 상세보기</title>
    <link rel="stylesheet" href="css/boardDetailStyle.css">
</head>
<body>

<%
	// 게시글 내용 볼 때 만약 없는 게시글이라면 메시지 출력
	if (request.getAttribute("msg") != null) {
		String msginfo = (String) request.getAttribute("msg");
		out.println("<script> alert('"+ msginfo +"'); location.href = 'boardList.do'; </script>");
	}
	
	/* 삭제된 글일 경우 방법 2.
	System.out.println("msg : " + request.getParameter("msg"));
	if (request.getParameter("msg") != null) {
		out.println("<script> alert('해당 글은 존재하지 않습니다!'); location.href = 'boardList.do'; </script>");
	}
	*/
	

%>

<script>

</script>
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
            <p class="author">이메일 : ${boardDto.memberDto.memberemail } | 조회수 : ${boardDto.bhit } | 작성 시간: ${boardDto.bdate }</p>
            <p><strong>아이디:</strong> ${boardDto.memberid }</p>
            <p><strong>작성자:</strong> ${boardDto.memberDto.membername }</p>
            <p><strong>글 내용:</strong><br>
            ${boardDto.bcontent }</p>
        </div>
        <div class="button-container">
            <a href="boardList.do" class="button">목록보기</a>
            <a href="boardEdit.do?bnum=${boardDto.bnum }" class="button">수정</a>
            <a href="boardDelete.do?bnum=${boardDto.bnum }" class="button">삭제</a>
            
            <!-- 방법1 c:if 사용하기. 로그인한 아이디와 작성자가 동일한 경우만 수정, 삭제 버튼 보이게 해줌 -->
            <!--   <c:if test="${sessionScope.sessionId == boardDto.memberid }">
            <a href="boardEdit.do?bnum=${boardDto.bnum }" class="button">수정</a>
            <a href="boardDelete.do?bnum=${boardDto.bnum }" class="button">삭제</a>
            </c:if> -->
        </div>
    </main>

    <footer>
        <p>&copy; 2025 약국 게시판</p>
    </footer>
</body>
</html>
