
var data;
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


    $("#topvenues").click(function(event){
    	var number = document.getElementById('one').value.toString() 
    	+ document.getElementById('two').value.toString() 
    	+ document.getElementById('three').value.toString();

		var num = parseInt(number);
    	if(num == 0){
    		alert("Enter valid number.");
    	}else {
			if(data){
				//console.log(catIds);
				drawMarkers(data, num);
			}else{
				alert("Select category.");
			}
		}
    });
    $("#diversity").click(function(event){
    	
		if(data){
			//console.log(catIds);
			diversityMap(data);
		}else{
			alert("Select category.");
		}
		
    });

    $("#saveFile").click(function(event){
		//var text = document.getElementById("textArea").value;
		if(data){
			var text = dataToCSV(data);
			if(text || text.length !== 0) { 
				// var e = document.getElementById("formats");
				// var choice = e.options[e.selectedIndex].value;
				event.preventDefault();
				//var BB = new Blob();
				saveAs(
					  new Blob(
						  [text || text.placeholder]
						//, {type: "text/plain;charset=UTF-8" + document.characterSet}
						, {type: "text/csv;charset=utf-8;"}
					)
					, (document.getElementById("html-filename").value || document.getElementById("html-filename").placeholder) + ".csv"
				);
			}
		}else{
			alert("Select category.");
		}
	});
});

function addtopMarkers(){
	var categories = ["residence","food","artsentertainment","collegeuniversity","nightlifespot","outdoorsrecreation","shopservice","traveltransport"];
	var catIds =[];
	for(var i=0; i<categories.length; i++){
		var cat = categories[i];
		if(document.getElementById(cat).checked){
			catIds.push(document.getElementById(cat).value);
		}
	}
	
	ll = ["41.979452,21.441423","42.004,21.38","42.009,21.4","41.997,21.4","42.009710,21.415792","42.002822,21.409784",
	"42.005,21.424719","42.001927,21.417938","41.996090,21.413732","41.983468,21.408582","42.002439,21.433817",
	"41.999122,21.425749","41.996124,21.421200","41.991850,21.421114","41.995869,21.431585","41.993381,21.427036",
	"41.997017, 21.439911","41.990829,21.435362","41.984641,21.426779","41.989389,21.453654","42.008657, 21.355539",
	"42.009112, 21.448520"];
	
	rad = ["1000","1200","900","800","500","500","500","500","500","1200",
	"500","400","300","400","300","300","500","500","800","1200","1200","1200"];
	// var allVenues = [];
	var dolz = ll.length;
	var catgol = catIds.length;
	var allVenues = [];
	for(var k=0; k < catIds.length; k++){

		//razlicni vo ramki na kategorija
		var venuesIDS = [];
		 var categoryVenues = []; 
		 var p = [];
		 //var kategorija = categories[k];

		for(var j=0; j < ll.length; j++){
			
			var queryurl = "https://api.foursquare.com/v2/venues/search?ll="+ll[j]+"&radius="+rad[j]+"&limit=50&intent=checkin&categoryId="+catIds[k]+"&oauth_token=SOJEBSV4JWZZ2E52MUEPOQTL4GMEZSCWTCE5PSRHK5RUUY0N&v=20140609";
			
			//var queryurl = "https://api.foursquare.com/v2/venues/search?ll="+ll[j]+"&radius="+rad[j]+"&limit=50&intent=checkin&categoryId="+catIds[k]+"&oauth_token=Q0SXDSHROLOFGFAXPKZQO1L2ZQBWNN0BKEMCWYNQQUBVGRYJ&v=20140619";
			var kategorija = categories[k];

			$.ajax({
		    	dataType: "json",
		    	//data: kategorija,
		    	url: queryurl,
		    	success: function(json) {
		    		if(json){
		    			//console.log(json);
		     			var venues = json.response.venues;
						for(var i=0; i < venues.length; i++){
						   if($.inArray(venues[i].id, venuesIDS) === -1){
							   	venuesIDS.push(venues[i].id);
							   	var arr = new Array();
							  	arr["lat"] = venues[i].location.lat;
							  	arr["lng"] = venues[i].location.lng;
							  	arr["nameVenue"] = venues[i].name;
							  	arr["venueType"] = venues[i].categories[0].name;
							  	arr["checkinsCount"] = venues[i].stats.checkinsCount;
							  	arr["usersCount"] = venues[i].stats.usersCount;
							  	arr["count"] = 0;
							    categoryVenues.push(arr);
							    //console.log(arr);
						   }
						}
					} 
					data = categoryVenues;
				}	    	
		    });
		}
		//console.log(allVenues);
	}
};


