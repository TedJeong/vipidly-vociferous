package summer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CardViewDetailsCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		CardViewDao cdao = new CardViewDao(); 
		String usrn = request.getAttribute("username").toString();
		int crn = Integer.parseInt(request.getAttribute("cardnumber").toString());
		//String id = request.getSession().getAttribute("id").toString();
		
		CardDto cdto = cdao.CardSelect(crn, usrn);
		request.setAttribute("selectedcard", cdto);
	}

}
