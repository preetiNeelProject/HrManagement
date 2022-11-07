

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
					page="../../commonView/companyNav.jsp" /></div>

			<!-- ----------------------Heading-------------------- -->
			<div class="col-md-12" style="background-color:;
    margin-bottom: 9px;
    border-radius: 13px;">
				<div class="row" style=";">
					<div class="col-sm-6">
						<div class="row">
							<div style="margin-left: 29px;">
								<h2>
									<b>Role</b>
								</h2>
							</div>
						</div>
					</div>
					<div class="col-sm-6" id="addEmp">
						<div class="row">
							<ul style="float: right; margin-right: 10%;">
								<li>
								<a class="btn btn-danger" style="color: white;"
										href="${pageContext.request.contextPath}/admin/company/subRole">
										Sub-Role</a>
									<a class="btn btn-danger" style="color: white;"
										href="${pageContext.request.contextPath}/admin/company/addRole">Add
										Role</a>
										

								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<!-- ----------------------Role Data Table-------------------- -->
			<div class="col-md-12" style="">
				<div class="row" style="background: #f2f2f2;">
					<div id="allData" style="padding: 10px;">
						<div class="table-responsive">
							<table id="tableData" class="table table-bordred table-striped">

								<thead style="background: #283655; color: white;">
									<tr>
										<th>Select</th>
										<th>Role</th>
										<th>Role Type</th>
										<th>Role Description</th>
										<th>Action</th>
									</tr>
								</thead>
							
								<tbody id="salaryRevisionTable">
									<c:if test="${not empty role}">
										<c:forEach items="${role}" var="role">
											<tr>
												<td><input type="checkbox" name="snos"
													value="${role.roleId}"></td>
												<td id="roleName">
													${role.roleName}</td>
													<td id="roleType">${role.roleType}</td>
												<td id="role">${role.roleDesc}</td>
												<td style="display: inline-block;"><a
													class="btn btn-success"
													href="${pageContext.request.contextPath}/admin/company/editRole/${role.roleId}"><i
														class="fa fa-pencil" aria-hidden="true"></i> </a>  <a
													class="btn btn-danger"
													href="${pageContext.request.contextPath}/admin/company/deleteRole/${role.roleId}"><i
														class="fa fa-trash" aria-hidden="true"></i>  </a> 
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



</body>
</html>
