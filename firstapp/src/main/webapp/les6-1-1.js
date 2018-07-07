$(document).ready(function() {
	$('#input_field').on('input', function() {
		var value = $(this).val();
		localStorage.setItem('les6prac1value', value);
		
	});
});