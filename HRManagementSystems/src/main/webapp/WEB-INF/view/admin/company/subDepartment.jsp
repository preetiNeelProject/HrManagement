
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
									<b>Sub-Department</b>
								</h2>
							</div>
						</div>
					</div>
					<div class="col-sm-6" id="addEmp">
						<div class="row">
							<ul style="float: right; margin-right: 10%;">
								<li>
								<a class="btn btn-danger" style="color: white;"
										href="${pageContext.request.contextPath}/admin/company/department">
										Department</a>
									<a class="btn btn-danger" style="color: white;"
										href="${pageContext.request.contextPath}/admin/company/addSubDepartment">Add
										Sub-Department</a>
										

								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<!-- ----------------------Department Data Table-------------------- -->
			<div class="col-md-12" style="">
				<div class="row" style="background: #f2f2f2;">
					<div id="allData" style="padding: 10px;">
						<div class="table-responsive">
							<table id="table" class="table table-bordred table-striped">

								<thead style="background: #283655; color: white;">
									<tr>
										<th>Select</th>
										<th>Department Name</th>
										<th>Sub-Department Name
										<th>Sub-Department Description</th>
										<th>Manager</th>
										<th>Total Employee</th>
										<th>Max Employee</th>
										<th>Min Employee</th>
										<th>Work Description</th>

										<th>Action</th>
									</tr>
								</thead>
								
								<tbody id="salaryRevisionTable">
									<c:if test="${not empty department}">
										<c:forEach items="${department}" var="department">
											<tr>
												<td><input type="checkbox" name="snos"
													value="${department.subDepartmentId}"></td>
													<td id="deptName">
													${department.departmentName}</td>
												<td id="deptName">
													${department.deptName}</td>
												<td id="status">${department.deptDesc}</td>
												<td id="managerId">${department.managerId}</td>
												<td id="totalEmployee">${department.totalEmployee}</td>
												<td id="maxEmployee">${department.maxEmployee}</td>

												<td id="minEmployee">${department.minEmployee}</td>
												<td id="workDesc">${department.workDesc}</td>
												<td style="display: inline-block; width: 150px;"><a
													class="btn btn-danger"
													href="${pageContext.request.contextPath}/admin/company/editSubDepartment/${department.subDepartmentId}"><i
														class="fa fa-pencil" aria-hidden="true"></i> </a> <%-- <a
													class="btn btn-warning"
													onclick="downloadTimeSheetForm('${location.workLocationId}')"><i
														class="fa fa-download" aria-hidden="true"></i> </a> --%> <a
													class="btn btn-success"
													href="${pageContext.request.contextPath}/admin/company/deleteSubDepartment/${department.subDepartmentId}"><i
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