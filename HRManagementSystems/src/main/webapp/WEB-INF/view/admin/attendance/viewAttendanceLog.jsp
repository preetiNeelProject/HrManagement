
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script
	src="${pageContext.request.contextPath}/staticResources/JavaScript/addEmployee.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="${pageContext.request.contextPath}/staticResources/JavaScript/jquery-3.5.1.min.js" ></script>


<link rel="stylesheet" href="${pageContext.request.contextPath}/staticResources/styleSheets/jquery.dataTables.min.css" />

<script src="${pageContext.request.contextPath}/staticResources/JavaScript/jquery-dataTables.min.js"></script>

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
					page="../../commonView/attendanceNav.jsp" /></div>

			<div class="col-lg-11 col-sm-12">


				<div class="container">
					<div class="row">
						<div  style="display: block" id="summary">
							<div class="container">
								<div class="row">

									<div class="col-md-12">
										<div class="row">
											<div class="col-md-12">
												<div class="row">
													<div class="col-sm-6">
														<div id="mainHeadingTitle">Attendance Logs</div>
													</div>
													<div class="col-sm-6">

														<%-- <jsp:include page="../commonView/calender.jsp" /> --%>


													</div>

												</div>
											</div>
											<div class="col-md-12" >
												<div class="row " style=" margin-bottom: 9px;
                                                                           border-radius: 13px;">

                                                <sec:authorize  access="hasAuthority('ADMIN')">
													<div class="col-sm-6">
														<div class="row">
															<div id="addAttendanceBtn"
																style="float: left; margin: 3% 6%;">
																<a class="btn btn-danger"
																	href="${pageContext.request.contextPath}/admin/attendance/add_Attendance">Add
																	Attendance</a>
															</div>

															<div id="attendenceHeader"
																style="float: left; margin: 3% 6%;">
																<a class="btn btn-danger"
																	href="${pageContext.request.contextPath}/admin/attendance/add_Attendance_Header">Addendance
																	Header</a>
															</div>
														</div>
													</div>
													</sec:authorize>
													<div class="col-sm-6">
														<div class="row">
														
															<div class="col-sm-10">

																<ul style="border: none;" id="uploadForm">
																	<li>
																		<div id="mainHeadingCalender">
																			<form
																				action="${pageContext.request.contextPath}/admin/attendance/viewAttendanceLogDate"
																				id="viewTImeSheet" method="POST">
																				<input type="month" onchange='this.form.submit()'
																					id="filterDate" name="filterDate">
																					
																					<input type="hidden" value="${impId}" >  
																			</form>

																		</div>
																	</li>
																	
																	<sec:authorize access="hasAuthority('ADMIN')">
																	<li><label style="BACKGROUND: #EE6666;"><a
																			onclick="uploadTimeSheetForm()"><i
																				style="padding: 10px;" class="fa fa-upload"></i> </a> </label></li>
																	<li><span style="padding: 11px 0px; float: left;">Import</span></li>
																	
																	<li><label style="BACKGROUND: #28A745;"><a
																			onclick="exportTimeSheetForm()"><i
																				class="fa fa-upload fa-rotate-180"
																				style="padding: 10px;" style="padding-bottom: 9px;">
																			</i></a> </label></li>
																	<li><span style="padding: 11px 0px; float: left;">Export</span>
																	</li>
																	</sec:authorize>
																</ul>
															</div>

														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
							 	<div class="col-md-12" style="padding: 10px 40px 40px;">
										<div class="row" style="background: #f2f2f2;">
											<div id="allData" style="padding: 10px;">
												<div class="table-responsive">
													<table id="tableData" class="table table-bordred table-striped">
														<thead style="background: #283655; color: white;">
															<tr>
																<th>Select</th>
																<th>Employee Id
																<th>Employee Name</th>
																<th>Status</th>
																<th>In-Time</th>
																<th>Out-Time</th>
																<th>Work Duration</th>
															    <th>Date</th> 
																<th>Location</th>
																<th>Department</th>
																<th>Manager</th>												
																										
															<sec:authorize access="hasAuthority('ADMIN')">	
																<th>Action</th>
															</sec:authorize>
															</tr>
														</thead>
										
														<tbody id="salaryRevisionTable" style="background-color: aliceblue">
															<c:if test="${not empty records}">
																<c:forEach items="${records}" var="record">
																	<tr>
																		<td><input type="checkbox" name="snos"
																			value="${record.timesheetId}"></td>
																		<%-- <c:if test="${record.employeeId!=' '}"> --%>
																		<td id="employeeCode" value="${record.employeeId}">${record.employeeCode}<p
																				window.onload="employeeInfo('${record.employeeId}')"></p></td>
																		<%-- 	</c:if> --%>
																		<td id="firstName">${record.firstName}
																			${record.lastName}</td>
																		<td id="status">${record.status}</td>
																		<td id="inTime">${record.inTime}</td>
																		<td id="outTime">${record.outTime}</td>
																		<td id="workingHours">${record.workDuration}</td>

                                                                        <td id="date">${record.date}</td>
                                                                        
                                                                        
																		<%-- <td id="overTime">${record.overTime}</td> --%>
																		<td id="add1">${record.add1}${record.add2}
																			${record.add3}</td>
																		<td id="departmentName">${record.departmentName}</td>
																		<td id="manager">${record.manager}</td>


                                                <sec:authorize  access="hasAuthority('ADMIN')">


																		<td style="display: inline-block; width: 150px;"><a
																			class="btn btn-danger"
																			href="${pageContext.request.contextPath}/admin/attendance/viewEmpAttendanceLog/${record.timesheetId}"><i
																				class="fa fa-user" aria-hidden="true"></i> </a> <a
																			class="btn btn-warning"
																			onclick="downloadTimeSheetForm('${record.timesheetId}')"><i
																				class="fa fa-download" aria-hidden="true"></i> </a> <a
																			class="btn btn-success"
																			href="${pageContext.request.contextPath}/admin/attendance/deleteAttendance/${record.timesheetId}"><i
																				class="fa fa-trash" aria-hidden="true"></i> </a>
																			&nbsp;&nbsp;</td>





