<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.io.*" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>My Place</title>
	<style>
	#counter {
	  background:rgba(255,0,0,0.5);
	  border-radius: 0.5em;
	  padding: 0 .5em 0 .5em;
	  font-size: 0.75em;
	}
	</style>

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

</head>
<body>
<%
	//CardDto c = request.getAttribute("selectedcard");
	

%>
	<section id="contact">
		<div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                
                    <h2 class="section-heading">Mark the place</h2>
                    <h3 class="section-subheading text-muted">share the place you recommend to friends!</h3>
                    
                	<div class="row">
                            <div class="col-md-6 text-center" >
                                <div class="form-group">
                    				<div id="map" style="width:400px;height:500px;"></div>
                    				<div id="clickLatlng"></div>
									<br/>
									<br/>
								</div>
							</div>
							<div class="col-md-6 text-center">
								<div class="form-group">
									<div id="roadview" style="width:400px;height:500px;"></div>
									<input type="text" class="form-control" placeholder="Your title *" id="title" name="title" required data-validation-required-message="Please enter your title.">
									<div id="imgboard" style="width:300"></div>
									
                                    <p class="help-block text-danger"></p>
								</div>
							</div>
              	  </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <form action="write.cdo" method="post" enctype="multipart/form-data">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" class="form-control" value="${selectedcard.username}" id="name" name="id" readonly required data-validation-required-message="Please enter your name.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                
                                <div class="form-group">
                                    <input type="text" class="form-control" value="${selectedcard.title}" id="title" name="title" readonly required data-validation-required-message="Please enter your title.">
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <textarea class="form-control" maxlength="300" id="content" readonly name="content" required data-validation-required-message="Please enter contents.">${selectedcard.content}</textarea>
                                    <p class="help-block text-danger" ></p>
                                    <span id="counter">###</span>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                                <div id="success"></div>
                                <input type="submit" class="btn btn-xl" value="Post">
                                <input type="file" class="btn btn-xl" name="u_file" value="Upload">
                                <button type="submit" class="btn btn-xl">Post</button>
                             
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
   
	
    <script type="text/javascript" src="//apis.daum.net/maps/maps3.js?
	apikey=18201af6362be26682e70d5083846da8"></script>
	
	<script type="text/javascript" src="//apis.daum.net/maps/maps3.js?apikey=18201af6362be26682e70d5083846da8
	&libraries=services,cluster,drawing"></script>
	<script type="text/javascript">
		var container = document.getElementById('map'); //������ ���� ������ DOM ���۷���
		var options = { //������ ������ �� �ʿ��� �⺻ �ɼ�
			center: new daum.maps.LatLng(37.504607, 127.024945), //������ �߽���ǥ.
			level: 3 //������ ����(Ȯ��, ��� ����)
		};
		
		var map = new daum.maps.Map(container, options); //���� ���� �� ��ü ����
		
	
	
		// �Ϲ� ������ ��ī�̺�� ���� Ÿ���� ��ȯ�� �� �ִ� ����Ÿ�� ��Ʈ���� �����մϴ�
		var mapTypeControl = new daum.maps.MapTypeControl();
	
		// ������ ��Ʈ���� �߰��ؾ� �������� ǥ�õ˴ϴ�
		// daum.maps.ControlPosition�� ��Ʈ���� ǥ�õ� ��ġ�� �����ϴµ� TOPRIGHT�� ������ ���� �ǹ��մϴ�
		map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);
	
		// ���� Ȯ�� ��Ҹ� ������ �� �ִ�  �� ��Ʈ���� �����մϴ�
		var zoomControl = new daum.maps.ZoomControl();
		map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);

	
		var roadviewContainer = document.getElementById('roadview'); //�ε�並 ǥ���� div
		var roadview = new daum.maps.Roadview(roadviewContainer); //�ε�� ��ü
		var roadviewClient = new daum.maps.RoadviewClient(); //��ǥ�κ��� �ε�� �ĳ�ID�� ������ �ε�� helper��ü
		
		var position = new daum.maps.LatLng(37.504607, 127.024945);
		
		// Ư�� ��ġ�� ��ǥ�� ����� �ε���� panoId�� �����Ͽ� �ε�並 ����.
		roadviewClient.getNearestPanoId(position, 50, function(panoId) {
		    roadview.setPanoId(panoId, position); //panoId�� �߽���ǥ�� ���� �ε�� ����
		});
		
		
		

		var usrmarker = new daum.maps.Marker({
			position: map.getCenter()
		});
		usrmarker.setMap(map)
		
		daum.maps.event.addListener(map, 'click', function(mouseEvent){
			var latlng = mouseEvent.latLng;
			usrmarker.setPosition(latlng);
			var message = 'Ŭ���� ��ġ�� ������ '+ latlng.getLat()+'�̰�, ';
			message += '�浵�� '+latlng.getLng()+' �Դϴ�.';
			var resultDiv = document.getElementById('clickLatlng')
			resultDiv.innerHTML = message;
		});
		
		
		daum.maps.event.addListener(usrmarker, 'dragend', function(mouseEvent){
			//var latlng = mouseEvent.latLng;
			//usrmarker.setPosition(latlng);
			var latlng = usrmarker.getPosition();
			var message = 'Ŭ���� ��ġ�� ������ '+ latlng.getLat()+'�̰�, ';
			message += '�浵�� '+latlng.getLng()+' �Դϴ�.';
			var resultDiv = document.getElementById('clickLatlng')
			resultDiv.innerHTML = message;
		});
		
		usrmarker.setDraggable(true)
	</script>
		
    <script type="text/javascript">
    /*
		$(document).ready(function() {
			
	    $('#content').on('keyup', function() {
	
	        if($(this).val().length > 300) {
	            $(this).val($(this).val().substring(0, 300));
	        }
	
	    });
	
	});
		*/
		
		
		function make(){
			var img = document.createElment('img');
			document.getElementById('imgboard').appendChild(img);
			img.onclick = function(){viewImage(img)};
		}
		
		
		// Image detail views
		function doImgPop(img){
			img1 = new Image();
			img1.src=(img)
			imgControll(img);
		}
		function imgControl(img){
			if((img1.width!=0)&&(img.height!=0)){
				viewImage(img);
			}else{
				controller="imgControll('"+img+"')"
				intervalD=setTimeout(controller,20);
			}
		}
		
		function viewImage(img){
			W = img1.width;
			H = img1.height;
			O = "width="+W+",height="+H+",scrollbars=yes";
			imgWin=window.open("","",O);
			imgWin.document.write("<html><head><title></title></head>")
			imgWin.document.write("<body topmargin=0 leftmargin=0>");
			imgWin.document.write("<img src="+img+" onclick='self.close()' style='cursor:pointer;' title ='Ŭ���Ͻø� â�� �����ϴ�.'>");
			imgWin.document.write("</html>")
			imgWin.document.close();
		}
		
		$(function() {
		      $('#content').keyup(function (e){
		          var content = $(this).val();
		          $(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
		          $('#counter').html(content.length + '/300');
		      });
		      $('#content').keyup();
		});
	</script>

    <!-- jQuery -->
    <script src="vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>

    <!-- Contact Form JavaScript -->
    <script src="js/jqBootstrapValidation.js"></script>
    <script src="js/contact_me.js"></script>

    <!-- Theme JavaScript -->
    <script src="js/agency.min.js"></script>
    
    
</body>

</body>
</html>

