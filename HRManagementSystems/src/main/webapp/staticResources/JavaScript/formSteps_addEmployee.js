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
function checkFirstForm(){

    let personTitle = document.forms["msform"]["personTitle"].value;
	let firstName = document.forms["msform"]["firstName"].value;
	let middleName = document.forms["msform"]["middleName"].value;
	let lastName = document.forms["msform"]["lastName"].value;
	let gender = document.forms["msform"]["gender"].value;
	let allowedLeaves = document.forms["msform"]["allowedLeaves"].value;	
	let email1 = document.forms["msform"]["email1"].value;
	let phone1 = document.forms["msform"]["phone1"].value;
	let address = document.forms["msform"]["address"].value;
	let cityData = document.forms["msform"]["cityData"].value;
	let stateId = document.forms["msform"]["stateId"].value;
	let countryId = document.forms["msform"]["countryId"].value;
	let pincode = document.forms["msform"]["pincode"].value;

   error=(firstName == '' || firstName == null )?true:(lastName == '' || lastName == null )?true:(gender == '' || gender== null)?true:(allowedLeaves == '' || allowedLeaves == null)?true:(email1 == '' || email1 == null)?true:(address == '' || address == null)?true:(cityData == '' || cityData == null )?true:(countryId == '' || countryId == null )?true:(pincode == '' || pincode == null )?true:false;
 
 return error; 
}


function checkSecondForm(){
 var employeeCode = document.forms["msform"]["employeeCode"].value;
	var employeeTypeId = document.forms["msform"]["employeeTypeId"].value;
	var employeeStatus = document.forms["msform"]["employeeStatus"].value;
	var experience = document.forms["msform"]["experience"].value;
	var logonId = document.forms["msform"]["logonId"].value;
	var logonPassword = document.forms["msform"]["logonPassword"].value;
	var probationPeriod = document.forms["msform"]["probationPeriod"].value;	
	var manager = document.forms["msform"]["manager"].value;
	var workLocationId = document.forms["msform"]["workLocationId"].value;
	var designation = document.forms["msform"]["designation"].value;
	var jobTitleId = document.forms["msform"]["jobTitleId"].value;
	var departmentId = document.forms["msform"]["departmentId"].value;
	var roles = document.forms["msform"]["roles"].value;
	
	let error=(employeeCode == '' || employeeCode == null )?true:(employeeTypeId == '' || employeeTypeId == null )?true:(employeeStatus == '' || employeeStatus== null)?true:(experience == '' || experience == null)?true:(logonId == '' || logonId == null)?true:(logonPassword == '' || logonPassword == null)?true:(probationPeriod == '' || probationPeriod == null )?true:(manager == '' || manager == null )?true:(workLocationId == '' || workLocationId == null || workLocationId == 0  )?true:(employeeCode == '' || employeeCode == null )?true:(employeeCode == '' || employeeCode == null )?true:(departmentId == '' || departmentId == null )?true:(roles == '' || roles == null || roles == 0)?true: false;
	return error;

}


function checkFourthForm(){

	var ctc = document.forms["msform"]["ctc"].value;
	var holderName = document.forms["msform"]["holderName"].value;
	var bankName = document.forms["msform"]["bankName"].value;
	var city = document.forms["msform"]["city"].value;
	var branchName = document.forms["msform"]["branchName"].value;
	var ifscCode = document.forms["msform"]["ifscCode"].value;
	var accountNumber = document.forms["msform"]["accountNumber"].value;
	
	
	let error =(ctc == '' || ctc == null )?true:(holderName == '' || holderName == null)?true:(bankName == '' || bankName == null)?true:(ifscCode == '' || ifscCode == null)?true:(accountNumber == '' || accountNumber == null)?true:(branchName == '' || branchName == null)?true:(city == '' || city == null)?true:false;

return error;
}

$(function(){

var current_fs, next_fs, previous_fs; //fieldsets
var opacity;

$(".next").click(function(){




if(this.getAttribute('data-form')==1)
{
isError=checkFirstForm();
if(isError){
setContent();
return;
}
}


if(this.getAttribute('data-form')==2){
isError=checkSecondForm();
if(isError){
setContent();
return
}
}

if(this.getAttribute('data-form')==4)
{
isError=checkFourthForm();
if(isError){
setContent();
return;
}
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