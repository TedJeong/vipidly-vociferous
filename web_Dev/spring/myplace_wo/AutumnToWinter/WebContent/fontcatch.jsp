<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>


    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Autumn Project</title>

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

    <!-- Theme CSS -->
    <link href="css/agency.min.css" rel="stylesheet">
    


	<style>
		@import url(https://fonts.googleapis.com/css?family=Oswald:400,300);
		@import url(https://fonts.googleapis.com/css?family=Open+Sans);
			#inputMessages{
				font-family: 'Open Sans', sans-serif;
			}
			
			.popup-box {
			   background-color: #ffffff;
			    border: 1px solid #b0b0b0;
			    bottom: 0;
			    display: none;
			    height: 415px;
			    position: fixed;
			    right: 70px;
			    width: 300px;
			    font-family: 'Open Sans', sans-serif;
			}
			.round.hollow {
			    margin: 40px 0 0;
			}
			.round.hollow a {
			    border: 2px solid #ff6701;
			    border-radius: 35px;
			    color: red;
			    color: #ff6701;
			    font-size: 23px;
			    padding: 10px 21px;
			    text-decoration: none;
			    font-family: 'Open Sans', sans-serif;
			}
			.round.hollow a:hover {
			    border: 2px solid #000;
			    border-radius: 35px;
			    color: red;
			    color: #000;
			    font-size: 23px;
			    padding: 10px 21px;
			    text-decoration: none;
			}
			.popup-box-on {
			    display: block !important;
			}
			.popup-box .popup-head {
			    background-color: #fff;
			    clear: both;
			    color: #7b7b7b;
			    display: inline-table;
			    font-size: 21px;
			    padding: 7px 10px;
			    width: 100%;
			     font-family: Oswald;
			}
			.bg_none i {
			    border: 1px solid #ff6701;
			    border-radius: 25px;
			    color: #ff6701;
			    font-size: 17px;
			    height: 33px;
			    line-height: 30px;
			    width: 33px;
			}
			.bg_none:hover i {
			    border: 1px solid #000;
			    border-radius: 25px;
			    color: #000;
			    font-size: 17px;
			    height: 33px;
			    line-height: 30px;
			    width: 33px;
			}
			.bg_none {
			    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
			    border: medium none;
			}
			.popup-box .popup-head .popup-head-right {
			    margin: 11px 7px 0;
			}
			.popup-box .popup-messages {
			}
			.popup-head-left img {
			    border: 1px solid #7b7b7b;
			    border-radius: 50%;
			    width: 44px;
			}
			.popup-messages-footer > textarea {
			    border-bottom: 1px solid #b2b2b2 !important;
			    height: 34px !important;
			    margin: 7px;
			    padding: 5px !important;
			     border: medium none;
			    width: 95% !important;
			}
			.popup-messages-footer {
			    background: #fff none repeat scroll 0 0;
			    bottom: 0;
			    position: absolute;
			    width: 100%;
			}
			.popup-messages-footer .btn-footer {
			    overflow: hidden;
			    padding: 2px 5px 10px 6px;
			    width: 100%;
			}
			.simple_round {
			    background: #d1d1d1 none repeat scroll 0 0;
			    border-radius: 50%;
			    color: #4b4b4b !important;
			    height: 21px;
			    padding: 0 0 0 1px;
			    width: 21px;
			}
			
			
			
			
			
			.popup-box .popup-messages {
			    background: #3f9684 none repeat scroll 0 0;
			    height: 275px;
			    overflow: auto;
			}
			.direct-chat-messages {
			    overflow: auto;
			    padding: 10px;
			    transform: translate(0px, 0px);
			    
			}
			.popup-messages .chat-box-single-line {
			    border-bottom: 1px solid #a4c6b5;
			    height: 12px;
			    margin: 7px 0 20px;
			    position: relative;
			    text-align: center;
			}
			.popup-messages abbr.timestamp {
			    background: #3f9684 none repeat scroll 0 0;
			    color: #fff;
			    padding: 0 11px;
			}
			
			.popup-head-right .btn-group {
			    display: inline-flex;
				margin: 0 8px 0 0;
				vertical-align: top !important;
			}
			.chat-header-button {
			    background: transparent none repeat scroll 0 0;
			    border: 1px solid #636364;
			    border-radius: 50%;
			    font-size: 14px;
			    height: 30px;
			    width: 30px;
			}
			.popup-head-right .btn-group .dropdown-menu {
			    border: medium none;
			    min-width: 122px;
				padding: 0;
			}
			.popup-head-right .btn-group .dropdown-menu li a {
			    font-size: 12px;
			    padding: 3px 10px;
				color: #303030;
			}
			
			.popup-messages abbr.timestamp {
			    background: #3f9684  none repeat scroll 0 0;
			    color: #fff;
			    padding: 0 11px;
			}
			.popup-messages .chat-box-single-line {
			    border-bottom: 1px solid #a4c6b5;
			    height: 12px;
			    margin: 7px 0 20px;
			    position: relative;
			    text-align: center;
			}
			.popup-messages .direct-chat-messages {
			    height: auto;
			}
			.popup-messages .direct-chat-text {
			    background: #dfece7 none repeat scroll 0 0;
			    border: 1px solid #dfece7;
			    border-radius: 2px;
			    color: #1f2121;
			}
			
			.popup-messages .direct-chat-timestamp {
			    color: #fff;
			    opacity: 0.6;
			}
			
			.popup-messages .direct-chat-name {
				font-size: 15px;
				font-weight: 600;
				margin: 0 0 0 49px !important;
				color: #fff;
				opacity: 0.9;
			}
			.popup-messages .direct-chat-info {
			    display: block;
			    font-size: 12px;
			    margin-bottom: 0;
			}
			.popup-messages  .big-round {
			    margin: -9px 0 0 !important;
			}
			.popup-messages  .direct-chat-img {
			    border: 1px solid #fff;
				background: #3f9684  none repeat scroll 0 0;
			    border-radius: 50%;
			    float: left;
			    height: 40px;
			    margin: -21px 0 0;
			    width: 40px;
			}
			.direct-chat-reply-name {
			    color: #fff;
			    font-size: 15px;
			    margin: 0 0 0 10px;
			    opacity: 0.9;
			}
			
			.direct-chat-img-reply-small
			{
			    border: 1px solid #fff;
			    border-radius: 50%;
			    float: left;
			    height: 20px;
			    margin: 0 8px;
			    width: 20px;
				background:#3f9684;
			}
			
			.popup-messages .direct-chat-msg {
			    margin-bottom: 10px;
			    position: relative;
			}
			
			.popup-messages .doted-border::after {
				background: transparent none repeat scroll 0 0 !important;
			    border-right: 2px dotted #fff !important;
				bottom: 0;
			    content: "";
			    left: 17px;
			    margin: 0;
			    position: absolute;
			    top: 0;
			    width: 2px;
				 display: inline;
				  z-index: -2;
			}
			
			.popup-messages .direct-chat-msg::after {
			    background: #fff none repeat scroll 0 0;
			    border-right: medium none;
			    bottom: 0;
			    content: "";
			    left: 17px;
			    margin: 0;
			    position: absolute;
			    top: 0;
			    width: 2px;
				 display: inline;
				  z-index: -2;
			}
			.direct-chat-text::after, .direct-chat-text::before {
			   
			    border-color: transparent #dfece7 transparent transparent;
			    
			}
			.direct-chat-text::after, .direct-chat-text::before {
			    -moz-border-bottom-colors: none;
			    -moz-border-left-colors: none;
			    -moz-border-right-colors: none;
			    -moz-border-top-colors: none;
			    border-color: transparent #d2d6de transparent transparent;
			    border-image: none;
			    border-style: solid;
			    border-width: medium;
			    content: " ";
			    height: 0;
			    pointer-events: none;
			    position: absolute;
			    right: 100%;
			    top: 15px;
			    width: 0;
			}
			.direct-chat-text::after {
			    border-width: 5px;
			    margin-top: -5px;
			}
			.popup-messages .direct-chat-text {
			    background: #dfece7 none repeat scroll 0 0;
			    border: 1px solid #dfece7;
			    border-radius: 2px;
			    color: #1f2121;
			}
			.direct-chat-text {
			    background: #d2d6de none repeat scroll 0 0;
			    border: 1px solid #d2d6de;
			    border-radius: 5px;
			    color: #444;
			    margin: 5px 0 0 50px;
			    padding: 5px 10px;
			    position: relative;
			}
	
	</style>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


</head>

<body id="page-top" class="index">

    <!-- Navigation -->
    <nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand page-scroll" href="#page-top">Myplace</a>
            </div>

	</div>
	</nav>

    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>


	 <script type="text/javascript">
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
		    function chkEnter(){
	        	if(event.which || event.keyCode){
	        		if((event.which == 13) || (event.keyCode==13)){
	        			document.getElementById('sendbuttons').click();
	        			return false;
	        		}
	        	}else{
	        		return true;
	        	}
	        }

	        var textarea = document.getElementById("messageWindow");
	        var webSocket = new WebSocket('ws://localhost:8181/AutumnToWinter/broadcasting');
	        var inputMessage = document.getElementById("inputMessage");
	    	function livechatstart(){
		        
		       
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
	
	    	}

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
		    	 document.getElementById('sendbuttons').click();
		    	 
		    }
		    function send() {
		        textarea.value += "Me : " + inputMessage.value + "\n";
		        webSocket.send(inputMessage.value);
		        inputMessage.value = "";
		    }
		</script>
    <!-- Contact Form JavaScript -->
    <script src="js/jqBootstrapValidation.js"></script>
    <script src="js/contact_me.js"></script>

    <!-- Theme JavaScript -->
    <script src="js/agency.min.js"></script>

</body>

</html>
