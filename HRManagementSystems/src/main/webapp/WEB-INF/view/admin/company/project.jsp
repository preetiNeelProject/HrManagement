
<script src="${pageContext.request.contextPath}/staticResources/JavaScript/addEmployee.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
tfoot {
	display: table-header-group;
}

#table_filter {
	float: right;
}

#table_filter input {
	width: 258px;
	height: 30px;
	border-radius: 5px;
	margin-bottom: 10px;
}
</style>

<div class="container-fluid">
	<div class="row">
		<div class="col-lg-1 col-sm-12"><jsp:include
				page="../../commonView/mainNavbar.jsp" /></div>
		<div class="col-lg-11 col-sm-12">
			<div class="col-lg-11 col-sm-12"
				style="width: 100%; padding-right: 0px;"><jsp:include
					page="../../commonView/header.jsp" /></div>
			<div class="col-lg-11 col-sm-12" style="width: 100%;"><jsp:include
					page="../../commonView/companyNav.jsp" /></div>

			<!-- ----------------------Heading-------------------- -->
			<div class="col-md-12" style="">
				<div class="row" style=";">
					<div class="col-sm-6">
						<div class="row">
							<div style="margin-left: 29px;">
								<h2>
									<b>Company Work Location</b>
								</h2>
							</div>
						</div>
					</div>
					<div class="col-sm-6" id="addEmp">
						<div class="row">
							<ul style="float: right; margin-right: 10%;">
								<li>
									<button class="btn btn-danger" style="color: white;"
										onclick="location.href='../../admin/company/addWorkLocation'">Add Company
										Location</button>


								</li>
							</ul>
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
										<th>Address Title</th>
										<th>Address Type</th>
										<th>Address1</th>
										<th>Address2</th>
										<th>Address3</th>
										<th>City</th>
										<th>State</th>
										<th>Country</th>
										<th>Pincode</th>

										<!-- <th>Action</th> -->
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th style="width: 14px; height: 14px;"></th>
										<th>Address Title</th>
										<th>Address Type</th>
										<th>Address1</th>
										<th>Address2</th>
										<th>Address3</th>
										<th>City</th>
										<th>State</th>
										<th>Country</th>
										<th>Pincode</th>
										<!-- <th></th> -->
									</tr>
								</tfoot>
								<tbody id="salaryRevisionTable">
									<c:if test="${not empty location}">
										<c:forEach items="${location}" var="location">
											<tr>
											<td><input type="checkbox" name="snos"
												value="${location.workLocationId}"></td>
											<td id="addressTitle">${location.addressTitle}</td>
											<td id="addressType">${location.addressType}</td>
											<td id="address1">${location.address1}</td>
											<td id="address2">${location.address2}</td>
											<td id="address3">${location.address3}</td>
											<td id="city">${location.city}</td>
											<td id="state">${location.state}</td>
											<td id="country">${location.country}</td>
											<td id="pincode">${location.pincode}</td>

											<%-- <td style="display: inline-block; width: 150px;"><a
													class="btn btn-danger"
													onclick="location.href='../attendance/viewEmpAttendanceLog/${record.timesheetId}'"><i
														class="fa fa-user" aria-hidden="true"></i> </a> <a
													class="btn btn-warning"
													onclick="downloadTimeSheetForm('${record.timesheetId}')"><i
														class="fa fa-download" aria-hidden="true"></i> </a> <a
													class="btn btn-success"
													onclick="location.href='../attendance/deleteAttendance/${record.timesheetId}'"><i
														class="fa fa-trash" aria-hidden="true"></i> </a>
															&nbsp;&nbsp;</td> --%>
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



</body>
</html>
<script>
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
</script>