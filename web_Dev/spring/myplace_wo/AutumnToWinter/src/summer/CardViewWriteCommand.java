package summer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CardViewWriteCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getSession().getAttribute("id").toString();
		
		//String title = request.getParameter("title");
		//String content = request.getParameter("content");
		String title = request.getAttribute("title").toString();
		String content = request.getAttribute("content").toString();
		String uploadedfilepath = request.getAttribute("uploadedfilepath").toString();
		System.out.println("CardViewWriteCommand : "+uploadedfilepath);
		System.out.println("CardViewWriteCommand : "+id+title+content);
		CardViewDao dao = new CardViewDao();
		dao.CardWrite(id,title,content,uploadedfilepath);
	}
}
