package jsp11_mvc_250819.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsp11_mvc_250819.dao.BoardDao;

// 부모 BoardCommand 상속
public class BoardWriteOkCommand implements BoardCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		BoardDao boardDao = new BoardDao();
		String title = request.getParameter("title");
    	String content = request.getParameter("content");
    	String writer = request.getParameter("writer");
    	boardDao.boardWrite(title, content, writer);
    }

}
