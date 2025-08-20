package jsp11_mvc_250819.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jsp11_mvc_250819.dao.BoardDao;
import jsp11_mvc_250819.dao.MemberDao;
import jsp11_mvc_250819.dto.BoardDto;
import jsp11_mvc_250819.dto.BoardMemberDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
        String uri = request.getRequestURI();
        String comm = uri.substring(uri.lastIndexOf('/') + 1); // 주소만 가져옴. / 안 가져옴.
        System.out.println("comm : " + comm); // 콘솔창에 찍어주기
        
        HttpSession session = null;
        String viewpage = null;
        BoardDao boardDao = new BoardDao();
        MemberDao memberDao = new MemberDao(); 
        List<BoardDto> boardDtos = new ArrayList<>();
        List<BoardMemberDto> bmDtos = new ArrayList<>(); // board, member dto 합친 거
      
        
        if (comm.equals("index.do")) { // 홈화면
        	viewpage = "index.jsp";
        } else if (comm.equals("boardList.do")) { // 게시판 목록보기
        	boardDtos = boardDao.boardList();
        	request.setAttribute("boardDtos", boardDtos);
        	viewpage = "boardList.jsp";
        } else if (comm.equals("boardDetail.do")) { // 게시글 내용보기
        	String bnum = request.getParameter("bnum");
        	boardDao.updateHit(bnum);
        	BoardDto boardDto = boardDao.boardDetail(bnum);
        	if (boardDto == null) { // 글이 삭제 된 경우
            	request.setAttribute("msg", "존재하지 않은 게시글 입니다.");
        		/* 이렇게도 가능하다. jsp에서 getParameter로 가져오기
            	response.sendRedirect("boardDetail.jsp?msg=0");
            	return;
            	*/
        	} else {
            	request.setAttribute("boardDto", boardDto);
        	}
        	viewpage = "boardDetail.jsp";
        } else if (comm.equals("boardWrite.do")) { // 로그인 여부 확인 후 글 작성
        	session = request.getSession();
        	String sid = (String) session.getAttribute("sessionId");
        	if (sid != null) {
        		viewpage = "boardwrite.do";
        	} else {
        		response.sendRedirect("login.do?msg=2");
        		return;
        	}
        	viewpage = "boardWrite.jsp";
        } else if (comm.equals("boardWriteOk.do")) { // 글작성 버튼 누르고 난 후
        	String title = request.getParameter("title");
        	String content = request.getParameter("content");
        	String writer = request.getParameter("writer");
        	boardDao.boardWrite(title, content, writer);
        	
        	// form get, post 글 넘어가고 새로고침하면 버그 오류 생김. 안 나오게 하기 위한 조치
        	// forward 하면 안됨. 따라서 sendRedirect로 보내기.
        	response.sendRedirect("boardList.do");
        	return;
        } else if (comm.equals("boardEdit.do")) { // 글 수정
        	// 세션에 저장된 아이디 값을 불러와서 if문으로 
        	session = request.getSession();
        	String sid = (String) session.getAttribute("sessionId");
        	
        	// boardDetail 에서 button 수정을 눌렀을 때 넘어오는 bnum 값
        	String bnum = request.getParameter("bnum");
        	BoardDto boardDto = boardDao.boardDetail(bnum);
        	
        	// 작성자와 동일한 지 확인 후 수정 가능하게
        	if (boardDto.getMemberid().equals(sid)) {
        		request.setAttribute("boardDto", boardDto);
        		viewpage = "boardEdit.jsp";
        	} else {
        		response.sendRedirect("boardEdit.jsp?error=1");
        		return;
        	}
        } else if (comm.equals("boardEditOk.do")) { // 글 수정한 후 글 내용 보기
        	String bnum = request.getParameter("num");
        	String title = request.getParameter("title");
        	String content = request.getParameter("content");
        	String writer = request.getParameter("writer");
        	boardDao.boardUpdate(bnum, title, content);
        	
        	// 수정한 글을 Dto에 넣어주고 수정된 글 확인하기
        	BoardDto boardDto = boardDao.boardDetail(bnum);
        	request.setAttribute("boardDto", boardDto);
        	viewpage = "boardDetail.jsp";
        } else if (comm.equals("boardDelete.do")) { // 글 삭제
        	//String num = request.getParameter("bnum");
        	//boardDao.boardDelete(num);
        	//viewpage = "boardList.do"; // .jsp 아님. 삭제 후 게시글 목록 새롭게 불러온 거 확인 가능
       
        	String num = request.getParameter("bnum");
        	session = request.getSession();
        	String sid = (String) session.getAttribute("sessionId");
        	BoardDto boardDto = boardDao.boardDetail(num);
        	// 작성자와 동일한 지 확인 후 삭제 가능하게
        	if (boardDto.getMemberid().equals(sid)) {
        		boardDao.boardDelete(num);
        		viewpage = "boardList.jsp";
        	} else {
        		response.sendRedirect("boardEdit.jsp?error=1");
        		return;
        	}
        } else if (comm.equals("login.do")) { // 로그인 홈페이지 
        	viewpage = "login.jsp";
        }  else if (comm.equals("loginOk.do")) { 
        	String loginId = request.getParameter("username");
        	String loginPw = request.getParameter("password");
        	
        	// 메서드 이용해서 로그인 성공이면 1, 실패면 0 반환
        	int loginResult = memberDao.loginCheck(loginId, loginPw);
        	if (loginResult == 1) {
        		session = request.getSession();
        		session.setAttribute("sessionId", loginId);
        	} else {
        		response.sendRedirect("login.do?msg=1");
        		return;
        	}
        	viewpage = "boardList.do";
        } else {
        	viewpage = "index.do"; // 그 외 페이지는 다 홈페이지로 넘어가게
        }

        // 서블릿 내에서 제작한 request 객체를 전달해서 viewpage로 이동 시키기
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewpage);
        dispatcher.forward(request, response);
	}

}
