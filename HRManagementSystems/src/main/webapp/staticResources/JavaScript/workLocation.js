var selDiv = "";

document.addEventListener("DOMContentLoaded", init, false);


function setContent(){
let modalBdy=   document.getElementById('myModal'); 
let myModal= document.getElementById('modalBody');

$("#modalBody").html('');
let errormodal=`<span id="errorText" style="align-items: center;">  All  <span style="color:red"> *  </span>Fields are Mandatory </span>  
                    <span style="float: right; background-color: red; padding: 2px ; border: 1px solid; color: white" onclick="document.getElementById('myModal').style.display='none';
                         ">Close</span>       `;
                         
myModal.insertAdjacentHTML("afterbegin",errormodal);

modalBdy.style.display='block'


}





function init() {
	/*
	 * document.querySelector('#files').addEventListener('change',
	 * handleFileSelect, false);
	 */
	selDiv = document.querySelector("#selectedFiles");
}

function handleFileSelect(e) {

	if (!e.target.files)
		return;

	selDiv.innerHTML = "";

	var files = e.target.files;
	for (var i = 0; i < files.length; i++) {
		var f = files[i];

		selDiv.innerHTML += f.name + "<br/>";

	}

}

function addWorkLocation() {
	var workLocationId = document.forms["workLocform"]["workLocationId"].value;
	var addressTitle = document.forms["workLocform"]["addressTitle"].value;
	var addressType = document.forms["workLocform"]["addressType"].value;
	var address1 = document.forms["workLocform"]["address1"].value;
	var address2 = document.forms["workLocform"]["address2"].value;
	var address3 = document.forms["workLocform"]["address3"].value;
	var pincode = document.forms["workLocform"]["pincode"].value;
	var country = document.forms["workLocform"]["country"].value;
	var state = document.forms["workLocform"]["state"].value;
	var city = document.forms["workLocform"]["city"].value;


let error = (addressTitle == '' || addressTitle == null) ? true : (address1 == '' || address1 == null) ? true : (pincode == '' || pincode == null) ? true : (country == '' || country == null) ? true : (state == '' || state == null) ? true : (city == '' || city == null) ? true : false;
	if(error){
	setContent();
	return;
	}
	  

	var workLocation = {
		"workLocationId" : workLocationId,
		"addressTitle" : addressTitle,
		"addressType" : addressType,
		"address1" : address1,
		"address2" : address2,
		"address3" : address3,
		"pincode" : pincode,
		"country" : country,
		"state" : state,
		"city" : city

	};
	var workLocationAccessBean = JSON.stringify(workLocation);

	console.log("jsonString == " + workLocationAccessBean);
	var urlF='/admin/company/addWorkLocation';
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : urlF,
		data : workLocationAccessBean,
		dataType : 'json',
		cache : false,
		success : function(response) {
			console.log(response);
		},

		error : function(error) {
			console.log(error);
		}

	});

}
