    
// function addCheckins(ddjson, dbcheckins){
function addCheckins(ddjson){	
	var weekday = new Array(7);
	  weekday[0]=  "Sunday";
	  weekday[1] = "Monday";
	  weekday[2] = "Tuesday";
	  weekday[3] = "Wednesday";
	  weekday[4] = "Thursday";
	  weekday[5] = "Friday";
	  weekday[6] = "Saturday";
	  
    var ven = ddjson.response.venues;
    
	for(var i=0; i < ven.length; i++){
		
	  //console.log(ven[i].id);
	  var mm = ven[i].id;
	  //console.log(mm);
	  var nn = ven[i].hereNow.count;
	  //console.log(nn);
	  
	  var currentdate = new Date();
	  //adding data for check-ins
	  var ttime = currentdate.getHours() + ":00";
	  var dday = currentdate.getDate();
	  var ddayofweek = weekday[currentdate.getDay()];
	  var mmonth = (currentdate.getMonth()+1);
	  var yyear = currentdate.getYear();
	  var m = ttime+"-"+dday+"/"+mmonth+"/"+yyear+":"+mm;
	  
	  // var inarche = $.inArray(m, dbcheckins);
	  // if(inarche === -1){
		  var postParams1 = {
				time : ttime,
				day : dday,
				dayofweek : ddayofweek,
				month : mmonth,
				year : yyear,
		  		venueID : mm,
				numC : nn
		    };
			  //console.log(venues[i].location.lat.toString());
			  $.post("services/api/nightlifespotcheckin/create", postParams1, function(data, status) {
	    			if (data) {
	    				//successfull - the venue is returned
	    				//_groups[data.id] = data;
	    				console.log("Successfully created checkins data.");
	    				
	    			} else {
	    				//show returned errors
	    				console.log(data);
	    			}
	    		});
	  	//}
	 }
}

function executeQuery(dbvenues,latlng,rad){
	
	var queryurl = "https://api.foursquare.com/v2/venues/search?ll="+latlng+"&radius="+rad+"&limit=50&intent=checkin&categoryId=4d4b7105d754a06376d81259&oauth_token=SOJEBSV4JWZZ2E52MUEPOQTL4GMEZSCWTCE5PSRHK5RUUY0N&v=20140609";
	$.ajax({
    	dataType: "json",
    	url: queryurl,
    	success: function(data) {
    		var venues = data.response.venues;
    		console.log(venues);
    		for(var i=0; i < venues.length; i++){
        		var inarr = $.inArray(venues[i].id, dbvenues);
        		if(inarr === -1){
//        			console.log(venues[i].hereNow.count);
        			//adding new venues in database
					var postParams = {
			    		latitude : venues[i].location.lat.toString(),
			    		longitude : venues[i].location.lng.toString(),
			    		idVenue : venues[i].id,
			    		nameVenue : venues[i].name,
				    };
	        		//console.log(venues[i].location.lat.toString());
	        		$.post("services/api/nightlifespotvenue/create", postParams, function(data, status) {
	        		    if (data) {
	        		    	//successfull - the venue is returned
	        		    	//_groups[data.id] = data;
	        		    	console.log("Successfully created venue.");
	        		    } else {
	        		    	//show returned errors
	        		    	console.log(data);
	        		    }
	        		});
        		} 
    		}
    	// var path = 'services/api/nightlifespotcheckin';
   		//  var dbcheckins = [];
   	 //      $.get(path, function(json) {
   	 //          //parsing to get a hash map
   	 //          if(json){
   	 //              for(var i=0; i<json.length; i++){
   	 //            	  var m = json[i].time+"-"+json[i].day+"/"+json[i].month+"/"+json[i].year+":"+json[i].venueID;
   	 //                  dbcheckins.push(m);
   	 //              }
   	 //              console.log(dbcheckins);
   	 //              addCheckins(data, dbcheckins);
   	 //          }
   	 //      });
    addCheckins(data);
    	}
    });
};
    

$(document).ready(function(){
	
	window.onload = function () {
		$('#btnAddNewGroup').click(); //Make sure the function fires as soon as the page is loaded
    }
	  
    $('#btnAddNewGroup').click(function() {
    	
		var path = 'services/api/nightlifespotvenue';
		var dbvenues = [];
		var ll = [];
		var rad = [];
		
		ll.push("41.979452,21.441423");
		ll.push("42.004,21.38");
		ll.push("42.009,21.4");
		ll.push("41.997,21.4");
		ll.push("42.009710,21.415792");
		ll.push("42.002822,21.409784");
		ll.push("42.005,21.424719");
		ll.push("42.001927,21.417938");
		ll.push("41.996090,21.413732");
		ll.push("41.983468,21.408582");
		ll.push("42.002439,21.433817");
		ll.push("41.999122,21.425749");
		ll.push("41.996124,21.421200");
		ll.push("41.991850,21.421114");
		ll.push("41.995869,21.431585");
		ll.push("41.993381,21.427036");
		ll.push("41.997017,21.439911");
		ll.push("41.990829,21.435362");
		ll.push("41.984641,21.426779");
		ll.push("41.989389,21.453654");
		ll.push("42.008657,21.355539");
		ll.push("42.009112,21.448520");
		
		rad.push("1000");
		rad.push("1200");
		rad.push("900");
		rad.push("800");
		rad.push("500");
		rad.push("500");
		rad.push("500");
		rad.push("500");
		rad.push("500");
		rad.push("1200");
		rad.push("500");
		rad.push("400");
		rad.push("300");
		rad.push("400");
		rad.push("300");
		rad.push("300");
		rad.push("500");
		rad.push("500");
		rad.push("800");
		rad.push("1200");
		rad.push("1200");
		rad.push("1200");
		
	    for(var j=0; j< ll.length; j++){
			$.ajax({    
	        	url: path,   
	        	async: false,
	        	success : function(json) {
					if(json){
			            for(var i=0; i<json.length; i++){
			                dbvenues.push(json[i].idVenue);
			                //console.log(json[i].idVenue + " " + ll[j]);
			            }
			            executeQuery(dbvenues,ll[j],rad[j]);
			        }
				}
			});
		}
    });
    
    $('#btnDeleteC').click(function() {
    	
    	for(var p=3036; p<4642; p++ ){
    	var postParams = {
                id: p
            };

            $.post("services/api/nightlifespotcheckin/delete", postParams, function(data,status){
                //TODO: if successful remove group from the table, else highlight the errors
                    //show returned errors
                    console.log(data);
                    if(data.status === "success"){
                    	console.log("dd");
                    }
            });
    	}
    });
    
$('#btnDeleteV').click(function() {
    	
    	for(var p=1056; p<3037; p++ ){
    	var postParams = {
                id: p
            };

            $.post("services/api/nightlifespotvenue/delete", postParams, function(data,status){
                //TODO: if successful remove group from the table, else highlight the errors
                    //show returned errors
                    console.log(data);
                    if(data.status === "success"){
                    	console.log("dd");
                    }
            });
    	}
    });
});