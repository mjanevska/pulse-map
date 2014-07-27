    

$(document).ready(function(){
	
//	window.onload = function () {
//		$('#btnAddNewGroup').click(); //Make sure the function fires as soon as the page is loaded
//    }
	  
    $('#btnFilter').click(function() {

    		var path = '/services/api/traveltransportcheckintime/19/6/20/6';
    		var dbvenues = [];
//    		for(var j=0; j<path.length; j++){
    			// var urlcategory = path[j];
    			// var venues = [];
    			// var venuesIDS = [];
    			$.ajax({ type: "GET",   
    		        url: path,   
    		        async: false,
    		        success : function(json) {
    					if(json){
    						console.log(json);
    					}
    				}	
    			});
//    		}
//    		console.log(dbvenues);
//    		getVenues(path, dbvenues, from, to);
	   
    });
    
 });