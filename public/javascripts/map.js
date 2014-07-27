var totalVenues;

$(document).ready(function(){

	window.onload = function(){
		// var time = document.getElementById("time").value;
		// visualize(time);
		///
		var map;
		var heatmap; 
		var zoom = 12;
		document.getElementById("food").style.color="blue";
		var myLatlng = new google.maps.LatLng(41.9990903,21.4248902);
		// sorry - this demo is a beta
		// there is lots of work todo
		// but I don't have enough time for eg redrawing on dragrelease right now
		var myOptions = {
		  zoom: parseInt(zoom),
		  center: myLatlng,
		  mapTypeId: google.maps.MapTypeId.ROADMAP,
		  disableDefaultUI: false,
		  scrollwheel: true,
		  draggable: true,
		  navigationControl: true,
		  mapTypeControl: false,
		  scaleControl: true,
		  disableDoubleClickZoom: false
		};
		map = new google.maps.Map(document.getElementById("heatmapArea"), myOptions);
		///
		document.getElementById("currentZoom").innerHTML = 13;
		document.getElementById("currentCenter").innerHTML = "41.9990903,21.4248902";
		//outputUpdate();
		
 	 	google.maps.event.addListener(map, 'center_changed', function() {
 	 		var center = map.getCenter().toString();
 	 		center = center.substring(1, center.length-1);
	    	document.getElementById("currentCenter").innerHTML = center;
 	 	});
	};
	
    $("#submitCategories").click(function(event){
    	outputUpdate();
    });

    $("#submitCircles").click(function(event){
    	outputCircles();
    });

    $("#saveFile").click(function(event){

		if(totalVenues){
			var text = dataToCSV(totalVenues);
			if(text || text.length !== 0) { 

				event.preventDefault();
				//var BB = new Blob();
				saveAs(
					  new Blob(
						  [text || text.placeholder]
						, {type: "text/plain;charset=" + document.characterSet}
					)
					, (document.getElementById("html-filename").value || document.getElementById("html-filename").placeholder) + ".csv"
				);
			}
		}else{
			alert("Select category.");
		}
	});
});

function outputUpdate() {

	var from = document.getElementById("datetimefrom").value;
	var to = document.getElementById("datetimeto").value;
	var categories = ["residence","food","artsentertainment","collegeuniversity","nightlifespot","outdoorsrecreation","shopservice","traveltransport"];
	var path = [];
	if(from.length < 16 && to.length < 16){
		alert("Enter dates.");
		
	}
	else if(from > to) {
		alert("Incorrect data entered. Data from is latter than date to.");
	}
	else{
		for(var i=0; i<categories.length; i++){
			if(document.getElementById(categories[i]).checked){
				path.push("services/api/"+categories[i]+"venue");
			}
		}
		//var path = 'services/api/foodvenue';
		if(path.length > 0){

			var yfrom = from.substring(0,4);
			var mfrom = from.substring(5,7);
			var dfrom = from.substring(8,10);
			var fromhour = from.substring(11,13);

			var yto = to.substring(0,4);
			var mto = to.substring(5,7);
			var dto = to.substring(8,10);
			var tohour = to.substring(11,13);

			var newplace = yfrom+"_"+mfrom+"_"+dfrom+"_"+fromhour+"_"+yto+"_"+mto+"_"+dto+"_"+tohour;
			$("#html-filename").val('');
			$("#html-filename").attr("placeholder", newplace);
			visualize(path, from, to);
		}
	}
};


function visualize(path, from, to){

	//var path = 'services/api/nightlifespotvenue';
	var dbvenues = [];
	for(var j=0; j<path.length; j++){
		var urlcategory = path[j];
		var venues = [];
		var venuesIDS = [];
		$.ajax({ type: "GET",   
	        url: urlcategory,   
	        async: false,
	        success : function(json) {
				if(json){
					for(var i=0; i<json.length; i++){
					   if($.inArray(json[i].idVenue,venuesIDS) === -1){
					   	venuesIDS.push(json[i].idVenue);
					   	var arr = new Array();
					  	arr["idVenue"] = json[i].idVenue;
					  	arr["lat"] = json[i].latitude;
					  	arr["lng"] = json[i].longitude;
					  	arr["nameVenue"] = json[i].nameVenue;
					    venues.push(arr);
					   }
					}
				  	// console.log(venuesIDS.length);
				  	dbvenues.push(venues);
				}
			}	
		});
	}
	console.log(dbvenues);
	getVenues(path, dbvenues, from, to);
};

