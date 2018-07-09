//document.querySelector("#save").addEventListener("click", function() {
////  var formData = new FormData(document.querySelector("#countryForm"));
////  var encData = new URLSearchParams(formData.entries());
//
//	
	
	document.querySelector("#save").addEventListener("click", function() {

		var formData = new FormData(document.querySelector("#countryForm"));
		var encData = new URLSearchParams(formData.entries());
		formser= $("#countryForm").serialize() ;

	       fetch('/restservices/countries?'+encData, {
               body: encData,
               method: 'POST',
               headers: {
                   'Authorization': 'Bearer ' + sessionStorage.getItem('sessionToken'),
               }
           })		.then(response => response.json())
		.then(function(myJson) { console.log(myJson); 
		window.location = "/firstapp/les5.html";})
		});
	
  
//	var url = "/firstapp/restservices/countries";
//	var method = "POST";
//
//
//	
//	event.preventDefault();
//	
//	var formData = JSON.stringify($("#countryForm").serializeArray());
//	$.ajax({
//		  type: method,
//		  url: url +"?"+$("#countryForm").serialize(),
//		  data: formData,
//		  success: function(){
//			  window.location = "/firstapp/les5.html";
//		  },
//		});
//});


