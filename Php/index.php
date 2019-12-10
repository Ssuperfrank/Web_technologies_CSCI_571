<?php 
	$street = $city  = $state = $address = "";
	$map_content = $weather_content = "";
	$timezone = $temperature = $summary = $humidity = $pressure = $windSpeed = $visibility = $cloudCover = $ozone = "";

	$card_icon_url = $status_icon_url = $detail_icon_url = array();
	$daily_content = array();	// weather table
	$detail_content = array(); //details of each day

	$lat = $lon =  "";
	$geocode_key = "***";
	$sky_api = "***";

	$card_icon_url['humidity'] = 	"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-16-512.png";
	$card_icon_url['pressure'] = 	"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-25-512.png";
	$card_icon_url['windSpeed'] = 	"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png";
	$card_icon_url['visibility'] = 	"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-30-512.png";
	$card_icon_url['cloudCover'] = 	"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png";
	$card_icon_url['ozone'] = 		"https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-24-512.png";

	$status_icon_url['clear-day'] = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png";
	$status_icon_url['clear-night'] = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png";
	$status_icon_url['rain'] = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-04-512.png";
	$status_icon_url['snow'] = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-19-512.png";
	$status_icon_url['sleet'] = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-07-512.png";
	$status_icon_url['wind'] = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png";
	$status_icon_url['fog'] = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png";
	$status_icon_url['cloudy'] = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-01-512.png";
	$status_icon_url['partly-cloudy-day'] = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png";
	$status_icon_url['partly-cloudy-night:'] = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png";

	$detail_icon_url['clear-day'] = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png";
	$detail_icon_url['clear-night'] = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png";
	$detail_icon_url['rain'] = "https://cdn3.iconfinder.com/data/icons/weather-344/142/rain-512.png";
	$detail_icon_url['snow'] = "https://cdn3.iconfinder.com/data/icons/weather-344/142/snow-512.png";
	$detail_icon_url['sleet'] = "https://cdn3.iconfinder.com/data/icons/weather-344/142/lightning-512.png";
	$detail_icon_url['wind'] = "https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_10-512.png";
	$detail_icon_url['fog'] = "https://cdn3.iconfinder.com/data/icons/weather-344/142/cloudy-512.png";
	$detail_icon_url['cloudy'] = "https://cdn3.iconfinder.com/data/icons/weather-344/142/cloud-512.png";
	$detail_icon_url['partly-cloudy-day'] = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png";
	$detail_icon_url['partly-cloudy-night'] = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png";
?>
<?php if (!empty($_POST["street"]) && !empty($_POST["city"]) && !empty($_POST["state"])): ?>
<?php
	
	$street = $_POST['street'];
	$city = $_POST['city'];
	$state = $_POST['state'];

	$address = str_replace(" ","+",$street) ."," .str_replace(" ","+",$city) ."," .str_replace(" ","+",$state);

	// $address = urlencode($street).",".urlencode($city).",".urlencode($state);

	$url  = "https://maps.googleapis.com/maps/api/geocode/xml?address=" .$address ."&key=" .$geocode_key;
	$xmlcontent = file_get_contents($url);
	$map_content = json_decode(json_encode(simplexml_load_string($xmlcontent, 'SimpleXMLElement', LIBXML_NOCDATA)), true);

	if($map_content['status']!="OK"){
		exit();
	}
	
	if($map_content['status'] == "OK"){
		if(!array_key_exists('geometry', $map_content['result'])){
			exit();
		}
		//specile file format
		$lat = $map_content['result']['geometry']['location']['lat'];
		$lon = $map_content['result']['geometry']['location']['lng'];
		
		if(empty($lat) || empty($lon) || $lat==0 || $lon==0){
			exit();
		}

		$timeurl = "https://api.forecast.io/forecast/".$sky_api."/".$lat.",".$lon."?exclude=minutely,hourly,alerts,flags";
		$weather_content = file_get_contents($timeurl);
		// console.log($timeurl);
		echo $weather_content;
	
	}

