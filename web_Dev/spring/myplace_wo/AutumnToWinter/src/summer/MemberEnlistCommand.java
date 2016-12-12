package summer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberEnlistCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		String province = request.getParameter("province");
		
		System.out.println("==============================");
		System.out.println("MemberenlistCommand()");
		System.out.println(email+password+username+province);
		System.out.println("==============================");
		MemberListDao dao = new MemberListDao();
		
		dao.MemberWrite(email,password,username,province);
	}
	
}
