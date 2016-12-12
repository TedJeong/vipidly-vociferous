package summer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import summer.MemberListDao;
import summer.MemberDto;

public class MemberListCommand implements Command {
	@Override
	public void execute(HttpServletRequest request,HttpServletResponse response){
		
		MemberListDao mdao = new MemberListDao();
		ArrayList<MemberDto> dtos = mdao.MemberList();
		
		request.setAttribute("mlist", dtos);
	}
}
