<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body onload="initMap()">

    <div id="map" style="width:500px;height:400px" "></div>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB-3w5GHzUBnp4Ub8l4Gt08Vn51QTU3RxM&language=ko"
type="text/javascript"></script>
<script>

function initMap() {
  // Create a map object and specify the DOM element for display.
  var map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 37.5151, lng: 127.1046},
    scrollwheel: true,
    zoom: 12
  });
  
  var testLatLng = new google.maps.LatLng(37.515151,127.104444);
  var mapOptions={
		  zoom: 15,
		  center: testLatLng,
		  mapTypeId: google.maps.MapTypeId.ROADMAP
  }
  
  var marker = new google.maps.Marker({
	  position: testLatLng,
	  map: map,
	  title: "실험"
  });
  
  var infowindow = new google.maps.InfoWindow({
	  content: "<h1>실험</h1>",
	  maxWidth: 300
  });
  google.maps.event.addListener(marker,'click',function(){
	 infowindow.open(map,marker); 
  });
}
</script>

 
</body>
</html>