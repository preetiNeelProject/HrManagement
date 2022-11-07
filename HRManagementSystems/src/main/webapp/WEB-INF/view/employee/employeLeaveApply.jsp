

<script
	src="${pageContext.request.contextPath}/staticResources/JavaScript/leave.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${pageContext.request.contextPath}/staticResources/styleSheets/bootstrap.min.css">


<script src="${pageContext.request.contextPath}/staticResources/JavaScript/jquery-3.5.1.min.js">  </script>
<script src="${pageContext.request.contextPath}/staticResources/JavaScript/jquery-dataTables.min.js">  </script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/staticResources/styleSheets/jquery.dataTables.min.css"></link>




<script>

$(document).ready( function () {
    $('#tableData').DataTable();
} );

</script>




<style>

.navbar {
	overflow: unset !important;
	background-color: none;
	width: auto !important;
}
.navBody a:hover {
  background-color: transparent !important;
  font-size: 20px;
  li{
    text-decoration: underline;
    }
}
.navBody li:hover {
  
  
    text-decoration: underline;
  
}


.modal-backdrop {
z-index:0 !important;
}
</style>

<script  src="${pageContext.request.contextPath}/staticResources/JavaScript/bootstrap.min.js"></script>

<div class="container-fluid">
	<div class="row">
		<div class="col-lg-1 col-sm-12"><jsp:include
				page="../commonView/mainNavbar.jsp" /></div>
		<div class="col-lg-11 col-sm-12">
			<div class="col-lg-11 col-sm-12"
				style="width: 100%; padding-right: 0px;"><jsp:include
					page="../commonView/header.jsp" /></div>
			<div class="col-lg-11 col-sm-12" style="width: 100%;"><jsp:include
					page="../commonView/profileNav.jsp" /></div>

			<div class="col-lg-6 col-sm-6 text-center" >
				<h1>Leave Applications</h1>
			</div>

			<div class="col-lg-6 col-sm-6">

				<button type="button" class="btn btn-sm btn-danger"
					data-toggle="modal" data-target="#leave-form"
					style="float:right; margin-top: 2%;">Apply for Leave</button>

				<div id="leave-form" class="modal fade" role="dialog"
					aria-labelledby="exampleModalLabel">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="model-header bg-primary text-center">
								<h1 style="margin: 0">Apply For Leave</h1>
							</div>
							<div class="modal-body"></div>
							<form class="form-horizontal" action="/leave/applyLeave"
								method="post" id="applyLeaveform">
								<input type="hidden" name="employeeId"
									value="${sessionScope.impId}">
								<div class="modal-body">
									<div class="row" id="successFullyApplied"
										style="color: red; display: none; margin: 5px 0px 20px;">
										<h4>Successfully Leave Added...</h4>
									</div>
									<div class="row" id="errorApplyLeave"
										style="color: red; display: none; margin: 5px 0px 20px;">
										<h4>Error : All fields are mandatory</h4>
									</div>
									<div class="row" id="errorDateApplyLeave"
										style="color: red; display: none; margin: 5px 0px 20px;">
										<h4>Error : Check your date</h4>
									</div>

									<div class="row" style="line-height: 5px;">
										<div class="col-sm-12">
											<div class="form-group">
												<label for="leaveTypeId" class="col-form-label">Leave
													Type:</label>
												<div>
													<select name="leaveTypeId" id="leaveTypeId"
														style="margin-bottom: 5px;">
														<c:forEach items="${leaveType}" var="leaveType">
															<option value="${leaveType.leaveTypeId}">${leaveType.leaveName}</option>
																</c:forEach>
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
												<input type="hidden" class="form-control" name="" id="empId"
													value="empId" style="margin-bottom: 5px;"> <input
													type="hidden" class="form-control" name="" id="leId"
													value="leId" style="margin-bottom: 5px;">
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
													style="background-color: #d9534f; border-color: #d43f3a; padding: 7px; width: 100%; font-size: 16px;"
													onclick="applyLeaveForm();" value="Submit" />
											</div>
										</div>
									</div>





								</div>
							</form>


						</div>



					</div>
				</div>

			</div>
		</div>

	</div>
	<div class="container" style="margin-left: 10%">
		<div class="col-md-12" style="">
			<div class="row" style="background: #f2f2f2;">
				<div id="allData" style="padding: 10px;">
					<div class="table-responsive">
						<table id="tableData" class="table table-bordred table-striped">

							<thead style="background: #283655; color: white;">
								<tr>

									<th>Employe Name
									<th>Leave Type</th>
									<th>From Date</th>
									<th>To Date</th>
									<th>Reason</th>
									<th>Comments</th>
									<th>Approved By </th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody id="salaryRevisionTable">

								<c:forEach items="${leave}" var="leave">
									<tr>

										<td id="deptName">${leave.leaveId}</td>
										<td id="status">${leave.leaveType}</td>
										<td id="managerId">${leave.fromDate}</td>
										<td id="totalEmployee">${leave.toDate}</td>
										<td id="maxEmployee">${leave.reason}</td>
										<td id="maxEmployee">${leave.comments}</td>
										<td  id="">${leave.approvedBy}</td>
										<c:if test="${leave.status eq 'Pending'}">
											<td id="maxEmployee" class="btn btn-danger" disabled>${leave.status}</td>
										</c:if>
										<c:if test="${leave.status eq 'Approved'}">
											<td id="maxEmployee" class="btn btn-success">${leave.status}</td>
										</c:if>
										<c:if test="${leave.status eq 'Rejected'}">
											<td id="maxEmployee" class="btn btn-secondary">${leave.status}</td>
										</c:if>

									</tr>
								</c:forEach>
								
								





							</tbody>

						</table>

						<div class="clearfix"></div>


					</div>
				</div>
			</div>
		</div>



	</div>
</div>

</body>
</html>
<script>
	
</script>