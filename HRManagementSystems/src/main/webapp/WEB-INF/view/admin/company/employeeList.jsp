<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="generalForm"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="${pageContext.request.contextPath}/staticResources/JavaScript/jquery-3.5.1.min.js" ></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/staticResources/styleSheets/jquery.dataTables.min.css" />


<script 	src="${pageContext.request.contextPath}/staticResources/JavaScript/jquery-dataTables.min.js"></script>

<script>

$(document).ready( function () {
    $('#tableData').DataTable();
} );
</script>



<!-- ===================POP-UP===================-->
<c:if test="${not empty msg}">
	<div id="modal" class="modal" style="display: block;">
		<div class="modal-content" style="width: 750px;">
			<div class="modal-header" style="background-color: #387403;">
				<span class="close"
					onclick="document.getElementById('modal').style.display='none'"
					style="color: #FFFFFF;">&times;</span>
				<p style="text-align: center; color: #FFFFFF;" class="h3"
					id="contentPara">${msg}</p>
			</div>
		</div>
	</div>
</c:if>
<!-- ===================POP-UP===================-->

<div class="container-fluid">
	<div class="row">
		<div class="col-lg-1 col-sm-12"><jsp:include
				page="../../commonView/mainNavbar.jsp" /></div>
		<div class="col-lg-11 col-sm-12">
			<div class="col-lg-11 col-sm-12"
				style="width: 100%; padding-right: 0px;"><jsp:include
					page="../../commonView/header.jsp" /></div>
			<div class="col-lg-11 col-sm-12" style="width: 100%;"><jsp:include
					page="../../commonView/directoryNav.jsp" /></div>

			<div class="col-lg-11 col-sm-12">

				<div class="container">
					<div class="row">
						<div class="taxStyle" style="display: block;background-color:transparent" id="summary">
							<div class="col-md-12">
								<div class="row">
									<div class="col-sm-6">
										<div class="row"></div>
									</div>

								</div>
							</div>


							<div class="col-md-12">
								<div class="row"
									style="background-color: ; margin-bottom: 9px; border-radius: 13px;">


									<div class="col-sm-12">
										<div class="row">

											<div class="col-sm-6">
												<li id="mainHeadingTitle">Employee List</li>
											</div>
											<div class="col-sm-6">

												<ul style="border: none;" id="uploadForm">

											<!--		<li><label style="BACKGROUND: #EE6666;"><a
															onclick="uploadTimeSheetForm()"><i
																style="padding: 10px;" class="fa fa-upload"></i> </a> </label></li>
													<li><span style="padding: 11px 0px; float: left;">Import</span></li>
													<li><label style="BACKGROUND: #28A745;"><a
															onclick="exportTimeSheetForm()"><i
																class="fa fa-upload fa-rotate-180"
																style="padding: 10px;" style="padding-bottom: 9px;">
															</i></a> </label></li>
													<li><span style="padding: 11px 0px; float: left;">Export</span></li>
													-->
													<li><a class="btn btn-danger"
														href="${pageContext.request.contextPath}/admin/company/addEmployee">Add
															Employee</a></li>
												</ul>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-12" style="">
						<div class="row" style="background: #f2f2f2;">


							<div id="allData" style="padding: 10px;">
								<div class="table-responsive">
									<table id="tableData" class="table table-bordred table-striped">

										<thead style="background: #283655; color: white;">
											<tr>
									
												<th>Select</th>
												<th>Employee Code</th>												
												<th>Name</th>
												<th>Designation</th>
												<th>Manager</th>
												<th>LoginId</th>
												<th>Phone</th>												
												<th>Delete</th>
											</tr>
										</thead>
										<tbody id="salaryRevisionTable">
											<c:forEach items="${records}" var="record">
												<tr>
													<td><input type="checkbox" name="snos"
														value="${record.employeeId}"></td>
													<td>${record.employeeCode}</td>
													
													<td>${record.fname}${record.lname}</td>
													<td>${record.designation}</td>
													<td>${record.manager}</td>
													<td>${record.loginId}</td>
													<td>${record.phone}</td>
                                    
												<!--	<td><a class="btn btn-warning"
														style="margin-right: 6%;"
														href="${pageContext.request.contextPath}/employee/editProfile/${record.employeeId}"><i
															class="fa fa-user" aria-hidden="true"></i> </a> <a
														class="btn btn-success" style="margin-right: 6%;"
														href="${pageContext.request.contextPath}/employee/employeeDocument/${record.employeeId}"><i
															class="fa fa-file" aria-hidden="true"></i> </a> &nbsp;&nbsp;
													</td>  -->	
													<td><a
														href="${pageContext.request.contextPath}/employee/deleteEmploye/${record.employeeId}"
														onclick="" style="text-decoration: none;"> Delete</a></td>
												</tr>
											</c:forEach>
										</tbody>

									</table>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Start Export Form -->
		<div class="form-popup" id="myExportForm">
			<form class="form-horizontal"
				action="${pageContext.request.contextPath}/admin/attendance/uploadAttendance"
				enctype="multipart/form-data" method="post">
				<h4>File(CSV only)</h4>
				<br> <input type="file" name="csv" accept=".csv" required><br />
				<br>
				<button type="submit" class="btn">Import</button>
				<button type="button" class="btn cancel" onclick="closeForm()">Close</button>
			</form>
		</div>
		<!-- End Export Form -->

		<div class="form-popup" id="myForm">
			<form class="form-horizontal" action="/attendance/exportData"
				method="GET"">
				<h4>Select Date Range</h4>

				<label for="fromDate"><b>From</b></label> <input type="date"
					name="fromDate" /><br /> <label for="toDate"><b>To</b></label> <input
					type="date" name="toDate" /><br />

				<button type="submit" class="btn">Export</button>
				<button type="button" class="btn cancel" onclick="closeForm1()">Close</button>
			</form>
		</div>
	</div>

</div>
</div>
</div>
</body>
</html>
<script>
	function exportTimeSheetForm() {
		document.getElementById("myForm").style.display = "block";
	}
	function downloadTimeSheetForm(timesheetId) {
		document.getElementById("myForm").style.display = "block";
	}

	function closeForm() {
		document.getElementById("myExportForm").style.display = "none";

	}

	function uploadTimeSheetForm() {
		document.getElementById("myExportForm").style.display = "block";
	}

	function closeForm1() {
		document.getElementById("myForm").style.display = "none";
	}
</script>