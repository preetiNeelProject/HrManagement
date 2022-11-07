<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/staticResources/JavaScript/leave.js"></script>

<link rel="stylesheet" href="<c:url value='${pageContext.request.contextPath}/staticResources/styleSheets/tableManager.css'/>"/>
<script type="text/javascript" src="<c:url value='${pageContext.request.contextPath}/staticResources/JavaScript/table.js'/>"></script>
<script type="text/javascript" src="<c:url value='${pageContext.request.contextPath}/staticResources/JavaScript/table.min.js'/>"></script>


<div class="container-fluid">
	<div class="row">
		<div class="col-lg-1 col-sm-12"><jsp:include
				page="../../commonView/mainNavbar.jsp" /></div>
		<div class="col-lg-11 col-sm-12">
			<div class="col-lg-11 col-sm-12"
				style="width: 100%; padding-right: 0px;"><jsp:include
					page="../../commonView/header.jsp" /></div>
			<div class="col-lg-11 col-sm-12" style="width: 100%;"><jsp:include
					page="../../commonView/leaveNav.jsp" /></div>

			<div class="col-lg-11 col-sm-12">


				<div class="container">
					<div class="row">
						<div class="taxStyle" style="display: block" id="summary">
							<div class="container">
								<div class="row">
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
									<div class="col-md-12">
										<div class="row">
											<div class="col-md-12" style="margin: 20px;">
												<div class="row">
													<div class="col-sm-6">
														<div id="mainHeadingTitle">Rejected Leave Logs</div>
													</div>
													<div class="col-sm-6">

														<%-- <jsp:include page="../commonView/calender.jsp" /> --%>
														<!-- <div id="mainHeadingCalender">
															<form action="../../admin/leave/viewLeaveLogDate"
																id="viewTImeSheet" method="POST">
																<input type="date" onchange='this.form.submit()'
																	id="filterDate" name="filterDate">
															</form>

														</div> -->
													</div>

												</div>
											</div>
											<div class="col-md-12" style="padding: 0px 40px;">
												<div class="row"
													style="background: #f2f2f2; border-top-right-radius: 10px; border-top-left-radius: 10px;">

													<div class="col-sm-6"></div>
													<div class="col-sm-6">
														<div class="row">
															<div class="col-sm-2"></div>

															<div class="col-sm-6">

																<!-- <ul style="border: none;" id="uploadForm">
																	<li></li>
																	<li><label style="BACKGROUND: #EE6666;"><a
																			onclick="uploadTimeSheetForm()"><i
																				class="fa fa-upload"></i> </a> </label></li>
																	<li><span style="padding: 11px 0px; float: left;">Import</span></li>
																	<li><label style="BACKGROUND: #28A745;"><a
																			onclick="exportTimeSheetForm()"><i
																				class="fa fa-upload fa-rotate-180"
																				style="padding-bottom: 9px;"> </i></a> </label></li>
																	<li><span style="padding: 11px 0px; float: left;">Export</span></li>
																</ul> -->
															</div>
															<div class="col-sm-4">
																<button type="button" class="btn btn-success"
																	style="color: white;margin: 15%;"
																	onclick="applyLeave('${record.employeeId}','${record.leavesId}');">Apply
																	Leave</button>

																<!-- <a class="btn btn-danger" style="color: white;margin-top: 30%;"
																	onclick="applyLeaveForm()">Apply
																	Leave</a> -->
															</div>

														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- ----------------------WorkLocation Data Table-------------------- -->
									<div class="col-md-12" style="">
										<div class="row" style="background: #f2f2f2;">
											<div id="allData" style="padding: 10px;">
												<div class="table-responsive">
													<table id="table" class="table table-bordred table-striped">

														<thead style="background: #283655; color: white;">
															<tr>
																<th>Select</th>
																<th>Employee Id</th>
																<th>Employee Name</th>
																<th>Status</th>
																<th>Leave Type</th>
																<th>From-Date</th>
																<th>To-Date</th>
																<th>Apply Date</th>
																<th>Reason</th>
																<th>Action</th>
															</tr>
														</thead>
														<tbody id="salaryRevisionTable">
															<c:if test="${not empty records}">
																<c:forEach items="${records}" var="record">
																	<tr>
																		<td><input type="checkbox" name="snos"
																			value="${record.leavesId}"></td>
																		<c:if test="${record.employeeId!=' '}">
																			<td id="employeeCode" value="${record.employeeId}">${record.employeeCode}<p
																					window.onload="employeeInfo('${record.employeeId}')"></p></td>
																		</c:if>
																		<td id="firstName">${record.employeeName}
																			${record.employeeLastName}</td>
																		<td id="status">${record.status}</td>
																		<td id="leaveTypeId">${record.leaveType}</td>
																		<td id="fromDate">${record.fromDate}</td>
																		<td id="toDate">${record.toDate}</td>

																		<td id="applyDate">${record.applyDate}</td>
																		<td id="reason">${record.reason}</td>
																		<td style="">
																			<%-- <a class="btn btn-danger"
																			href="${pageContext.request.contextPath}/admin/leave/acceptLeave/${record.leavesId}">Accept</a> --%>
																			<button type="button" class="btn btn-success"
																				style="color: white;"
																				onclick="applyLeave('${record.employeeId}','${record.leavesId}');"><i class="fa fa-check" aria-hidden="true"></i></button> &nbsp;&nbsp;
																		</td>
																	</tr>
																</c:forEach>
															</c:if>
														</tbody>

													</table>

													<div class="clearfix"></div>


												</div>
											</div>
										</div>
									</div>


									<!-- -----------Form------------------------ -->
								</div>
							</div>
						</div>
					</div>
				</div>


				<!-- Satet Apply Leave Form -->
				<!-- Modal -->
				<div  id="applyLeave" class="modal">
					<div class="modal-dialog">

						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header"
								style="background-color: #387403; color: white;">
								<button type="button" onclick="closeApplyForm();"
									data-dismiss="modal"
									style="color: white; float: right; background: none; border: none;">&times;</button>
								<h4 class="modal-title">Apply Leave</h4>
							</div>
							<form class="form-horizontal" action="/leave/applyLeave"
								method="post" id="applyLeaveform">
								<div class="modal-body">
									<div class="row" id="errorApplyLeave" style="color:red;display:none;margin: 5px 0px 20px;"><h4>Error : All fields are mandatory</h4></div>
									<div class="row" id="errorDateApplyLeave" style="color:red;display:none;margin: 5px 0px 20px;"><h4>Error : Check your date</h4></div>
									<div class="row" style="line-height: 5px;">
										<div class="col-sm-12">
											<div class="form-group">
												<label for="leaveTypeId" class="col-form-label">Leave
													Type:</label>
												<div>
													<select name="leaveTypeId" id="leaveTypeId"
														style="margin-bottom: 5px;">
														<c:forEach items="${leaveType}" var="leaveType">
