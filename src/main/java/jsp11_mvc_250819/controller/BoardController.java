package jsp11_mvc_250819.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsp11_mvc_250819.dao.BoardDao;
import jsp11_mvc_250819.dto.BoardDto;

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
        
        String viewpage = null;
        BoardDao boardDao = new BoardDao();
        List<BoardDto> boardDtos = new ArrayList<>();
        
        if (comm.equals("index.do")) { // 홈화면
        	viewpage = "index.jsp";
        } else if (comm.equals("boardList.do")) { // 게시판 목록보기
        	boardDtos = boardDao.boardList();
        	request.setAttribute("boardDtos", boardDtos);
        	viewpage = "boardList.jsp";
        } else if (comm.equals("boardDetail.do")) { // 게시글 내용보기
        	String bnum = request.getParameter("bnum");
        	BoardDto boardDto = boardDao.boardDetail(bnum);
        	if (boardDto == null) { // 글이 삭제 된 경우
            	request.setAttribute("msg", "존재하지 않은 게시글 입니다.");
        	} else {
            	request.setAttribute("boardDto", boardDto);
        	}
        	viewpage = "boardDetail.jsp";
        } else if (comm.equals("boardWrite.do")) { // 글작성
        	
        	viewpage = "boardWrite.jsp";
        } else if (comm.equals("boardWriteOk.do")) { // 글작성 버튼 누르고 난 후
        	String title = request.getParameter("title");
        	String content = request.getParameter("content");
        	String writer = request.getParameter("writer");
        	boardDao.boardWrite(title, content, writer);
        	viewpage = "boardList.do";
        } else if (comm.equals("boardEdit.do")) { // 글 수정
        	
        	viewpage = "boardEdit.jsp";
        } else if (comm.equals("boardDelete.do")) { // 글 삭제
        	
        	viewpage = "boardList.do"; // .jsp 아님. 삭제 후 게시글 목록 새롭게 불러온 거 확인 가능
        }

        // 서블릿 내에서 제작한 request 객체를 전달해서 viewpage로 이동 시키기
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewpage);
        dispatcher.forward(request, response);
	}

}