?>
<?php elseif (!empty($_POST["lat"]) && !empty($_POST["lon"]) && empty($_POST["timestamp"])):?>
<?php	
	$lat = $_POST['lat'];
	$lon = $_POST['lon'];

	$timeurl = "https://api.forecast.io/forecast/".$sky_api."/".$lat.",".$lon."?exclude=minutely,hourly,alerts,flags";
	$weather_content = file_get_contents($timeurl);
	// console.log($timeurl);
	echo $weather_content;
?>
<?php elseif (!empty($_POST["lat"]) && !empty($_POST["lon"]) && !empty($_POST["timestamp"])):?>
<?php	
	$lat = $_POST['lat'];
	$lon = $_POST['lon'];
	$time = $_POST["timestamp"];

	$detail_url = "https://api.darksky.net/forecast/".$sky_api."/".$lat.",".$lon.",".$time ."?exclude=minutely";
	$detail_content = file_get_contents($detail_url);	

	echo $detail_content;
?>
<?php else:?>
<!DOCTYPE html>
<html>
<head>
<title>Weather Search</title>
<meta charset="utf-8">
<style type="text/css">
	div, h1, table{
		margin: 20px auto;
		text-align: center;
		font-family:  "Times New Roman", serif; 
	}
	input{
		font-family:  "Times New Roman", serif; 
		
	}
	div{
		position: relative;
		border-radius: 15px; 
	}
	#form_container {
		width: 800px;
		height: 280px;
		background-color: #32AB39; 
		color: white;
	}

	#form_container form{
		font-weight: 900;
		font-size: 1.5em;
	}

	#form_container ul{
		margin-top: 10px;
		margin-bottom: 10px;
	}

	#form_container h1{
		position: relative;
		top: 10px;
		font-style: italic;
		font-weight: 100;
	}

	#weather_card_box{
		background-color: #5DC4F4; 
		color: white;
		width: 500px; 
		height: 350px;		
	}

	#weather_card_box h1{
		position: absolute;
		left: 25px;
		font-size: 2em;
	}

	#weather_card_box h4{
	    position: absolute;
	    left: 25px;
	    top: 45px;
	    font-size: 15px;
	    font-weight: 100;
	}

	#weather_card_box h3{
		position: absolute;
	    left: 25px;
	    top: 90px;
	    font-size: 6em;
	    margin: 0 0;
	}

	#weather_card_box h3 img{
	    position: relative;
    	top: -65px;
	}

	#weather_card_box h3 a{
	    position: relative;
	    left: 15px;
	    font-size: 0.5em;
	}

	#weather_card_box p{
		position: absolute;
	    left: 25px;
	    top: 210px;
	    font-size: 2em;
	    margin: 0;
	    font-weight: 900;
	}

	#weather_card_box table{
		position: absolute;
		top: 250px;
		width: 500px;
		font-size: 1.5em;
    	font-weight: 900;
	}

	#weather_card_box table td{
		min-width: 78px; 
	}

	#weather_table , #weather_table tr th, #weather_table tr td{
		background-color: #9FC9EE;
		border-collapse: collapse;
		border: solid 2px #4C9CC5;
		text-decoration: none;
		color: white;
	}

	#weather_table tr th{
		height: 35px;
	}

	#weather_table tr td{
		height: 50px;
		font-weight: 900;
	}

	#weather_table a{
		text-decoration: none;
		color: white;
	}

	#weather_table td.date{
		width: 100px;
	}
	#weather_table td.sum{
		width: 340px;
	}
	#weather_table td.tempH{
		width: 145px;
	}
	#weather_table td.tempL{
		width: 145px;
	}
	#weather_table td.windS{
		width: 110px;
	}

	#daily_detail_box{
		width: 550px;
		height: 500px;
		background-color: #A6CFD8;
		color: white;
	}

	#daily_detail_box p img{
		position: relative;
		width: 250px; 
		height: 250px;
		left: 125px;
	}

	#daily_detail_box h3{
		position: absolute;
		font-size: 2.5em;
		left: 25px;
	    bottom: 350px;
    	max-width: 250px;
	}

	#daily_detail_box h4{
		position: absolute;
		font-size: 7em;
		left: 25px;
		top: 120px;
		margin: 0;
	}
	#daily_detail_box h4 img{
		position: relative;
		width: 15px; 
		height: 15px;
	    top: -70px;
	}

	#daily_detail_box h4 a{
		position: relative;
	    font-size: 0.8em;
	}

	#daily_detail_box table{
		position: absolute;
    	top: 240px;
    	left: 180px;
    	font-weight: 900;
		
		font-size: 1.3em;
	}

	#daily_detail_box table .col1{
		text-align: right;
	}

	#daily_detail_box table .col2{
		text-align: left;
	}

	#daily_detail_box table a{
		font-size: 1.4em;
	}

	#arrow{
		width: 45px;
		height: 45px;
	}

	#chart {
		width: 900px;
		height: 250px;
	}

	#chart div{
		top: -10px;
	}

