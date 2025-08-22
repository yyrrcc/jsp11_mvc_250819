package jsp11_mvc_250819.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsp11_mvc_250819.dao.BoardDao;
import jsp11_mvc_250819.dto.BoardDto;

// 부모 BoardCommand 상속
public class BoardEditOkCommand implements BoardCommand{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		BoardDao boardDao = new BoardDao();

    	String bnum = request.getParameter("num");
    	String title = request.getParameter("title");
    	String content = request.getParameter("content");
    	String writer = request.getParameter("writer");
    	boardDao.boardUpdate(bnum, title, content);
    	
    	BoardDto boardDto = boardDao.boardDetail(bnum);
    	request.setAttribute("boardDto", boardDto);
    }
}
