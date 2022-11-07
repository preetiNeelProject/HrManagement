<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="${pageContext.request.contextPath}/staticResources/JavaScript/jquery-3.5.1.min.js">  </script>
<script src="${pageContext.request.contextPath}/staticResources/JavaScript/jquery-dataTables.min.js">  </script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/staticResources/styleSheets/jquery.dataTables.min.css"></link>




<script>

$(document).ready( function () {
    $('#tableData').DataTable();
} );

</script>

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
										<div class="row" style="margin:2%">

                                              
											
										
									<!-- ----------------------WorkLocation Data Table-------------------- -->
									<div class="col-md-12" style="">
										<div class="row" style="background: #f2f2f2;">
											<div id="allData" style="padding: 10px;">
												<div class="table-responsive">
													<table id="tableData" class="table table-bordred table-striped">

														<thead style="background: #283655; color: white;">
															<tr>
																<th>Select</th>
																<th>Employee Code</th>
																<th>Employee Name</th>
	                                                             <th>Status</th>
																<th>Leave Type</th>
																<th>From-Date</th>
																<th>To-Date</th>
																<th>Apply Date</th>
																<th>Reason</th>
																<th>Approved By</th>
																<th>Comments</th>
																	
															 
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
																		<td id="firstName">${record.firstName}
																			${record.lastName}</td>
																	        	<td id="status">${record.status}</td>
																		<td id="leaveTypeId">${record.leaveType}</td>
																		<td id="fromDate">${record.fromDate}</td>
																		<td id="toDate">${record.toDate}</td>

																		<td id="applyDate">${record.applyDate}</td>
																		<td id="reason">${record.reason}</td>
																		<td id="approvedBy">${record.approvedBy}</td>
																		<td id="comments">${record.comments}</td>

																		<td style="display: inline-block; width: 150px;"><a
																			class="btn btn-danger"
																			href="${pageContext.request.contextPath}/admin/leave/viewEmpLeaveLog/${record.timesheetId}"><i
																				class="fa fa-user" aria-hidden="true"></i> </a> <a
																			class="btn btn-warning"
																			onclick="downloadTimeSheetForm('${record.timesheetId}')"><i
																				class="fa fa-download" aria-hidden="true"></i> </a> <a
																			class="btn btn-success"
																			href="${pageContext.request.contextPath}/admin/leave/deleteLeave/${record.timesheetId}"><i
																				class="fa fa-trash" aria-hidden="true"></i> </a>
																			&nbsp;&nbsp;</td>
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
				<!-- Start Export Form -->
				<div class="form-popup" id="myForm">
					<form class="form-horizontal" action="/leave/exportData"
						th:action="@{exportData}" method="GET" th:object="${timesheet}">
						<h4>Select Date Range</h4>

						<label for="fromDate"><b>From</b></label> <input type="date"
							name="fromDate" th:field="*{fromDate}" /><br /> <label
							for="toDate"><b>To</b></label> <input type="date" name="toDate"
							th:field="*{toDate}" /><br /> <input type="hidden"
							name="timeshId" id="timeshId" th:field="*{toDate}" /><br />

						<button type="submit" class="btn">Export</button>
						<button type="button" class="btn cancel" onclick="closeForm()">Close</button>
					</form>
				</div>
				<!-- End Export Form -->
				<!-- Start Import Form -->
				<div class="form-popup" id="myExportForm">
					<form class="form-horizontal" action="/leave/uploadLeave"
						enctype="multipart/form-data" method="post">
						<h4>File(CSV only)</h4>
						<br> <input type="file" name="csv" accept=".csv,.xlsx"
							required><br /> <br>
						<button type="submit" class="btn">Import</button>
						<button type="button" class="btn cancel" onclick="closeForm1()">Close</button>
					</form>
				</div>
				<!-- End Import Form -->
			</div>

		</div>
	</div>
</div>
</body>
</html>
<script>
	var id;
	var $rows = $('#salaryRevisionTable tr');
	$('#search').keyup(function() {
		var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

		$rows.show().filter(function() {
			var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
			return !~text.indexOf(val);
		}).hide();
	});

	$('#table tfoot th').each(function() {
		var title = $(this).text();
		$(this).html('<input type="text" placeholder="Search '+title+'" />');
	});

	// DataTable
	var otable = $('#table').DataTable();

	// Apply the search
	otable.columns().every(function() {

		var that = this;
		$('input', this.footer()).on('keyup change', function() {
			if (that.search() !== this.value) {
				that.search(this.value).draw();
			}
		});
	});

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