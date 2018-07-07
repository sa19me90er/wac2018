$(document).ready(function() {
	setInterval(function() {
		writeToConsole($("#textarea").val());
	}, 5000);
	
	var last = "";
	function writeToConsole(content) {
		if (last == content)
			return;
		debugger;
		last = content;
		console.log(content)
	}
});