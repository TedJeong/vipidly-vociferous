<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import = "java.io.DataInputStream,java.io.DataOutputStream,java.io.IOException,java.io.OutputStream,
java.net.InetAddress,java.net.ServerSocket,java.net.Socket,java.util.Collections,java.util.HashMap,java.util.Iterator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
    <fieldset>
        <textarea id="messageWindow" rows="10" cols="50" readonly="true"></textarea>
        <br/>
        <input id="inputMessage" type="text"/>
        <input type="submit" value="send" onclick="send()" />
    </fieldset>
    

	</body>
</html>

      
<%!
	public class SocketClient {
	
		
	    HashMap<String, DataOutputStream> clients;
	    private ServerSocket ServerSocket = null;
	 
	    public SocketClient() {
	        // 연결부 hashmap 생성자(Key, value) 선언
	        clients = new HashMap<String, DataOutputStream>();
	        // clients 동기화
	        Collections.synchronizedMap(clients);
	    }
	    public void start() {
	        
	        // Port 값은 편의를위해 4444로 고정 (Random값으로 변경가능)
	        int port = 4444;
	        Socket socket = null;
	 
	        try {
	            // 서버소켓 생성후 while문으로 진입하여 accept(대기)하고 접속시 ip주소를 획득하고 출력한뒤
	            // MultiThread를 생성한다.
	            ServerSocket = new ServerSocket(port);
	            System.out.println("접속대기중");
	            while (true) {
	                socket = ServerSocket.accept();
	                InetAddress ip = socket.getInetAddress();
	                // TODO : session 에서 login시 해당하는 id 값 불러오기
	                System.out.println(ip + "  connected");
	                new MultiThread(socket).start();
	            }
	        } catch (IOException e) {
	            System.out.println(e);
	        }
	    }
	 
	    class MultiThread extends Thread {
	 
	        Socket socket = null;
	 
	        String mac = null;
	        String msg = null;
	 
	        DataInputStream input;
	        DataOutputStream output;
	 
	        public MultiThread(Socket socket) {
	            this.socket = socket;
	            try {
	                // 객체를 주고받을 Stream생성자를 선언한다.
	                input = new DataInputStream(socket.getInputStream());
	                output = new DataOutputStream(socket.getOutputStream());
	            } catch (IOException e) {
	            }
	        }
	 
	        public void run() {
	 
	            try {
	                // 접속된후 바로 Mac 주소를 받아와 출력하고 clients에 정보를 넘겨주고 클라이언트에게 mac주소를보낸다.
	                mac = input.readUTF();
	                System.out.println("Mac address : " + mac);
	                clients.put(mac, output);
	                sendMsg(mac + "   접속");
	 
	                // TODO : DB에서 mac address로 id 불러오기
	                
	                // 그후에 채팅메세지수신시
	                while (input != null) {
	                    try {
	                        String temp = input.readUTF();
	                        sendMsg(temp);
	                        System.out.println(temp);                    
	                        
	                    } catch (IOException e) {
	                        sendMsg("No massege");
	                        break;
	                    }
	                }
	            } catch (IOException e) {
	                System.out.println(e);
	            }
	        }
	 
	        // 메세지수신후 클라이언트에게 Return 할 sendMsg 메소드
	        private void sendMsg(String msg) {
	 
	            // clients의 Key값을 받아서 String 배열로선언
	            Iterator<String> it = clients.keySet().iterator();
	 
	            // Return 할 key값이 없을때까지
	            while (it.hasNext()) {
	                try {
	                    OutputStream dos = clients.get(it.next());
	                    // System.out.println(msg);
	                    DataOutputStream output = new DataOutputStream(dos);
	                    output.writeUTF(msg);
	 
	                } catch (IOException e) {
	                    System.out.println(e);
	                }
	            }
	        }
	    }
}
%>

<%
	SocketClient sc = new SocketClient();
	sc.start();
%>