������																��<option value="${leaveType.leaveTypeId}">${leaveType.leaveName}</option>
��																��</c:forEach>
													</select><br />
												</div>
											</div>
										</div>
									</div>
									<div class="row" style="line-height: 5px;">
										<div class="col-sm-6">
											<div class="form-group">
												<label for="fromDate" class="col-form-label">From
													Date:</label> <input type="date" class="form-control"
													name="fromDate" id="fromDate" style="margin-bottom: 5px;">
												<input type="hidden" class="form-control" name="status"
													id="status" value="Pending" style="margin-bottom: 5px;">
													<input type="hidden" class="form-control" name=""
													id="empId" value="empId" style="margin-bottom: 5px;">
													<input type="hidden" class="form-control" name=""
													id="leId" value="leId" style="margin-bottom: 5px;">
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label for="toDate" class="col-form-label">To Date:</label>
												<input type="date" class="form-control" id="toDate"
													name="toDate" style="margin-bottom: 5px;">
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<label for="reason" class="col-form-label">Reason:</label>
												<textarea rows="4" cols="50" maxlength="50" id="reason"
													name="reason" style="margin-bottom: 5px; height: auto"></textarea>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<input type="button" class="action-button" name="submit"
													style="background-color: #d9534f; border-color: #d43f3a; padding: 7px; width: 100px; font-size: 16px; float: right; margin: 10px 10px 5px;"
													onclick="applyLeaveForm();" value="Submit" />
											</div>
										</div>
									</div>





								</div>
							</form>
							<!-- <div class="modal-footer"
								style="background-color: #387403; color: white;">
							
							</div> -->
						</div>

					</div>
				</div>
				
				<!-- End Leave Form -->


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
		document.getElementById("myForm").style.display = "none";
	}

	function uploadTimeSheetForm() {
		document.getElementById("myExportForm").style.display = "block";
	}

	function closeForm1() {
		document.getElementById("myExportForm").style.display = "none";
	}

	function closeApplyForm() {
		document.getElementById("applyLeave").style.display = "none";
	}

	function applyLeave(employeeId, leavesId) {
		$('#empId').val(employeeId);
		$('#leId').val(leavesId);
		document.getElementById("applyLeave").style.display = "block";
	}
</script>