var counter = 0;
var numbers = "";
var clean = false;
$(document).ready(function() {

	var display = $("#display");

	$("#btn_min").on("click", function() {
		addOpperator("-");
	});

	$("#btn_div").on("click", function() {
		addOpperator("/");
	});

	$("#btn_prod").on("click", function() {
		addOpperator("*");
	});

	$("#btn_plus").on("click", function() {
		addOpperator("+");
	});

	$("#btn_eq").on("click", function() {
		numbers += display.html();
		display.html(eval(numbers));
		numbers = "";
		clean = true;
	});

	$("#btn_clear").on("click", function() {
		clearCalc();
	});

	$("#btn_1").on("click", function() {
		addNumber(1);
	});

	$("#btn_2").on("click", function() {
		addNumber(2);
	});

	$("#btn_3").on("click", function() {
		addNumber(3);
	});

	$("#btn_4").on("click", function() {
		addNumber(4);
	});

	$("#btn_5").on("click", function() {
		addNumber(5);
	});

	$("#btn_6").on("click", function() {
		addNumber(6);
	});

	$("#btn_7").on("click", function() {
		addNumber(7);
	});

	$("#btn_8").on("click", function() {
		addNumber(8);
	});

	$("#btn_9").on("click", function() {
		addNumber(9);
	});

	$("#btn_0").on("click", function() {
		addNumber(0);
	});
	function addOpperator(op){
		numbers += display.html();
		display.html(op);
	}
	
	function clearCalc() {
		numbers = "";
		display.html(0);
		clean = false;
	}

	function addNumber(number) {
		if (clean || display.html() == "0") {
			display.html(number);
			clean = false;
			return;
		}
		display.html((display.html() + number));
	}
});