function getVenues(path, dbvenues, from, to){

	var totaldata=[];
	var max = 0;
	var dayofweek = document.getElementById("dayofweek").value;

	var fromdate = from.substring(0,10);
	var fromhour = parseInt(from.substring(11,13));

	var todate = to.substring(0,10);
	var tohour = parseInt(to.substring(11,13));
	
	//var yfrom = parseInt(fromdate.substring(2,4));
	var mfrom = checkValue(fromdate.substring(5,7));
	var dfrom = checkValue(fromdate.substring(8,10));



	//var yto = parseInt(todate.substring(2,4));
	var mto = checkValue(todate.substring(5,7));
	var dto = checkValue(todate.substring(8,10));	

	for(var k=0; k < dbvenues.length; k++){

		var category = path[k];
		var l = category.length;
		var index = category.lastIndexOf("/");
		var categoryName = category.substring(index+1, l-5);
		var urlpath = category.substring(0, l-5) + 'checkintime/' + dfrom + '/' + mfrom + '/' + dto + '/' + mto;
		var venues = dbvenues[k];

		$.ajax({ 
			type: "GET",   
	        url: urlpath,   
	        async: false,
	        data: {fromhour: fromhour, tohour: tohour, fromdate:fromdate, todate:todate},
	        success : function(json) {
		      //parsing to get a hash map
				if(json){
					console.log(json);

					var data = [];
					var yfrom = parseInt(fromdate.substring(2,4));
					var yto = parseInt(todate.substring(2,4));

					for(var i=0; i< venues.length; i++){

						var idVenue = venues[i]["idVenue"];
						var venueData = new Array();
						var count = 0;
						var br = 0;

						for(var j=0; j<json.length; j++){
							var t = json[j]["time"];
							t = t.substring(0,2);
							var y = json[j]["year"];
							var c = json[j]["numC"];

							var year = parseInt(y.substring(1,3));
							var time = parseInt(t);
							var	numC = parseInt(c);

							if(dayofweek.length > 0){


								if((json[j].venueID == idVenue) && (json[j].dayofweek == dayofweek)
									&& (time >= fromhour) && (year >= yfrom)
									&& (time <= tohour) && (year <= yto)){
							      		if(numC > max) {
							      			max = numC;
							      		}
							      		count+=numC;
							      		br ++;
					      		}
							}
							else {
						      	if((json[j].venueID == idVenue)
									&& (time >= fromhour) && (year >= yfrom)
									&& (time <= tohour) && (year <= yto)){
			
							      		if(numC > max) {
							      			max = numC;
							      		}
							      		count+=numC;
							      		br ++;
						      	}
						    }
							
					  	}
					  	if(br != 0) {
					  		venueData["lat"] = parseFloat(venues[i]["lat"]);
					  		venueData["lng"] = parseFloat(venues[i]["lng"]);
					  		venueData["count"] = parseFloat(count/br);
					  		venueData["total"]	= parseFloat(count);
					  		venueData["cat"] = categoryName;
					  		venueData["nameVenue"] = venues[i]["nameVenue"];
					  				
					  	}
					  	data.push(venueData);
					}
					totaldata=totaldata.concat(data);
				}
				totalVenues = totaldata;
			}
		});
	}
	//console.log(totaldata);
	totalVenues = totaldata;
	drawMap(totaldata, max, dbvenues);
	document.getElementById("saveFile").disabled = false;
	document.getElementById("submitCircles").disabled = false;

};

