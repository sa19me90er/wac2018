function initPage(result){

  var x= window.sessionStorage.getItem("sessionToken");
  console.log("User JWT"+x);

	fetch("https://ipapi.co/json/")
	.then(function (response){return response.json();})
	.then(function(myJson){
		
		$("#countrycode").append(myJson.country);
		$("#land").append(myJson.country_name);
		$("#regio").append(myJson.region);
		$("#stad").append(myJson.city);
		$("#postcode").append(myJson.postal);
		$("#latitude").append(myJson.latitude);
		$("#longitude").append(myJson.longitude);
		$("#ip").append(myJson.ip);
		
		localStorage.setItem('lat', myJson.latitude);
		localStorage.setItem('long', myJson.longitude);
		localStorage.setItem('city', myJson.city);
	
	    var lat = localStorage.getItem('lat');
	    var long = localStorage.getItem('long');
	    var city = localStorage.getItem('city');
	   
	    showWeather(myJson.latitude, myJson.longitude, myJson.city);
       
        $("#stad").click(function() {
         
            $("#h3-1").empty().append("Het weer in: " + myJson.city)
   
	});
	});


        
        
	function showWeather(latitude, longitude, city){
		
		
		if (localStorage.getItem(city) != null) {			
			
			var weather = JSON.parse(localStorage.getItem(city));		
  
	        var time = weather.time;

	        if (time!= null){
	            var date = new Date(time);
	            
	            if(date.setMinutes(date.getMinutes() + 10)> new Date ()){
	                var jsonFromCity =  JSON.parse(localStorage.getItem (city));
	                	        		
	    			function msToTime(duration) {
	    		        var milliseconds = parseInt((duration%1000)/100)
	    		            , seconds = parseInt((duration/1000)%60)
	    		            , minutes = parseInt((duration/(1000*60))%60)
	    		            , hours = parseInt((duration/(1000*60*60))%24);

	    		        hours = (hours < 10) ? "0" + hours : hours;
	    		        minutes = (minutes < 10) ? "0" + minutes : minutes;
	    		        seconds = (seconds < 10) ? "0" + seconds : seconds;

	    		        return hours + ":" + minutes + ":" + seconds + "." + milliseconds;
	    			}

	    			zonsopgang=msToTime(jsonFromCity.sys.sunrise);
	    			zonsondergang=msToTime(jsonFromCity.sys.sunset);
	    			
	    			var temp = Math.round((jsonFromCity.main.temp - 273.15)* 100) / 100;
	    			$("#temptatuur").empty().append(temp + " °C");
	    			$("#luchtvochtigheid").empty().prepend(jsonFromCity.main.humidity);
	    			$("#windsnelheid").empty().prepend(jsonFromCity.wind.speed);
	    			$("#windrichting").empty().prepend(jsonFromCity.wind.deg);
	    			$("#zonsopgang").empty().prepend(zonsopgang);
	    			$("#zonsondergang").empty().prepend(zonsondergang);
	                
	            }   
	            else refresh();
	        }
		}
        else refresh();
		function refresh() {
	fetch("https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=7aba276c0999327bea88ac89dc97703a")

	.then(function (response){return response.json();})	
	.then(function(myJson){
		

		// Tot change miliseconds to hours :
		function msToTime(duration) {
	        var milliseconds = parseInt((duration%1000)/100)
	            , seconds = parseInt((duration/1000)%60)
	            , minutes = parseInt((duration/(1000*60))%60)
	            , hours = parseInt((duration/(1000*60*60))%24);

	        hours = (hours < 10) ? "0" + hours : hours;
	        minutes = (minutes < 10) ? "0" + minutes : minutes;
	        seconds = (seconds < 10) ? "0" + seconds : seconds;

	        return hours + ":" + minutes + ":" + seconds + "." + milliseconds;
	    }

		zonsopgang=msToTime(myJson.sys.sunrise);
		zonsondergang=msToTime(myJson.sys.sunset);
		
		var temp = Math.round((myJson.main.temp - 273.15)* 100) / 100;
		$("#temptatuur").empty().append(temp + " °C");
		$("#luchtvochtigheid").empty().prepend(myJson.main.humidity);
		$("#windsnelheid").empty().prepend(myJson.wind.speed);
		$("#windrichting").empty().prepend(myJson.wind.deg);
		$("#zonsopgang").empty().prepend(zonsopgang);
		$("#zonsondergang").empty().prepend(zonsondergang);
		
		myJson.time = new Date();
        localStorage.setItem (city, JSON.stringify(myJson));
	
		}	
	
	
	)};
	}
	
	
	
	showWeather(localStorage.getItem('lat'), localStorage.getItem('long'), localStorage.getItem('city'));
	 $("#h3-1").empty().append("Het weer in: "+localStorage.getItem('city')); 

 

function loadCountries(){


	fetch("/restservices/countries/")
	.then(function (response){return response.json();})
	.then(function(myJson){
		for (const m of myJson) {
			
			 var tr = $("<tr></tr>");
             
			 tr.click(function() {

                 $("#h3-1").empty().append("Het weer in: "+ m.capital);   
                 localStorage.setItem('lat',m.lat);
                 localStorage.setItem('lon', m.lng);
                 localStorage.setItem('city', m.capital);
                 
                 showWeather(m.lat, m.lng, m.capital);
                 });
            
			$("#destination-items-body").append(tr);
			tr.append("<td class=name>"+m.name+"</td>")
			tr.append("<td class=capital>"+m.capital+"</td>")
			tr.append("<td class=region>"+m.region+"</td>")
			tr.append("<td class=surfacearea>"+m.surfacearea+"</td>")
			tr.append("<td calss=population>"+m.population+"</td>")

		    var dlt="<td><button class=remove>Delete</button></td>"
		    tr.append(dlt)
		    
		     var wijziging="<td><button class=wijzigen>Wijzegen</button></td>"
		    tr.append(wijziging)
		    
//***************** Delete **************************	
		    
		    $(tr).on("click", ".remove", function(){
		    	
		    	var fetchoptions = { method: 'DELETE', headers : { 'Authorization': 'Bearer ' + window.sessionStorage.getItem("sessionToken") }}
		    			fetch("/restservices/countries/delete/" + m.code, fetchoptions)
		    			.then(function(response) {
		    			if (response.ok) {
		    			console.log("Country deleted!");
		    			} else console.log("Could not delete country!");
		    			})
		    			.catch(error => console.log(error));
	    	});

		    	
//		    	var uri = "/firstapp/restservices/countries/delete/" + m.code;
//		        $.ajax(uri, { 
//		            type: "delete", 
//		            success: function(response) {
//		            	$("#response").text(m.name + "Country deleted!");
//		            	console.log(m.name +" Country deleted!");
//		               loadCountries();
//		            },
//		            error: function(response) {
//		            	$("#response").text("Could not delete Country!");
//		            }
//		        });

		    
			
			
			
			//***************** Put **************************
			tr2=$("<tr></tr>");
			 $(tr).on("click", ".wijzigen", function(){
				
				 var id = m.code;
		    		window.location = "wijziging.html?id="+id;
		    		localStorage.setItem('id', id);
			
			 });

		}	

	
		
		
	});
	

	
	
}	
loadCountries();





}


initPage();






