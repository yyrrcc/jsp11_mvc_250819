/*
package jsp11_mvc_250819.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsp11_mvc_250819.dto.BoardDto;

public class BoardDeleteCommand implements BoardCommand{
	
	public void execute(HttpServletRequest request, HttpServletResponse response) {
    	String num = request.getParameter("bnum");
    	session = request.getSession();
    	String sid = (String) session.getAttribute("sessionId");
    	BoardDto boardDto = boardDao.boardDetail(num);
	}

}
*/