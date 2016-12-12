


    window.onload = function() {
		//브라우저에서 웹 지오로케이션 지원 여부 판단
		if (navigator.geolocation) { 
			var options={ enableHighAccuracy:true, timeout:1000, maximumAge:6000 };
			navigator.geolocation.getCurrentPosition(MyPosition, ErrorCall, options);
		} 
	}

	var htmllat;
    var htmllng;


	function MyPosition(position) { 
		var lat = position.coords.latitude; 
		var lng = position.coords.longitude;
		htmllat=lat;
		htmllng=lng;
		alert("위도 : " + lat + " 경도 : " + lng ); 
	}


	//에러 발생 처리
	function ErrorCall(error) { 
		switch(error.code) { 
			case error.TIMEOUT: 
				alert("시간 제한을 초과했습니다."); break; 
			case error.POSITION_UNAVAILABLE: 
				alert("현재 위치를 구할 수 없습니다."); break; 
			case error.PERMISSION_DENIED: 
				alert("위치를 구할 수 있는 권한이 없습니다."); break; 
			case error.UNKNOWN_ERROR: alert("알 수 없는 에러가 발생하였습니다."); break; 
			default : alert (error.message); 
		} 
	}