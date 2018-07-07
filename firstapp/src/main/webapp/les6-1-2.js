$(document).ready(function() {
	var element = $("#value");

	function addHandler(ele,trigger,handler){
        if(window.addEventListener){
            ele.addEventListener(trigger,handler,false);
            return false;
        }
        window.attachEvent(trigger,handler);
    }

    function onStoargeEvent(e) {
        var value = localStorage.getItem('les6prac1value');
        element.html(value);
    }
    var value = localStorage.getItem('les6prac1value');
    element.html(value);
    addHandler(window,"storage",onStoargeEvent);
});