function drawMap(totaldata, max, dbvenues) { 

	var map;
	var heatmap; 
	//console.log(data);
	var zoom = document.getElementById("currentZoom").innerHTML;
	var center = document.getElementById("currentCenter").innerHTML;
	var ll = center.split(",");
	var myLatlng = new google.maps.LatLng(parseFloat(ll[0]), parseFloat(ll[1]));

	var myOptions = {
	  zoom: parseInt(zoom),
	  center: myLatlng,
	  mapTypeId: google.maps.MapTypeId.ROADMAP,
	  disableDefaultUI: false,
	  scrollwheel: true,
	  draggable: true,
	  navigationControl: true,
	  mapTypeControl: false,
	  scaleControl: true,
	  disableDoubleClickZoom: false
	};
	map = new google.maps.Map(document.getElementById("heatmapArea"), myOptions);
	
	heatmap = new HeatmapOverlay(map, {"radius":60, "visible":true, "opacity":80, "legend": {
            position: 'br',
            title: 'Example Distribution'
        } });


	var testData = new Array();
	testData["max"] = max;
	testData["data"] = totaldata;
	console.log(testData);
	// this is important, because if you set the data set too early, the latlng/pixel projection doesn't work
	google.maps.event.addListenerOnce(map, "idle", function(){
		heatmap.setDataSet(testData);
	});
	var center = new google.maps.LatLng(parseFloat(ll[0]), parseFloat(ll[1])-0.0000001);
	map.panTo(center);

	google.maps.event.addListener(map, 'zoom_changed', function() {
    	document.getElementById("currentZoom").innerHTML = map.getZoom();
 	});
 	google.maps.event.addListener(map, 'center_changed', function() {
 		var center = map.getCenter().toString();
 		center = center.substring(1, center.length-1);
		document.getElementById("currentCenter").innerHTML = center;
 	});


};


function dataToCSV(venuesData){
	var resCSV = 'venue_category,venue_name,lat,lng,numCheckins' + '\n';
	for(var i=0; i < venuesData.length; i++){
		resCSV+= ''+venuesData[i]["cat"] + ','+venuesData[i]["nameVenue"]+ ',' + venuesData[i]["lat"] + ',' +venuesData[i]["lng"] + ','+ venuesData[i]["count"] + '\n';
	}
	return resCSV;
};


function checkValue(strNum){
	var n = parseInt(strNum);
	if(n< 10){
		return strNum.substring(1,2);
	}
	else{
		return strNum;
	}
}; 

function outputCircles(){
	if(totalVenues){

		var map;
		var heatmap; 
		//console.log(data);
		var zoom = document.getElementById("currentZoom").innerHTML;
		var center = document.getElementById("currentCenter").innerHTML;
		var ll = center.split(",");
		var myLatlng = new google.maps.LatLng(parseFloat(ll[0]), parseFloat(ll[1]));

		var myOptions = {
		  zoom: parseInt(zoom),
		  center: myLatlng,
		  mapTypeId: google.maps.MapTypeId.ROADMAP,
		  disableDefaultUI: false,
		  scrollwheel: true,
		  draggable: true,
		  navigationControl: true,
		  mapTypeControl: false,
		  scaleControl: true,
		  disableDoubleClickZoom: false
		};
		map = new google.maps.Map(document.getElementById("heatmapArea"), myOptions);

		for(var i=0; i < totalVenues.length; i++){

			var color ;
			switch(totalVenues[i]["cat"]){
				case "residence": 
					color = "#70DBE0";
					break;
				case "food": 
					color = "#00E13C";
					break;
				case "collegeuniversity": 
					color = "#A349A4";
					break;
				case "nightlifespotvenue": 
					color = "#FF9900";
					break;
				case "outdoorsrecreation": 
					color = "#E14F9E";
					break;
				case "artsentertainment": 
					color = "#5781FC";
					break;
				case "shopservice": 
					color = "#FCF357";
					break;
				case "traveltransport": 
					color = "#FF6859";
					break;
				default:
					color = "#33D685";							
			}

			var myLL = new google.maps.LatLng(totalVenues[i]["lat"], totalVenues[i]["lng"]);

			var diversityOptions = {
		      strokeColor: color,
		      strokeOpacity: 0.8,
		      strokeWeight: 2,
		      fillColor: color,
		      fillOpacity: 0.35,
		      map: map,
		      center: myLL,
		      radius: Math.sqrt(totalVenues[i]["count"]) * 100
		    };
		    // Add the circle for this city to the map.
		    cityCircle = new google.maps.Circle(diversityOptions);
		}
	}
}