function drawMarkers(data, num){

	var map;
	var heatmap; 
	var zoom = 12;
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
	
	google.maps.event.addListener(map, 'zoom_changed', function() {
    	document.getElementById("currentZoom").innerHTML = map.getZoom();
 	});
	
 	google.maps.event.addListener(map, 'center_changed', function() {
 		var center = map.getCenter().toString();
 		center = center.substring(1, center.length-1);
		document.getElementById("currentCenter").innerHTML = center;
 	});

	var categories = ["residence","food","artsentertainment","collegeuniversity","nightlifespot","outdoorsrecreation","shopservice","traveltransport"];
	var categ =[];
	for(var j=0; j<categories.length; j++){
		var cat = categories[j];
		if(document.getElementById(cat).checked){
			categ.push(cat);
		}
	}
	
	data = topvenues(data);
	//console.log(data.length);
	var limit = data.length-1;
	for(var j=limit; j > limit-num; j--){

		var iconimg = getIcon(data[j]["venueType"],data[j]["nameVenue"]);
		marker = new google.maps.Marker({
       		position: new google.maps.LatLng(data[j]["lat"], data[j]["lng"]),
  	    		map: map,
  	    		title: data[j]["nameVenue"],
  	    		icon: iconimg
	     	});
	}
};


function topvenues(venues) {
	var res = [];
	venues.sort(function(a, b) {
        return a["checkinsCount"] - b["checkinsCount"];
   });
	venues.sort(function(a, b) {
        return a["checkinsCount"] - b["checkinsCount"];
   });
	venues.sort(function(a, b) {
        return a["checkinsCount"] - b["checkinsCount"];
   });
	venues.sort(function(a, b) {
        return a["checkinsCount"] - b["checkinsCount"];
   });

	return venues;
};


function diversityMap(data){
	console.log(data);
	var heatData = [];
	var total = 0;
	var max = 0;
	for(var i=0; i<data.length; i++){
		total +=data[i]["checkinsCount"];
	}
	console.log(total);

	for(var j=0; j<data.length; j++){
		var arr =  new Array();
		arr["lat"] = data[j]["lat"];
		arr["lng"] = data[j]["lng"];
		arr["count"] = data[j]["usersCount"]*(data[j]["checkinsCount"]/total);
		if(max < arr["count"]){
			max = arr["count"];
		}
		heatData.push(arr);
	}
	console.log(heatData);
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
	testData["data"] = heatData;
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
	var resCSV = 'venue_type,venue_name,lat,lng,totalCheckins,diverseCheckins' + '\n';
	for(var i=0; i < venuesData.length; i++){
		var name = venuesData[i]["nameVenue"];
		resCSV+= ''+venuesData[i]["venueType"] + ','+name.replace(/,/gi, "")+',' + venuesData[i]["lat"] + ',' + venuesData[i]["lng"] + ',' +venuesData[i]["checkinsCount"] + ',' +venuesData[i]["usersCount"] + '\n';
	}
	return resCSV;
} 

function onChange(){
	var one  = document.getElementById('one').value;
	if(parseInt(one)) {
		document.getElementById('two').value = 0;
		document.getElementById('two').disabled = true;
		document.getElementById('three').value = 0;
		document.getElementById('three').disabled = true;
		console.log(data);
		var t = dataToCSV(data);
		console.log(t);
	}
	else {
		document.getElementById('two').disabled = false;
		document.getElementById('three').disabled = false;
	}
};

