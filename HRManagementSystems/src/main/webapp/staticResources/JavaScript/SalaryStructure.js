




function createEmpSalaryStructure() {
	var employeeId = $("#employeeId").val();
	var ctc = $("#cTC").val();
	var host = window.location.host;

  var	urlF = '/calBasicSalary'

	if (ctc.length > 3) {
		$.ajax({
			type: "POST",
			dataType: 'text',
			url: urlF,
			data: {
				ctc,
				employeeId
			},
			dataType: 'json',
			cache: false,
			success: function(response) {
				console.log("success : " + response.basicSalaryMonthly);
				$('#annualBasicSalary').val(response.basicSalaryMonthly);
				$('#pf').val(response.pfMonthly);
				$('#hra').val(response.hraMonthly);
				$('#convey').val(response.conveyanceMonthly);
				$('#annualFlexibleBenifits').val(response.otherMonthly);
				$('#annualRefSalary').val(response.annualRefSalary);
				$('#esi').val(response.esiMonthly);
				// document.getElementById("myBtn").disabled = false;
			},

			error: function(error) {
				console.log("basicSalaryMonthly " + JSON.stringify(error.basicSalaryMonthly));
				console.log("error : " + JSON.stringify(error));
			}

		});
	}
};

function showNextTaxStep(nextTaxStep) {
	$('.taxStyle').each(function(index) {
		if ($(this).attr("id") == nextTaxStep) {
			$(this).show(200);
		}
		else {
			$(this).hide(600);
		}
	});
}

function openTaxTab(evt, taxDetails) {
	var i, taxStyle, tablinks;
	taxStyle = document.getElementsByClassName("taxStyle");
	for (i = 0; i < taxStyle.length; i++) {
		taxStyle[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tablinks");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}
	document.getElementById(taxDetails).style.display = "block";
	evt.currentTarget.className += " active";
}

$("#mytable #checkall").click(function() {
	if ($("#mytable #checkall").is(':checked')) {
		$("#mytable input[type=checkbox]").each(function() {
			$(this).prop("checked", true);
		});

	} else {
		$("#mytable input[type=checkbox]").each(function() {
			$(this).prop("checked", false);
		});
	}
});

$("#searchBar").on("keyup", function() {
	var value = $(this).val().toLowerCase();
	$("#salaryRevisionTable tr").filter(function() {
		$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	});
});

var Id;
$("#employeeId").load($(this), function employeeInfo(Id) {
	var search = $("#employeeId").val();
	var url = '/getEmployeeDetails/' + Id.value;
	$.ajax({
		url: url,
		data: {}, //parameters go here in object literal form
		type: 'GET',
		datatype: 'json',
		success: function(data) { alert('got here with data'); },
		error: function() { alert('something bad happened'); }
	});
});

function salarySlipDownload() {
	var employeeId = document.forms["emloyeeSalaryForm"]["employeeId"].value;
	var joiningDate = document.forms["emloyeeSalaryForm"]["joiningDate"].value;
	var cTC = document.forms["emloyeeSalaryForm"]["cTC"].value;
	var annualBasicSalary = document.forms["emloyeeSalaryForm"]["annualBasicSalary"].value;
	var hra = document.forms["emloyeeSalaryForm"]["hra"].value;
	var convey = document.forms["emloyeeSalaryForm"]["convey"].value;
	var annualFlexibleBenifits = document.forms["emloyeeSalaryForm"]["annualFlexibleBenifits"].value;
	var pf = document.forms["emloyeeSalaryForm"]["pf"].value;
	var cTC = document.forms["emloyeeSalaryForm"]["cTC"].value;
	var esi = document.forms["emloyeeSalaryForm"]["esi"].value;
	var mediclame=document.forms["emloyeeSalaryForm"]["mediclame"].value;
	var travelExpensis=document.forms["emloyeeSalaryForm"]["travelExpensive"].value;
	var bonus=document.forms["emloyeeSalaryForm"]["bonus"].value;
	var incentives=document.forms["emloyeeSalaryForm"]["incentives"].value;
	var tds=document.forms["emloyeeSalaryForm"]["tds"].value;
	var gratuity = 0;
	var annualRefSalary = document.forms["emloyeeSalaryForm"]["annualRefSalary"].value;


	var employeeCtc = {
		"employeeId": employeeId,
		"joiningDate": joiningDate,
		"cTC": cTC,
		"annualBasicSalary": annualBasicSalary,
		"hra": hra,
		"convey": convey,
		"annualFlexibleBenifits": annualFlexibleBenifits,
		"pf": pf,
		"esi": esi,
		"gratuity": gratuity,
		"annualRefSalary": annualRefSalary,
		"mediclame":mediclame,
		"travelExpensis":travelExpensis,
		"bonus":bonus,
		"incentives":incentives,
		"tds":tds

	};

	var employeeCTCAccessBean = JSON.stringify(employeeCtc);

	console.log("jsonString == " + employeeCTCAccessBean);
	//var urlF=location.protocol + '/' +window.location.pathname.split('/')[1]+ '/' + 'admin/payroll/SalarySlips';
	var urlF = '/admin/payroll/SalarySlips';
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: urlF,
		data: employeeCTCAccessBean,
		dataType: 'application/pdf',
		cache: false,
		success: function(response) {
			console.log(response);
			window.location = urlF;
		},

		error: function(error) {
			console.log(error);
		}

	});
}



