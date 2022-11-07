var selDiv = "";

document.addEventListener("DOMContentLoaded", init, false);

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

function submitForm() {
	var departmentId = document.forms["deptform"]["departmentId"].value;
	var workLocationId = document.forms["deptform"]["workLocationId"].value;
	var deptName = document.forms["deptform"]["deptName"].value;
	var deptDesc = document.forms["deptform"]["deptDesc"].value;
	var managerId = document.forms["deptform"]["managerId"].value;
	var totalEmployee = document.forms["deptform"]["totalEmployee"].value;
	var maxEmployee = document.forms["deptform"]["maxEmployee"].value;
	var minEmployee = document.forms["deptform"]["minEmployee"].value;
	var workDesc = document.forms["deptform"]["workDesc"].value;
   // var allowedLeaves= document.forms["deptform"]["totalLeaves"].value;
    
   let error = (deptName == '' || deptName == null) ? true : (deptDesc == '' || deptDesc == null) ? true : (managerId == '' || managerId == null) ? true : (totalEmployee == '' || totalEmployee == null) ? true : (maxEmployee == '' || maxEmployee == null) ? true : (minEmployee == '' || minEmployee == null) ? true : (workDesc == '' || workDesc == null) ? true :  false;
   if(error){
    return;
   }
    
	var department = {
	    "workLocationId":workLocationId,
		"departmentId" : departmentId,
		"deptName" : deptName,
		"deptDesc" : deptDesc,
		"managerId" : managerId,
		"totalEmployee" : totalEmployee,
		"maxEmployee" : maxEmployee,
		"minEmployee" : minEmployee,
		"workDesc" : workDesc,
		

	};
	var departmentAccessBean = JSON.stringify(department);
    console.log(departmentAccessBean);
	console.log("jsonString == " + departmentAccessBean);
	var urlF='';
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url :'/admin/company/addDepartment',
		data : departmentAccessBean,
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

function submitAddSubDepartmentForm() {
	var subDepartmentId = document.forms["deptform"]["subDepartmentId"].value;
	var departmentId = document.forms["deptform"]["departmentId"].value;
	var deptName = document.forms["deptform"]["deptName"].value;
	var deptDesc = document.forms["deptform"]["deptDesc"].value;
	var managerId = document.forms["deptform"]["managerId"].value;
	var totalEmployee = document.forms["deptform"]["totalEmployee"].value;
	var maxEmployee = document.forms["deptform"]["maxEmployee"].value;
	var minEmployee = document.forms["deptform"]["minEmployee"].value;
	var workDesc = document.forms["deptform"]["workDesc"].value;

 let error = (deptName == '' || deptName == null) ? true : (deptDesc == '' || deptDesc == null) ? true : (managerId == '' || managerId == null) ? true : (totalEmployee == '' || totalEmployee == null) ? true : (maxEmployee == '' || maxEmployee == null) ? true : (minEmployee == '' || minEmployee == null) ? true : (workDesc == '' || workDesc == null) ? true : false;
   if(error){
    return;
   }
	var subdepartment = {
			"subDepartmentId":subDepartmentId,
		"departmentId" : departmentId,
		"deptName" : deptName,
		"deptDesc" : deptDesc,
		"managerId" : managerId,
		"totalEmployee" : totalEmployee,
		"maxEmployee" : maxEmployee,
		"minEmployee" : minEmployee,
		"workDesc" : workDesc

	};
	var subdepartmentAccessBean = JSON.stringify(subdepartment);

	console.log("jsonString == " + subdepartmentAccessBean);
	var urlF= '/admin/company/addSubDepartment';
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : urlF,
		data : subdepartmentAccessBean,
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