function getIcon(venueType, nameVenue){
	var food = ["Restaurant","Food","Pizza","Bakery", "Burger", "Gastropub", "Salad"];
	var patisserie = ["Desert", "Patisserie", "Cupcakes", "Pancakes"];
	var residence = ["Home", "Residence", "Residential","Neighborhood", "Living"];
	var artsentertainment = ["Art", "Entertainment", "Movie", "Theater", "Arcade", "Aquarium", "Music", "Zoo","Stadium","Street", "Gallery", "Circus", "Hall", "Park", "Historic", "Museum", "Bowling"];
	var collegeuniversity = ["College", "University"];
	var cafe = ["Cafe", "Café", "Coffee"];
	var nightlifespot = ["Disco","Nightclub", "Dance"];
	var bar = ["Pub", "Bar", "Brewery"];
	var store = ["Market", "Store", "Supermarket", "Mall"];
	var fitness = ["Fitness", "Health", "Bodybuilding","Sport", "Gym"];
	var hotel = ["Hotel", "Motel","Building", "Reservation", "Vacation"];
	var words = venueType.split(" ");
	var name = venueType.split(" ");
	var res = "/public/images/markers/star.png";
	for(var i=0; i<food.length; i++){
		if($.inArray(food[i],words) !== -1){
			res = "/public/images/markers/fastfood.png";
			break;
		}
	}
	for(var i=0; i<patisserie.length; i++){
		if($.inArray(patisserie[i],words) !== -1){
			res = "/public/images/markers/patisserie.png";
			break;
		}
	}
	for(var i=0; i<residence.length; i++){
		if($.inArray(residence[i],words) !== -1){
			res = "/public/images/markers/home.png";
			break;
		}
	}
	for(var i=0; i<artsentertainment.length; i++){
		if($.inArray(artsentertainment[i],words) !== -1){
			res = "/public/images/markers/home.png";
			break;
		}
	}
	for(var i=0; i<cafe.length; i++){
		if(($.inArray(cafe[i],words) !== -1) || ($.inArray(cafe[i],name) !== -1)){
			res = "/public/images/markers/coffee.png";
			break;
		}
	}
	for(var i=0; i<collegeuniversity.length; i++){
		if($.inArray(collegeuniversity[i],words) !== -1){
			res = "/public/images/markers/university.png";
			break;
		}
	}
	for(var i=0; i<nightlifespot.length; i++){
		if($.inArray(nightlifespot[i],words) !== -1){
			res = "/public/images/markers/dancinghall.png";
			break;
		}
	}
	for(var i=0; i<fitness.length; i++){
		if($.inArray(fitness[i],words) !== -1){
			res = "/public/images/markers/fitness.png";
			break;
		}
	}
	for(var i=0; i<store.length; i++){
		if($.inArray(store[i],words) !== -1){
				res = "/public/images/markers/store.png";
			
		}
	}
	for(var i=0; i<hotel.length; i++){
		if($.inArray(hotel[i],words) !== -1){
				res = "/public/images/markers/hotel.png";
			
		}
	}
	if($.inArray("Zoo",words) !== -1){
			res = "/public/images/markers/zoo.png";
		
	}
	if($.inArray("Lounge",words) !== -1){
			res = "/public/images/markers/lounge.png";
		
	}
	if($.inArray("Rock",words) !== -1){
			res = "/public/images/markers/rock.png";
		
	}
	if($.inArray("BBQ",words) !== -1){
			res = "/public/images/markers/barbecue.png";
		
	}
	if($.inArray("Gas",words) !== -1){
			res = "/public/images/markers/gas.png";
		
	}
	if($.inArray("Bank",words) !== -1){
			res = "/public/images/markers/bank.png";
		
	}
	if($.inArray("Road",words) !== -1){
			res = "/public/images/markers/road.png";
		
	}
	if(($.inArray("Barbershop",words) !== -1) || ($.inArray("Salon",words) !== -1)){
			res = "/public/images/markers/salon.png";
		
	}
	if(($.inArray("Bus",words) !== -1) || ($.inArray("Traffic",words) !== -1)){
			res = "/public/images/markers/bus.png";
		
	}
	if($.inArray("Travel",words) !== -1) {
			res = "/public/images/markers/travel.png";
		
	}
	return res;
}