</style>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	function requestIP(){
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.open("GET", "http://ip-api.com/json", false);
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState == XMLHttpRequest.DONE && xmlhttp.status == 200){
				var json = JSON.parse(xmlhttp.responseText);
				var hidden = { "lat" : json.lat, "lon" : json.lon, "city": json.city};

				document.getElementById('hiddenURL').value = JSON.stringify(hidden); 
			}
		}
		xmlhttp.send();
	}

	function errorHandle(){
		var htmltext = "<p style='border: 2px solid black; background-color: grey-white; width: 400px; margin: 20px auto; '>Please check the input address.</p>";
		document.getElementById('result_box1').innerHTML = htmltext;
		document.getElementById('result_box2').innerHTML = "";
		document.getElementById('result_box3').innerHTML = "";


	}

	function checkInput(){
		if(document.getElementById('current').checked){
			var url = document.getElementById('hiddenURL').value;
			var urljson = JSON.parse(url);
			var city = urljson.city;
			var param = "lat=" + urljson.lat + "&lon=" + urljson.lon;
			var weather = requestContent(param);
			if(!weather || weather ==""){
				return;
			}
			make_weather_card(weather, city);
		}else{
			var street = document.getElementById('street').value;
			var city = document.getElementById('city').value;
			var state = document.getElementById('state').options[document.getElementById('state').options.selectedIndex].value;
			if( street == "" || city == "" || state == "State"){
				errorHandle();
				return;
			}
			var param = "street=" + street +"&city=" + city + "&state=" + state;
			var weather = requestContent(param);
			if(!weather || weather ==""){
				return;
			}
			make_weather_card(weather, city);
		}
	}

	function boxCheck(){
		var status = document.getElementById('current').checked;
		if(status == true){
			document.getElementById('street').value = "";
			document.getElementById('city').value = "";
			document.getElementById('state').options[0].selected = true;
			// document.getElementById('street').readOnly = true;

			document.getElementById('street').disabled = true;
			document.getElementById('city').disabled = true;
			document.getElementById('state').disabled = true;
			requestIP();
		}else{
			document.getElementById('street').disabled = false;
			document.getElementById('city').disabled = false;
			document.getElementById('state').disabled = false;
		}
	}

	function clearAll(){
		document.getElementById('search_form').reset();
		document.getElementById('street').disabled = false;
		document.getElementById('city').disabled = false;
		document.getElementById('state').disabled = false;
		document.getElementById('result_box1').innerHTML = "";
		document.getElementById('result_box2').innerHTML = "";
		document.getElementById('result_box3').innerHTML = "";

	}

	function requestContent(url){
		var jsonHttp = new XMLHttpRequest();
		jsonHttp.open("POST", "index.php", false);
		jsonHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		jsonHttp.send(url);
		var content = jsonHttp.responseText;

		if(!content || content == ""){
			errorHandle();
			return;
		}
		// console.log(content);
		return  JSON.parse(content);
	}

	function make_weather_card(weather, city){
		var card_icon = <?php echo json_encode($card_icon_url); ?>;
		var status_icon = <?php echo json_encode($status_icon_url); ?>;

		var timezone = weather.timezone;
		var temperature = weather.currently.temperature;
		var summary = weather.currently.summary;
		var humidity = weather.currently.humidity;
		var pressure = weather.currently.pressure;
		var windSpeed = weather.currently.windSpeed;
		var visibility = weather.currently.visibility;
		var cloudCover = weather.currently.cloudCover;
		var ozone = weather.currently.ozone;
		
		// document.getElementById('result_box1').setAttribute('style', "")
		// document.getElementById('result_box2').setAttribute('style', "background-color: #6495ed	; width: 900px; height: 700px;")

		var weather_card = "<div id='weather_card_box'><h1>" + city + "</h1><h4>"+timezone+"</h4><h3>"+Math.round(temperature) +"<img height='15' width='15' src='https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png'><a>F</a></h3><p>" + summary + "</p>";

		weather_card += "<table><tr>";
		
		if(humidity && humidity != 0)
			weather_card+=	"<td><img height='30' width='30' title='Humidity'  src='" + card_icon['humidity'] +"'><br/>"+ humidity +"</td>";
		if(humidity && pressure != 0)
			weather_card+=	"<td><img height='30' width='30' title='Pressure'  src='" +card_icon['pressure'] +"'><br/>"+pressure+"</td>";
		if(humidity && windSpeed != 0)
			weather_card+=	"<td><img height='30' width='30' title='WindSpeed' src='" +card_icon['windSpeed'] +"'><br/>"+windSpeed+"</td>";
		if(humidity && visibility != 0)
			weather_card+=	"<td><img height='30' width='30' title='Visibility' src='" + card_icon['visibility'] +"'><br/>"+visibility+"</td>";
		if(humidity && cloudCover != 0)
			weather_card+=	"<td><img height='30' width='30' title='CloudCover'  src='" +card_icon['cloudCover'] +"'><br/>"+cloudCover+"</td>";
		if(humidity && ozone != 0)
			weather_card+=	"<td><img height='30' width='30' title='Ozone'   src='" +card_icon['ozone'] +"'><br/>"+ozone+"</td>";
		
		weather_card += "</tr></table></div>";
		
		document.getElementById('result_box1').innerHTML = weather_card;

		var weather_table = "<table id='weather_table'><tr><th>Date</th><th>Status</th><th>Summary</th><th>TemperatureHigh</th><th>TemperatureLow</th><th>Wind Speed</th></tr>";


		var daily_data = weather.daily.data;
		for(var i = 0; i < daily_data.length; i++ ){
			var timeStamp = daily_data[i].time;
			var date = new Date(timeStamp*1000);
			var dateP = date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1)+"-"+date.getDate();
			var statu = daily_data[i].icon;
			var daily_summary = daily_data[i].summary;
			var temp_high = daily_data[i].temperatureHigh;
			var temp_low = daily_data[i].temperatureLow;
			var daily_windSpeed = daily_data[i].windSpeed;

			weather_table +=  "<tr><td class='date'>" + dateP + "</td>"
			+	"<td class='status'><img width='50px' height='50px' src=\"" + status_icon[statu] + "\"></td>"
			+	"<td class='sum'><a href='#' onclick='make_detail("+weather.latitude+","+weather.longitude+","+timeStamp+");'>"+daily_summary+"</a></td>"
			+	"<td class='tempH'>" + temp_high + "</td>"
			+	"<td class='tempL'>" + temp_low + "</td>"
			+	"<td class='windS'>" + daily_windSpeed + "</td></tr>";
			
		}
		weather_table += "</table>";

		document.getElementById('result_box2').innerHTML = weather_table;
		document.getElementById('result_box3').innerHTML = "";
	}

	function processIntensity(Intensity){
		if(Intensity <= 0.001) return "N/A";
		else if(Intensity <= 0.015 && Intensity > 0.001) return "Very Light";
		else if(Intensity <= 0.05 && Intensity > 0.015) return "Light";
		else if(Intensity <= 0.1 && Intensity > 0.05) return "Moderate";
		else  return "Heavy";
	}

	function processDate(timestamp){
		var date = new Date(timestamp*1000);
		var hour = date.getHours();
		if(hour < 12) return hour;
		else return (hour-12);
	}

	function numberPrecision(num){
		return (num*100).toString().split('.')[0];
	}

	function make_detail(lat ,lon ,timestamp){
		var param = "lat=" + lat +"&lon=" + lon + "&timestamp=" + timestamp;
		var detail = requestContent(param);
		var icon_url = <?php echo json_encode($detail_icon_url);?>;

		var daily_detail = "<h1>Daily Weather Detail</h1>";
		daily_detail += "<div id='daily_detail_box'>"
		+ "<h3>"+ detail.currently.summary+"</h3>"
		+ "<h4>"+ Math.round(detail.currently.temperature)+"<img src='https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png'><a>F</a></h4>"
		+ "<p><img alt='icon' src='"+ icon_url[detail.currently.icon]+"'></p>";

		daily_detail += "<table><tr><td class='col1'>Precipitation:</td><td class='col2'><a>"+ processIntensity(detail.currently.precipIntensity)+"</a></td></tr>"
		+ "<tr><td class='col1'>Chance of Rain:</td><td class='col2'><a>"+ numberPrecision(detail.currently.precipProbability)+"</a>%</td></tr>"
		+ "<tr><td class='col1'>Wind Speed:</td><td class='col2'><a>"+ detail.currently.windSpeed+"</a>mph</td></tr>"
		+ "<tr><td class='col1'>Humidity:</td><td class='col2'><a>"+ numberPrecision(detail.currently.humidity)+"</a>%</td></tr>"
		+ "<tr><td class='col1'>Visibility:</td><td class='col2'><a>"+ detail.currently.visibility+"</a>mi</td></tr>"
		+ "<tr><td class='col1'>Sunrise / Sunset:</td><td class='col2'><a>"+ processDate(detail.daily.data[0].sunriseTime)+"</a>AM/<a>"+processDate(detail.daily.data[0].sunsetTime)+"</a>PM</td></tr></table></div>";

		var daily_chart = "<h1>Day's Hourly Weather</h1>"
		+ "<img id='arrow' src='https://www.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png' onclick='show_charts("+ JSON.stringify(detail) +");'>";

		document.getElementById('result_box1').innerHTML = daily_detail;
		document.getElementById('result_box2').innerHTML = daily_chart;
		document.getElementById('result_box3').innerHTML = "";
	}

	google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(show_charts);

	function show_charts(detail){
		var image = document.getElementById('arrow');
		if(image != null){
			if(image.src.match("down")){
				image.src = "https://www.iconfinder.com/data/icons/geosm-e-commerce/18/point-up-512.png";
				
				document.getElementById('result_box3').innerHTML = "<div id='chart'></idv>"; 

				var content  = detail.hourly.data;
				var tt = new Array();
				tt.push(['Time', 'T']);
				for (var i = 0; i < content.length ; i++) {
					var date = new Date(content[i].time*1000);
					tt.push([i, content[i].temperature]);
				}
				var data =  new google.visualization.arrayToDataTable(tt);
				var options = {
					width: '600px',
					height: '250px',
					hAxis:{
						title: 'Time'
					},
					vAxis:{
						title: 'Temperature',
						textPosition: 'none'
					},
					curveType: 'function',
					tooltip: {
						itHtml: true,
						trigger: 'focus'
					}
				}

				var chart = new google.visualization.LineChart(document.getElementById('chart'));
      			chart.draw(data,options);

			}else{
				image.src = "https://www.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png";
				document.getElementById('result_box3').innerHTML = "";
			}
		}
	}

