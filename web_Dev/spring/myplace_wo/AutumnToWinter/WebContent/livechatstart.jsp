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
	        // ����� hashmap ������(Key, value) ����
	        clients = new HashMap<String, DataOutputStream>();
	        // clients ����ȭ
	        Collections.synchronizedMap(clients);
	    }
	    public void start() {
	        
	        // Port ���� ���Ǹ����� 4444�� ���� (Random������ ���氡��)
	        int port = 4444;
	        Socket socket = null;
	 
	        try {
	            // �������� ������ while������ �����Ͽ� accept(���)�ϰ� ���ӽ� ip�ּҸ� ȹ���ϰ� ����ѵ�
	            // MultiThread�� �����Ѵ�.
	            ServerSocket = new ServerSocket(port);
	            System.out.println("���Ӵ����");
	            while (true) {
	                socket = ServerSocket.accept();
	                InetAddress ip = socket.getInetAddress();
	                // TODO : session ���� login�� �ش��ϴ� id �� �ҷ�����
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
	                // ��ü�� �ְ���� Stream�����ڸ� �����Ѵ�.
	                input = new DataInputStream(socket.getInputStream());
	                output = new DataOutputStream(socket.getOutputStream());
	            } catch (IOException e) {
	            }
	        }
	 
	        public void run() {
	 
	            try {
	                // ���ӵ��� �ٷ� Mac �ּҸ� �޾ƿ� ����ϰ� clients�� ������ �Ѱ��ְ� Ŭ���̾�Ʈ���� mac�ּҸ�������.
	                mac = input.readUTF();
	                System.out.println("Mac address : " + mac);
	                clients.put(mac, output);
	                sendMsg(mac + "   ����");
	 
	                // TODO : DB���� mac address�� id �ҷ�����
	                
	                // ���Ŀ� ä�ø޼������Ž�
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
	 
	        // �޼��������� Ŭ���̾�Ʈ���� Return �� sendMsg �޼ҵ�
	        private void sendMsg(String msg) {
	 
	            // clients�� Key���� �޾Ƽ� String �迭�μ���
	            Iterator<String> it = clients.keySet().iterator();
	 
	            // Return �� key���� ����������
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