</sec:authorize>


																	</tr>
																</c:forEach>
															</c:if>
															<c:if test="${empty records}">
																<tr>
																	<td colspan="10">No Data Found...</td>
																</tr>
															</c:if>
														</tbody>

													</table>

													<div class="clearfix"></div>


												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- Start Export Form -->
				<div class="form-popup" id="myForm">
					<form class="form-horizontal"
						action="${pageContext.request.contextPath}/admin/attendance/exportData"
						method="get">
						<h4>Select Date Range</h4>

						<label for="fromDate"><b>From</b></label> <input type="date"
							name="fromDate" /> <br /> <label for="toDate"><b>To</b>
						</label> <input type="date" name="toDate" /><br />


						<button type="submit" class="btn">Export</button>
						<button type="button" class="btn cancel" onclick="closeForm()">Close</button>
					</form>
				</div>
				<!-- End Export Form -->
				<!-- Start Import Form -->
				<div class="form-popup" id="myExportForm">
					<form class="form-horizontal"
						action="${pageContext.request.contextPath}/admin/attendance/uploadAttendance"
						enctype="multipart/form-data" method="post">
						<h4>File(CSV only)</h4>
						<br> <input type="file" name="csv" accept=".csv" required><br />
						<br>
						<button type="submit" class="btn">Import</button>
						<button type="button" class="btn cancel" onclick="closeForm1()">Close</button>
					</form>
				</div>
				<!-- End Import Form -->
		<!-- 	</div>

		</div>
	</div>
</div> -->
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
		document.getElementById("myForm").style.display = "none";
	}

	function uploadTimeSheetForm() {
		document.getElementById("myExportForm").style.display = "block";
	}

	function closeForm1() {
		document.getElementById("myExportForm").style.display = "none";
	}
</script>