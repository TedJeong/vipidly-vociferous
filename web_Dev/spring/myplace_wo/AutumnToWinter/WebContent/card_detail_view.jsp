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
		var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
		var options = { //지도를 생성할 때 필요한 기본 옵션
			center: new daum.maps.LatLng(37.504607, 127.024945), //지도의 중심좌표.
			level: 3 //지도의 레벨(확대, 축소 정도)
		};
		
		var map = new daum.maps.Map(container, options); //지도 생성 및 객체 리턴
		
	
	
		// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
		var mapTypeControl = new daum.maps.MapTypeControl();
	
		// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
		// daum.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
		map.addControl(mapTypeControl, daum.maps.ControlPosition.TOPRIGHT);
	
		// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
		var zoomControl = new daum.maps.ZoomControl();
		map.addControl(zoomControl, daum.maps.ControlPosition.RIGHT);

	
		var roadviewContainer = document.getElementById('roadview'); //로드뷰를 표시할 div
		var roadview = new daum.maps.Roadview(roadviewContainer); //로드뷰 객체
		var roadviewClient = new daum.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
		
		var position = new daum.maps.LatLng(37.504607, 127.024945);
		
		// 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
		roadviewClient.getNearestPanoId(position, 50, function(panoId) {
		    roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
		});
		
		
		

		var usrmarker = new daum.maps.Marker({
			position: map.getCenter()
		});
		usrmarker.setMap(map)
		
		daum.maps.event.addListener(map, 'click', function(mouseEvent){
			var latlng = mouseEvent.latLng;
			usrmarker.setPosition(latlng);
			var message = '클릭한 위치의 위도는 '+ latlng.getLat()+'이고, ';
			message += '경도는 '+latlng.getLng()+' 입니다.';
			var resultDiv = document.getElementById('clickLatlng')
			resultDiv.innerHTML = message;
		});
		
		
		daum.maps.event.addListener(usrmarker, 'dragend', function(mouseEvent){
			//var latlng = mouseEvent.latLng;
			//usrmarker.setPosition(latlng);
			var latlng = usrmarker.getPosition();
			var message = '클릭한 위치의 위도는 '+ latlng.getLat()+'이고, ';
			message += '경도는 '+latlng.getLng()+' 입니다.';
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
			imgWin.document.write("<img src="+img+" onclick='self.close()' style='cursor:pointer;' title ='클릭하시면 창이 닫힙니다.'>");
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

