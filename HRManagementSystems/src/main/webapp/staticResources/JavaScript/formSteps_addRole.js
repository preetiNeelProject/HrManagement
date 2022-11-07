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


function checkFirstForm(){
    var roleName = document.forms["roleform"]["roleName"].value;
	var roleType = document.forms["roleform"]["roleType"].value;
	var roleDesc = document.forms["roleform"]["roleDesc"].value;

      let error =(roleName == '' || roleName == null)?true :(roleType == '' || roleType == null) ?true: (roleDesc == '' || roleDesc == null) ? true :false;
      return error; 
}




$(function(){

var current_fs, next_fs, previous_fs; //fieldsets
var opacity;

$(".next").click(function(){


let isError=  checkFirstForm();
if(isError){
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