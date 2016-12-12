    <input type="button" onclick="PositionStart()" value="위치 추적 시작">
	<input type="button" onclick="PositionStop()" value="위치 추적 정지"><br>
	<div id="position"></div>



	var watchId;
	function PositionStart() {
		watchId = navigator.geolocation.watchPosition(MyPosition);
	}
	function MyPosition(position) {
		var lat = position.coords.latitude;
		var lng = position.coords.longitude;
		document.getElementById("position").innerHTML = "현재 위치 (위도 : " + position.coords.latitude + ", 경도 : " + position.coords.longitude + ")";
	}
	function PositionStop() {
		navigator.geolocation.clearPosition(watchId);
	}
