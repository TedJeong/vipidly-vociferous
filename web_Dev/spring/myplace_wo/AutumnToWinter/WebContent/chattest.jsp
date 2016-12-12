<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	    <!-- Bootstrap Core CSS -->
	    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	    <!-- Custom Fonts -->
	    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
	    <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
	    <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
	    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>
		<link href="css/chatpopup.css" rel="stylesheet">
				
		<title>Insert title here</title>
	</head>
	<body>
		<div class="container text-center">
			<div class="row">
				<h2>Open in chat (popup-box chat-popup)</h2>
		        <h4>Click Here</h4>
		        <div class="round hollow text-center">
		        <a href="#" id="addClass"><span class="glyphicon glyphicon-comment"></span> Open in chat </a>
		        </div>
		        
		        <hr>
		        
		        MORE :
		        <a target="_blank" href="http://bootsnipp.com/snippets/33ejn">Whatsapp Chat Box POPUP</a>,
		         <a target="_blank" href="http://bootsnipp.com/snippets/z4P39"> Creative User Profile  </a>
		         
			</div>
		</div>
		
		<div class="popup-box chat-popup" id="qnimate">
		    		<div class="popup-head">
						<div class="popup-head-left pull-left"><img src="http://bootsnipp.com/img/avatars/bcf1c0d13e5500875fdd5a7e8ad9752ee16e7462.jpg" alt="iamgurdeeposahan"> Gurdeep Osahan</div>
							  <div class="popup-head-right pull-right">
								<div class="btn-group">
		    								  <button class="chat-header-button" data-toggle="dropdown" type="button" aria-expanded="false">
											   <i class="glyphicon glyphicon-cog"></i> </button>
											  <ul role="menu" class="dropdown-menu pull-right">
												<li><a href="#">Media</a></li>
												<li><a href="#">Block</a></li>
												<li><a href="#">Clear Chat</a></li>
												<li><a href="#">Email Chat</a></li>
											  </ul>
								</div>
								
								<button data-widget="remove" id="removeClass" class="chat-header-button pull-right" type="button"><i class="glyphicon glyphicon-off"></i></button>
		                      </div>
					  </div>
					
					<textarea class="popup-messages" id="messageWindow" rows="10" cols="35" readonly="true"></textarea>
					
					<div class="popup-messages-footer">
						<input id="inputMessage" onkeydown="if(event.keyCode == 13) chkEnter();" placeholder="Type a message..." rows="10" cols="40" name="message"></input>
						<button type="submit" id="sendbutton" onclick="send()" value="send">send</button>
						<div class="btn-footer">
							<button class="bg_none"><i class="glyphicon glyphicon-film"></i> </button>
							<button class="bg_none"><i class="glyphicon glyphicon-camera"></i> </button>
				            <button class="bg_none"><i class="glyphicon glyphicon-paperclip"></i> </button>
							<button class="bg_none pull-right"><i class="glyphicon glyphicon-thumbs-up"></i> </button>
						</div>
					</div>
		  </div>
		  
	
	    <!-- jQuery -->
	    <script src="vendor/jquery/jquery.min.js"></script>
	
	    <!-- Bootstrap Core JavaScript -->
	    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	
	    <!-- Plugin JavaScript -->
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
	
		  <script language="javascript">
		  $(function(){
			  $("#addClass").click(function () {
			            $('#qnimate').addClass('popup-box-on');
			              });
			            
			              $("#removeClass").click(function () {
			            $('#qnimate').removeClass('popup-box-on');
			              });
			    })
		  </script>
		  
	    <script type="text/javascript">
	        var textarea = document.getElementById("messageWindow");
	        var webSocket = new WebSocket('ws://localhost:8181/AutumnToWinter/broadcasting');
	        var inputMessage = document.getElementById("inputMessage");
	        
	        function chkEnter(){
	        	if(event.which || event.keyCode){
	        		if((event.which == 13) || (event.keyCode==13)){
	        			document.getElementById('sendbutton').click();
	        			return false;
	        		}
	        	}else{
	        		return true;
	        	}
	        }
	        
	        
	        $(document).on('click', '.panel-heading span.icon_minim', function (e) {
	            var $this = $(this);
	            if (!$this.hasClass('panel-collapsed')) {
	                $this.parents('.panel').find('.panel-body').slideUp();
	                $this.addClass('panel-collapsed');
	                $this.removeClass('glyphicon-minus').addClass('glyphicon-plus');
	            } else {
	                $this.parents('.panel').find('.panel-body').slideDown();
	                $this.removeClass('panel-collapsed');
	                $this.removeClass('glyphicon-plus').addClass('glyphicon-minus');
	            }
	        });
	        $(document).on('focus', '.panel-footer input.chat_input', function (e) {
	            var $this = $(this);
	            if ($('#minim_chat_window').hasClass('panel-collapsed')) {
	                $this.parents('.panel').find('.panel-body').slideDown();
	                $('#minim_chat_window').removeClass('panel-collapsed');
	                $('#minim_chat_window').removeClass('glyphicon-plus').addClass('glyphicon-minus');
	            }
	        });
	        $(document).on('click', '#new_chat', function (e) {
	            var size = $( ".chat-window:last-child" ).css("margin-left");
	             size_total = parseInt(size) + 400;
	            alert(size_total);
	            var clone = $( "#chat_window_1" ).clone().appendTo( ".container" );
	            clone.css("margin-left", size_total);
	        });
	        $(document).on('click', '.icon_close', function (e) {
	            //$(this).parent().parent().parent().parent().remove();
	            $( "#chat_window_1" ).remove();
	        });

		    webSocket.onerror = function(event) {
		      onError(event)
		    };
		    webSocket.onopen = function(event) {
		      onOpen(event)
		    };
		    webSocket.onmessage = function(event) {
		      onMessage(event)
		    };
		    webSocket.onclose = function(event){
		    	onClose(event)
		    };
		    function onMessage(event) {
		        textarea.value += "Others : " + event.data + "\n";
		    }
		    function onOpen(event) {
		        textarea.value += "Entered!\n";
		    }
		    function onError(event) {
		      alert(event.data);
		    }
		    function onClose(event){
		    	 inputMessage.value="I'm leaving..";
		    	 document.getElementById('sendbutton').click();
		    	 
		    }
		    function send() {
		        textarea.value += "Me : " + inputMessage.value + "\n";
		        webSocket.send(inputMessage.value);
		        inputMessage.value = "";
		    }
		</script>
		  <!-- Theme JavaScript -->
    <script src="js/agency.min.js"></script>
	</body>
</html>