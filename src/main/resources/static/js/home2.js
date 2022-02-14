function navDropDown() {
	if (
		document.getElementById("dropdown").style.getPropertyValue("display") ===
		"none"
	) {
		document.getElementById("dropdown").style.flexDirection = "column";
		document.getElementById("dropdown").style.display = "block";
	} else {
		document.getElementById("dropdown").style.display = "none";
	}
}

function resize() {
	var w = window.innerWidth;
	if (w >= 640) {
		document.getElementById("dropdown").style.display = "flex";
		document.getElementById("dropdown").style.flexDirection = "row";
	} else {
		document.getElementById("dropdown").style.display = "none";
	}
}

var form = document.getElementById("form123");


// document.getElementById("searchByRegionButton").value;
function sendForm(region) {
	loading();
	document.getElementById("regionhidden").value = region;
	form.method = "post";
	form.submit();
}

var geolocation = (function () {
	"use strict";

	var geoposition;
	var options = {
		maximumAge: 1000,
		timeout: 15000000,
		enableHighAccuracy: true,
	};

	function _onError(callback, error) {
		alert(
			"to access this service your need to turn on location service, please turn on and retry"
		);
		windows.history.back();
		callback();
	}

	function _onSuccess(callback, position) {
		document.getElementById("xhidden").value = position.coords.latitude;
		document.getElementById("yhidden").value = position.coords.longitude;
		geoposition = position;
		loading();
		callback();
	}

	function _getLocation(callback) {
		navigator.geolocation.getCurrentPosition(
			_onSuccess.bind(this, callback),
			_onError.bind(this, callback),
			options
		);
	}

	return {
		location: _getLocation,
	};
})();

var locationButton = document.getElementById("byLocation");
if(locationButton !== null){
	locationButton.addEventListener("click", searchByLocation);
}

function searchByLocation() {
	form.action = "/location";
	geolocation.location(() => form.submit());
}

var locationButtons = document.getElementsByName("byLocationFromWL");
for(var i = 0; i < locationButtons.length ;i++)
{
	if(locationButtons[i] !== null){
		locationButtons[i].addEventListener("click", searchByLocationWL);
	}
}

function searchByLocationWL() {
	geolocation.location(() => form.submit());
}


// adding map view
var forms = document.getElementsByClassName("medicines");

function loading(){
	document.getElementById("loading").style ="display: flex"
}
var map;


function showMap(element)
{
	document.getElementById("map").style = "display: block; z-index:100000000000000000000000000000000000000000 !important;";
	document.getElementById("close-map").style = "display: block; z-index:100000000000000000000000000000000000000000 !important;";

	var pharmacyId = document.getElementById(element.id).id;
	console.log(user_lon);
	console.log(user_lat);
	var routable = routables[pharmacyId+""];

	var pharmacy_name = routable.name;
	var lat = routable.coordsLat;
	var lon = routable.coordsLon;
	var route = JSON.parse(routable.route);
	console.log(route);
	if(map != undefined){
		map.remove();
	}
	map = L.map("map").setView(
		[parseFloat(user_lon), parseFloat(user_lat)],
		25
	);

	L.geoJSON(route).addTo(map);
	L.marker([parseFloat(lat), parseFloat(lon)]).addTo(map).bindPopup(pharmacy_name).openPopup().addTo(map);
	L.marker([parseFloat(user_lat), parseFloat(user_lon)]).addTo(map).bindPopup("Your Location").openPopup().addTo(map);
	
	L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
		attribution:
			'&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
	}).addTo(map);
		

}
function closeMap(element){
	document.getElementById("map").style = "display: none;";
	document.getElementById("close-map").style = "display: none;";
}

