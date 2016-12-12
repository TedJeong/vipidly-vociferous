package summer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberCheckCommand implements Command{

	@Override
	public void execute(HttpServletRequest request,HttpServletResponse response){
		
		MemberListDao mdao = new MemberListDao();
		String id = request.getParameter("username");
		String pw = request.getParameter("password");
		String mtf = mdao.MemberCheck(id,pw);
		
		if(mtf.equals("true")){
			/*
			HttpSession session = request.getSession();
			session.getAttribute("id");
			*/
			request.setAttribute("id", id);
			Object tmp = request.getAttribute("id");
			System.out.println("================================checker<br/>");
			System.out.println("tmp:"+tmp.toString()+"\n");
	    	System.out.println("=========================================<br/>");
			Cookie cookie = new Cookie("id",id);
			cookie.setMaxAge(60);
			response.addCookie(cookie);
			
		}else{
			
		}
		request.setAttribute("membertf", mtf);
	}
}
