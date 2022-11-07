

function setContent(){
let modalBdy=   document.getElementById('myModal'); 
let myModal= document.getElementById('modalBody');

$("#modalBody").html('');
let errormodal=`<span id="errorText" style="align-items: center;">  All Fields are Mandatory </span>  
                    <span style="float: right; background-color: red; padding: 2px ; border: 1px solid; color: white" onclick="document.getElementById('myModal').style.display='none';
                         ">Close</span>            `;
myModal.insertAdjacentHTML("afterbegin",errormodal);

modalBdy.style.display='block'


}










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



$(function(){

var current_fs, next_fs, previous_fs; //fieldsets
var opacity;

$(".next").click(function(){


	var employeeId = document.forms["attendanceform"]["employeeId"].value;
	var projectId = document.forms["attendanceform"]["projectId"].value;
	var managerId = document.forms["attendanceform"]["managerId"].value;
	var holidayId = document.forms["attendanceform"]["holidayId"].value;
	var date = document.forms["attendanceform"]["date"].value;
	var day = document.forms["attendanceform"]["day"].value;
	var checkIn = document.forms["attendanceform"]["checkIn"].value;
	var checkOut = document.forms["attendanceform"]["checkOut"].value;
	var status = document.forms["attendanceform"]["status"].value;
	
	
let isError=(employeeId == '' || employeeId == null)?true:(managerId == '' || managerId == null)?true:(date == '' || date == null)?true:(employeeId == '' || employeeId == null)?true:(checkIn == '' || checkIn == null)?true:(status == '' || status == null)?true:false;
	if(isError)
	{
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
current_fs.animate({opacity: 0}, {
step: function(now) {
// for making fielset appear animation
opacity = 1 - now;

current_fs.css({
'display': 'none',
'position': 'relative'
});
next_fs.css({'opacity': opacity});
},
duration: 600
});
});

$(".previous").click(function(){

current_fs = $(this).parent();
previous_fs = $(this).parent().prev();

//Remove class active
$("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");

//show the previous fieldset
previous_fs.show();

//hide the current fieldset with style
current_fs.animate({opacity: 0}, {
step: function(now) {
// for making fielset appear animation
opacity = 1 - now;

current_fs.css({
'display': 'none',
'position': 'relative'
});
previous_fs.css({'opacity': opacity});
},
duration: 600
});
});

$('.radio-group .radio').click(function(){
$(this).parent().find('.radio').removeClass('selected');
$(this).addClass('selected');
});

$(".submit").click(function(){
return false;
})

});