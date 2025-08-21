<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 목록</title>
    <link rel="stylesheet" href="css/boardListStyle.css">
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
        <h2 class="title">게시글 목록</h2>
        <h4 class="logining">
        	<c:if test="${not empty sessionScope.sessionId }">
        		${sessionScope.sessionId }님 로그인 중
        	</c:if>
        </h4>
        <div class="wrapper">
        	<a href="boardWrite.do" class="button">글쓰기</a> 
        	
        	<form action="#" method="get">
        		<select name="searchType" class="searchs">
        			<option value="btitle">제목</option>
        			<option value="bcontent">내용</option>
        			<option value="searchTC">제목 + 내용</option>
        			<option value="b.memberid">작성자</option>
        			</select>
        		<input type="text" name="searchKeyword" placeholder="검색어를 입력하세요" class="searchs">
        		<input type="submit" class="searchbutton" value="검색">
        		<button type="submit" class="searchbutton">검색버튼</button>
        	</form>
        	
	        	<table class="table">
	                <thead>
	                    <tr class="header">
	                        <th>번호</th>
	                        <th>제목</th>
	                        <th>아이디</th>
	                        <th>이메일</th>
	                        <th>조회수</th>
	                        <th>작성 시간</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <!-- c:forEach로 돌려주기 -->
						<c:forEach items="${boardDtos }" var="boardDto" >
							<tr>
			                    <td>${boardDto.bno }</td>
								<td><a href="boardDetail.do?bnum=${boardDto.bnum }">${boardDto.btitle }</a></td>
			                    <td>${boardDto.memberid }</td>
			                    <td>${boardDto.memberDto.memberemail }</td> <!-- 이메일 추가 -->
			                    <td>${boardDto.bhit }</td>
			                    <td>${fn:substring(boardDto.bdate,0,10) }</td> <!-- 작성 날짜만 뽑아내기 -->           
			                </tr>
	          	      </c:forEach>
	                </tbody>
	            </table>
        </div>
    </main>

    <footer>
        <p>&copy; 2025 약국 게시판</p>
    </footer>
</body>
</html>