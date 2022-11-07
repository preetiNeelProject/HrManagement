

function checkAddresForm() {

	var addressTitle = document.forms["workLocform"]["addressTitle"].value;
	var address1 = document.forms["workLocform"]["address1"].value;
	var pincode = document.forms["workLocform"]["pincode"].value;
	var country = document.forms["workLocform"]["country"].value;
	var state = document.forms["workLocform"]["state"].value;
	var city = document.forms["workLocform"]["city"].value;
	
	let error = (addressTitle == '' || addressTitle == null) ? true : (address1 == '' || address1 == null) ? true : (pincode == '' || pincode == null) ? true : (country == '' || country == null) ? true : (state == '' || state == null) ? true : (city == '' || city == null) ? true : false;
	return error;

}



$(function() {

	var current_fs, next_fs, previous_fs; //fieldsets
	var opacity;

	$(".next").click(function() {
	
	
	       if(this.getAttribute('data-form') == 1){
	          isError= checkAddresForm();
	          if(isError){
	           setContent();
	           return
	          }
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