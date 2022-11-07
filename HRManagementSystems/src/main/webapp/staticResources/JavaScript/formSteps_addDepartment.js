
function setContent() {
	let modalBdy = document.getElementById('myModal');
	let myModal = document.getElementById('modalBody');

	$("#modalBody").html('');
	let errormodal = `<span id="errorText" style="align-items: center;">  All Fields are Mandatory </span>  
                    <span style="float: right; background-color: red; padding: 2px ; border: 1px solid; color: white" onclick="document.getElementById('myModal').style.display='none';
                         ">Close</span>            `;
	myModal.insertAdjacentHTML("afterbegin", errormodal);

	modalBdy.style.display = 'block'


}
function checkFirstForm() {

	var deptName = document.forms["deptform"]["deptName"].value;
	var deptDesc = document.forms["deptform"]["deptDesc"].value;
	var managerId = document.forms["deptform"]["managerId"].value;
	var totalEmployee = document.forms["deptform"]["totalEmployee"].value;
	var maxEmployee = document.forms["deptform"]["maxEmployee"].value;
	var minEmployee = document.forms["deptform"]["minEmployee"].value;
	var workDesc = document.forms["deptform"]["workDesc"].value;

	let error = (deptName == '' || deptName == null) ? true : (deptDesc == '' || deptDesc == null) ? true : (managerId == '' || managerId == null) ? true : (totalEmployee == '' || totalEmployee == null) ? true : (maxEmployee == '' || maxEmployee == null) ? true : (minEmployee == '' || minEmployee == null) ? true : (workDesc == '' || workDesc == null) ? true :false;

	return error;




}




$(function() {

	var current_fs, next_fs, previous_fs; //fieldsets
	var opacity;

	$(".next").click(function() {

		isError = checkFirstForm();
		if (isError) {
			setContent();
			return;
		}



		current_fs = $(this).parent();
		next_fs = $(this).parent().next();

		//Add Class Active
		$("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");

		//show the next fieldset
		next_fs.show();
		//hide the current fieldset with style
		current_fs.animate({ opacity: 0 }, {
			step: function(now) {
				// for making fielset appear animation
				opacity = 1 - now;

				current_fs.css({
					'display': 'none',
					'position': 'relative'
				});
				next_fs.css({ 'opacity': opacity });
			},
			duration: 600
		});
	});

	$(".previous").click(function() {

		current_fs = $(this).parent();
		previous_fs = $(this).parent().prev();

		//Remove class active
		$("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");

		//show the previous fieldset
		previous_fs.show();

		//hide the current fieldset with style
		current_fs.animate({ opacity: 0 }, {
			step: function(now) {
				// for making fielset appear animation
				opacity = 1 - now;

				current_fs.css({
					'display': 'none',
					'position': 'relative'
				});
				previous_fs.css({ 'opacity': opacity });
			},
			duration: 600
		});
	});

	$('.radio-group .radio').click(function() {
		$(this).parent().find('.radio').removeClass('selected');
		$(this).addClass('selected');
	});

	$(".submit").click(function() {
		return false;
	})

});