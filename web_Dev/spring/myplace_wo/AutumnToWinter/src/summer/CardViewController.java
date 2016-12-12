package summer;

import java.io.IOException;
import com.oreilly.servlet.multipart.*;
import com.oreilly.servlet.MultipartRequest;


import java.time.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Time;


/**
 * Servlet implementation class CardViewController
 */
@WebServlet("*.cdo")
public class CardViewController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardViewController() {
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

	public void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		
		Command command = null;
		String ViewPage = null;
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());
		
		if(com.equals("/list.cdo")){
			command = new CardViewListCommand();
			command.execute(request, response);
			
			
			
			ViewPage = "card_view.jsp";
		}else if(com.equals("/write.cdo")){
			if(request.getSession().getAttribute("id") == null){
				System.out.println("noid");
				ViewPage = "login.html";
			}else{
				String id = request.getSession().getAttribute("id").toString();
				//String title = request.getParameter("title");
				//String content = request.getParameter("content");
				//System.out.println(id+title+content);
		
			    //String savePath = request.getServletContext().getRealPath("img/place/"+id);
				// C:\Users\Joo\JAVA\apache-tomcat-7.0.70\apache-tomcat-7.0.70\wtpwebapps\AutumnToWinter\img\place\ssss

				//String id = multi.getParameter("id");  
				String savePath = "C:/Users/Joo/JAVA/workspace/AutumnToWinter/WebContent/"+"img/place/"+id+"/";
				System.out.println(savePath);
			    int sizeLimit = 1024*1024*15; // 15MB
			 	MultipartRequest multi = new MultipartRequest(request,savePath,sizeLimit,"utf-8",new DefaultFileRenamePolicy());
			 	//Time.sleep(1000);
			 	

			 	System.out.println("request getContentType : " + request.getContentType());
			    String fileName = multi.getFilesystemName("u_file");
			    
			    String title = new String(multi.getParameter("title").getBytes("8859_1"),"utf-8");
			    String content = new String(multi.getParameter("content").getBytes("8859_1"),"utf-8");
			    String DBPath = "img/place/"+id+"/"+fileName;
			    
			    
			    
			    //title = new String(title.getBytes("8859_1"),"utf-8");
			    //content = new String(content.getBytes("8859_1"),"utf-8");
			    DBPath = new String(DBPath.getBytes("8859_1"),"utf-8");
			    
			    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
			    System.out.println("CardViewController.action.do.write.cdo : "+title+content+DBPath);
			    
				request.setAttribute("uploadedfilepath", DBPath);
				request.setAttribute("title", title);
				request.setAttribute("content", content);
				
				command = new CardViewWriteCommand();
				command.execute(request, response);
				ViewPage = "list.cdo";
			}
		}else if (com.equals("/details.cdo")){
			command = new CardViewDetailsCommand();
			int cardnumber = Integer.parseInt(request.getParameter("cardnumber").toString());
			String username = request.getParameter("username").toString();
			
			request.setAttribute("cardnumber", cardnumber);
			request.setAttribute("username",username);
			
			command.execute(request, response);
			ViewPage = "card_detail_view.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(ViewPage);
		dispatcher.forward(request,	response);
	}
}
