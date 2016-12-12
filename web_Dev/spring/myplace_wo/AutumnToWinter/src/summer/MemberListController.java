package summer;

import java.io.IOException;
import java.io.*;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MemberListController
 */
@WebServlet("*.do")
public class MemberListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		actionDo(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo");
		request.setCharacterEncoding("EUC-KR");
		
		// 요청후 어떤 뷰를 보여줄지
		String viewPage = null;
		// 어떤 로직을 실행 할까
		Command command = null;
		
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());
		System.out.println("memberlistcontroller called!");
		
		if(com.equals("/memberlistlogin.do")){
			command = new MemberCheckCommand();
			command.execute(request,response);
			
			Object tmp = request.getAttribute("id");
			request.getSession().setAttribute("id", tmp.toString());
			System.out.println("================================controller<br/>");
			System.out.println(request.getSession().getAttribute("id") + " 님 환영합니다");
        	System.out.println("=========================================<br/>");
			
			if( request.getAttribute("membertf").toString().equals("true") ){
				viewPage = "index.jsp";
			}else{
				viewPage = "login.html";
			}
			
		}else if(com.equals("/memberlistsignin.do")){
			command = new MemberEnlistCommand();
			command.execute(request, response);
			String username = request.getParameter("username");
			
			
			// 최초 아이디 생성시 웹 컨테이너에 계정 폴더 생성
			
			String fileName = "userinfo.txt"; //생성할 파일명
			String fileDir = username; //파일을 생성할 디렉토리
			String filePath = "C:/Users/Joo/JAVA/workspace/AutumnToWinter/WebContent/img/place/" + fileDir + "/"; //파일을 생성할 전체경로
			File fPath = new File(filePath); //경로생성
			
			Calendar cal = Calendar.getInstance();
			String yStr = ""+cal.get(Calendar.YEAR); // 올해 연도
			String mStr = ""+(cal.get(Calendar.MONTH)+1); // 현재 월
			String dStr = ""+cal.get(Calendar.DATE);
			String hStr = ""+cal.get(Calendar.HOUR);
			String miStr = ""+cal.get(Calendar.MINUTE);
			
			
			
			if ( !fPath.exists() ) {
				fPath.mkdirs(); //상위 디렉토리가 존재하지 않으면 상위디렉토리부터 생성.
			}
			filePath += fileName; //생성할 파일명을 전체경로에 결합
			try {
				File f = new File(filePath); // 파일객체생성
				f.createNewFile(); //파일생성
				// 파일쓰기
				FileWriter fw = new FileWriter(filePath); //파일쓰기객체생성
				String data = "파일에다 아무거나 적습니다."+System.getProperty("line.separator")
				+"이거는 두번째 줄인데 아무거나 적습니다"
				+System.getProperty("line.separator")
				+"Sign in Date : "+yStr+":"+mStr+":"+dStr+":"+hStr+":"+miStr;
				fw.write(data); //파일에다 작성
				fw.close(); //파일핸들 닫기
				
				// 파일읽기
				
				/*
				FileReader fr = new FileReader(filePath); //파일읽기객체생성
				BufferedReader br = new BufferedReader(fr); //버퍼리더객체생성
				String line = null; 
				while((line=br.readLine())!=null) { //라인단위 읽기
					out.println(line + "<br>"); 
				}
				*/
			} catch (IOException e) { 
				System.out.println(e.toString()); //에러 발생시 메시지 출력
			}
			
			
			
			request.getSession().setAttribute("id", username);
			viewPage = "index.jsp";
		
		}else if(com.equals("/memberlistlogout.do")){
			// del cookies
			Cookie[] cookies = request.getCookies();
			for(int i=0;i<cookies.length;i++){
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
			// del session
			request.getSession().invalidate();
			viewPage = "index.jsp";
		}
		
		//fowarding 기법
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
	}
}
