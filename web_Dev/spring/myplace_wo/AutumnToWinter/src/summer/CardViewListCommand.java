package summer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

public class CardViewListCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		CardViewDao cdao = new CardViewDao(); 
		ArrayList<CardDto> cdtos = new ArrayList<>();
		cdtos = cdao.CardList();
		request.setAttribute("clist", cdtos);
	}

}