</script>
</head>
<body>
<div id="form_container">
<h1 style="margin-bottom: 0px; font-size: 45px;">Weather Search</h1>
<form method="POST" id="search_form" action="" autocomplete ="off">
	<table  width="800" height="150" style="margin-top: 10px;" ><tbody>
		<tr style="line-height: 20px; position: absolute;">
			<td style="text-align: left; text-decoration: none; border-right:solid 5px white; width: 450px; line-height: 10px;">
			<ul>	
				<label for="street">Street </label><input type="text" name="street" id="street" size="20" style="height: 20px;" ></ul>
			<ul>
				<label style="margin-right: 16px;" for="city">City </label><input type="text" name="city" id="city" size="20" style="height: 20px;" ></ul>
			<ul>
				<label for="state">State </label><select id="state" name="state" style="width: 20em;">
											<option selected value="State">State</option>
											<option disabled>-----------------</option>
											<option value="AL">Alabama</option>
											<option value="AK">Alaska</option>
											<option value="AZ">Arizona</option>
											<option value="AR">Arkansas</option>
											<option value="CA">California</option>
											<option value="CO">Colorado</option>
											<option value="CT">Connecticut</option>
											<option value="DE">Delaware</option>
											<option value="DC">District Of Columbia</option>
											<option value="FL">Florida</option>
											<option value="GA">Georgia</option>
											<option value="HI">Hawaii</option>
											<option value="ID">Idaho</option>
											<option value="IL">Illinois</option>
											<option value="IN">Indiana</option>
											<option value="IA">Iowa</option>
											<option value="KS">Kansas</option>
											<option value="KY">Kentucky</option>
											<option value="LA">Louisiana</option>
											<option value="ME">Maine</option>
											<option value="MD">Maryland</option>
											<option value="MA">Massachusetts</option>
											<option value="MI">Michigan</option>
											<option value="MN">Minnesota</option>
											<option value="MS">Mississippi</option>
											<option value="MO">Missouri</option>
											<option value="MT">Montana</option>
											<option value="NE">Nebraska</option>
											<option value="NV">Nevada</option>
											<option value="NH">New Hampshire</option>
											<option value="NJ">New Jersey</option>
											<option value="NM">New Mexico</option>
											<option value="NY">New York</option>
											<option value="NC">North Carolina</option>
											<option value="ND">North Dakota</option>
											<option value="OH">Ohio</option>
											<option value="OK">Oklahoma</option>
											<option value="OR">Oregon</option>
											<option value="PA">Pennsylvania</option>
											<option value="RI">Rhode Island</option>
											<option value="SC">South Carolina</option>
											<option value="SD">South Dakota</option>
											<option value="TN">Tennessee</option>
											<option value="TX">Texas</option>
											<option value="UT">Utah</option>
											<option value="VT">Vermont</option>
											<option value="VA">Virginia</option>
											<option value="WA">Washington</option>
											<option value="WV">West Virginia</option>
											<option value="WI">Wisconsin</option>
											<option value="WY">Wyoming</option>
											</select></ul>
			</td>
				<td style="width: 350px; vertical-align: top;">
					<ul style="margin-top: 0px;"><input type="checkbox" name="current" id="current" onclick="boxCheck()"><label style="font-size: 20px;" for="current">Current Location</label></ul>
			</td>
		</tr>
	</tbody></table>
	<input type="button" id="search" name="search" value="search" onclick="checkInput()">
	<input style="margin-right: 75px;" type="button" id="clear" name="clear" value="clear" onclick="clearAll()">
	<input type="text" name="hiddenURL" id="hiddenURL" hidden>
</form>
</div>
<div id="result_box1">
</div>
<div id="result_box2">
</div>
<div id="result_box3">
</div>
</body>
</html>
<?php endif?>
