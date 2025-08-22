package jsp11_mvc_250819.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 부모 BoardCommand 인터페이스
public interface BoardCommand {
	public void execute(HttpServletRequest request, HttpServletResponse response);
}
