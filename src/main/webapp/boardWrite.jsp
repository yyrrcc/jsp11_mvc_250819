<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 작성</title>
    <link rel="stylesheet" href="boardWrite.css">
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
        <section class="post-write">
            <h2>새 게시글 작성</h2>
            <form action="boardWriteOk.do" method="post">
            	<label for="title">제목:</label>
                <input type="text" id="title" name="title" placeholder="게시글 제목을 입력하세요" required><br>

                <label for="content">내용:</label><br>
                <textarea id="content" name="content" rows="10" cols="30" placeholder="게시글 내용을 입력하세요" required></textarea><br>
				
				<label for="writer">작성자:</label>
                <input type="text" id="writer" name="writer" placeholder="작성자를 입력하세요" required><br>
                
                <button type="submit" class="button">게시글 작성</button>
                <button type="reset" class="button">다시 작성</button>
            </form>
        </section>
    </main>

    <footer>
        <p>&copy; 2025 약국 게시판</p>
    </footer>
</body>
</html>
