<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 수정</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <header class="header">
        <h1>약국 게시판</h1>
        <nav>
            <ul>
                <li><a href="index.jsp">홈</a></li>
                <li><a href="boardList.jsp">게시글 목록</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <section class="post-edit">
            <h2>게시글 수정</h2>
            <form action="#" method="post">
                <label for="title">제목:</label>
                <input type="text" id="title" name="title" value="감기약 종류 추천" required><br><br>

                <label for="content">내용:</label><br>
                <textarea id="content" name="content" rows="10" cols="30" required>감기에 효과적인 약의 종류를 추천해드립니다. 다양한 약물에 대한 설명과 함께 복용 방법을 알려드립니다. 복용 시 주의사항도 확인하세요.</textarea><br><br>

                <button type="submit">수정 완료</button>
            </form>
        </section>
    </main>

    <footer>
        <p>&copy; 2025 약국 게시판</p>
    </footer>
</body>
</html>
