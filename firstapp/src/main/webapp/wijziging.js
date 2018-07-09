$(document).ready(function (){
	function loadData(){
		var code = localStorage.getItem('id')
		console.log(code);
		$.ajax({
			  type: "get",
			  url: "/restservices/countries/"+code,

			  success: function(result){
				  	$("#code").val(result.code);
					$("#iso3").val(result.iso3);
					$("#name").val(result.name);
					$("#capital").val(result.capital);
					$("#continent").val(result.continent);
					$("#region").val(result.region);
					$("#goverment").val(result.goverment);
					$("#population").val(result.population);
					$("#lat").val(result.lat);
					$("#longitude").val(result.lng);
					$("#surface").val(result.surface);
			  },
			  error: function(){
				  $("#codeField").removeClass("hidden");
			  }
			});
		
	}
	
	loadData();
	
	
/*	
	document.querySelector("#save").addEventListener("click", function () {
		  var id = document.querySelector("#put_id").value;
		  var formData = new FormData(document.querySelector("#countryForm"));
		  var encData = new URLSearchParams(formData.entries());

		  fetch("/firstapp/restservices/countries", { method: 'PUT', body: encData })
		    .then(response => response.json())
		    .then(function(myJson) { console.log(myJson); })
		});
*/
	
	
	document.querySelector("#save").addEventListener("click", function() {
//		var url = "/firstapp/restservices/countries";
//		var method = "PUT";

		var formData = new FormData(document.querySelector("#countryForm"));
		var encData = new URLSearchParams(formData.entries());

	       fetch('restservices/countries?'+encData, {
               body: encData,
               method: 'PUT',
               headers: {
                   'Authorization': 'Bearer ' + sessionStorage.getItem('sessionToken'),
               }
           })		.then(response => response.json())
		.then(function(myJson) { console.log(myJson); })
		});

//
//	    $.ajax( { 
//	    	type: method,
//			url: url +"?"+$("#countryForm").serialize(),
//	        data: $("#countryForm").serialize(), 
//	        beforeSend: function (xhr) {
//	            var token = window.sessionStorage.getItem("sessionToken");
//	            xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
//	        },
//
//	        success: function(response) {
//	            $("#response").text("Country saved!");
//	        },
//	        error: function(response) {
//	            $("#response").text("Could not update country!");
//	        }
//	    }); 
//	});

	

});