/*function calculatAnnualSalary() {
	var ctc = $("#cTC").val();
	var percentage = $("#percentage").val();
	var basicSalary = $("#annualBasicSalary").val();
	if (ctc != '') {
	  let annualSalary=  (ctc / 100) * percentage
	   	$("#annualBasicSalary").val();
		$("#hra").val(basicSalary/2);
	}
}

function calculateNetSalary() {
	var ctc = parseFloat($("#cTC").val());
	var annualBasicSalary =parseFloat($("#annualBasicSalary").val());
	var hra =parseFloat($("#hra").val());
	var convey =parseFloat( $("#convey").val());
	var annualFlexibleBenifits =parseFloat( $("#annualFlexibleBenifits").val());
	var pf =parseFloat ($("#pf").val());
	var esi =parseFloat ($("#esi").val());
	var netAmount =parseFloat ($("#annualRefSalary").val());
	
	
	
	var basicSalary=  $("#annualRefSalary").val(ctc-annualBasicSalary+hra+convey+annualFlexibleBenifits+pf+esi);
	
}
*/


function calculateOneTypesalary() {
	var ctc = parseFloat($("#cTC").val());
	var annualBasicSalary = parseFloat($("#annualBasicSalary").val());
	var hra = parseFloat($("#hra").val());
	var convey = parseFloat($("#convey").val());
	var annualFlexibleBenifits = parseFloat($("#annualFlexibleBenifits").val());
	var pf = parseFloat($("#pf").val());
	var esi = parseFloa($("#esi").val());
	var netAmount = parseFloat($("#annualRefSalary").val());

}
 var v= document.getElementById("annualRefSalary").value

 
  
function calculateFinalAmount() {
	var travelExpensive = $("#travelExpensive").val();
	var bonus = $("#bonus").val();
	var incentives = $("#incentives").val();
	var tds = $("#tds").val();
	var loan = $("#loan").val();
	var ctc = $("#cTC").val();
	var annualRefSalary =v;
       let basicSalary= $("#annualBasicSalary").val()
	if (travelExpensive  == '' && bonus==  '' && incentives == ' '  && tds == '' & loan == ''  && annualRefSalary  == '')  {
		alert('Error')
	}


	$.ajax({
		type:  'POST',
		url: '/getFinalAmount',
		data: {
			'travelExpensive': travelExpensive,
			'bonus': bonus,
			'incentives': incentives,
			'tds': tds,
			'loan': loan,
			'annualRefSalary': annualRefSalary,
			'basicSalary':basicSalary
		},
		success: function(data) {
			console.log(data)
			$("#annualRefSalary").val(data);
		},
		error: function(error) {
			console.log(error);
		}

	});



}

function totalCalculation(){
    var ctc = Number($("#cTC").val());
	var basicSalary = Number($("#annualBasicSalary").val());
	var hra = Number($("#hra").val());
	var convey = Number($("#convey").val());
	var annualFlexibleBenifits = Number($("#annualFlexibleBenifits").val());
	var pf = Number($("#pf").val());
	var esi = Number($("#esi").val());
	var myMediclam = Number($("#mediclame").val());
	var travelExpensive = Number($("#travelExpensive").val());
	var bonus = Number($("#bonus").val());
	var incentives = Number($("#incentives").val());
	var tds = Number($("#tds").val());
	//var netAmount = $("#annualRefSalary").val();

    
    var myBasicSalary=ctc/2;
    var myHra=myBasicSalary/2;
    var myConvey=1600;
    var myBenifts= ctc - (basicSalary + hra + convey);
    var myPf=pf// Number(basicSalary*.24);
    var myEsi=esi//Number(ctc*.04);
    var myMediclam=myMediclam;
    
    
    var myTravelExpensive=travelExpensive;
    var myBonus=bonus;
    var myIncentives=incentives;
   // var myTds=tds;
    
    var deduction=myPf+myEsi+myMediclam;
    var netAmount = ctc+myTravelExpensive+myBonus+myIncentives-deduction;
    
    $("#annualRefSalary").val(netAmount);
    
    
    
    

}




























