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



	<div class="row">
		<div class="col-lg-1 col-sm-12"><jsp:include
				page="../../commonView/mainNavbar.jsp" /></div>
		<div class="col-lg-11 col-sm-12">
			<div class="col-lg-11 col-sm-12"
				style="width: 100%; padding-right: 0px;"><jsp:include
					page="../../commonView/header.jsp" /></div>
			<div class="col-lg-11 col-sm-12" style="width: 100%;"><jsp:include
					page="../../commonView/payrollNav.jsp" /></div>


			<!-- ----------------------Heading-------------------- -->
			<div class="col-md-12" style="
    background-color:;
    margin-bottom: 9px;
    border-radius: 13px;
">
				<div class="row" style=";">
					<div class="col-sm-4">
						<div class="row">
							<div style="margin-left: 29px;">
								<h2>
									<b>Salary Revision</b>
								</h2>
							</div>
						</div>
					</div>
					<div class="col-sm-8" id="addEmp">
						<div class="row">
							<ul style="float: right; margin-right: 10%;">
								<li><a class="btn btn-danger" style="color: white;"
									href="${pageContext.request.contextPath}/admin/payroll/salaryStructure">
										Add Salary</a> <a class="btn btn-danger" style="color: white;"
									href="${pageContext.request.contextPath}/admin/payroll/addIncrementSalary">
										Increment Salary</a> <input type="month" id="salaryId"
									Style="margin-left: 20px">
									
									 <a class="btn btn-primary"		id="salaryDownload" >Salary</a></li>
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
										<th><input type="checkbox" id="checkall" /></th>
										<th>Employee Id</th>
										<th>Employee Name</th>
										<th>CTC(Rs)</th>
										<th>Basic(Rs)</th>
										<th>HRA(Rs)</th>
										<th>Convey(Rs)</th>
										<th>Other(Rs)</th>
										<th>PF(Rs)</th>
										<th>ESI(Rs)</th>
										<th>Edit</th>
										<th>Download</th>
									</tr>
								</thead>
								<tbody id="salaryRevisionTable">
									<%-- 	<c:forEach items="${records}" var="record">
										<tr>
											<td><input type="checkbox" name="snos"
												value="${record.ctcHistoryId}"></td>
											<td>${record.employeeId}</td>
											<td>${record.name}</td>
											<td>${record.cTC}</td>
											<td>${record.newCtc}</td>
											<td>${record.incrementDate}</td>
											<td>${record.comments}</td>
											<td style="display: inline-block; width: auto;"><a
													class="btn btn-danger"
													href="${pageContext.request.contextPath}/admin/payroll/salarySlip/${record.ctcHistoryId}">Slip </a> <a
													class="btn btn-warning"
													onclick=""><i
														class="fa fa-download" aria-hidden="true"></i> </a> <a
													class="btn btn-success"
													onclick=""><i
														class="fa fa-trash" aria-hidden="true"></i> </a>
															&nbsp;&nbsp;</td>
											
										</tr>
									</c:forEach> --%>
									<c:forEach items="${currentRecords}" var="currentRecord">
										<tr>
											<td><input type="checkbox" name="snos"
												value="${currentRecord.employeeCTCId}"></td>
											<td>${currentRecord.employeeId}</td>
											<td>${currentRecord.name}</td>
											<td>${currentRecord.cTC}</td>
											<td>${currentRecord.annualBasicSalary}</td>
											<td>${currentRecord.hra}</td>
											<td>${currentRecord.convey}</td>
											<td>${currentRecord.annualFlexibleBenifits}</td>
											<td>${currentRecord.pf}</td>
											<td>${currentRecord.esi}</td>
											<td style="display: inline-block;"><a
												class="btn btn-warning"
												href="${pageContext.request.contextPath}/admin/payroll/salaryStructure"><i
													class="fa fa-pencil" aria-hidden="true"></i> </a> <a
												class="btn btn-success"
												href="${pageContext.request.contextPath}/admin/payroll/deleteSalary/${currentRecord.employeeCTCId}">


													<i class="fa fa-trash" aria-hidden="true"></i>
											</a> &nbsp;&nbsp;</td>
											<td><a class="btn btn-danger"
												href="${pageContext.request.contextPath}/admin/payroll/salarySlip/${currentRecord.employeeCTCId}">Salary
													Slip </a></td>
										</tr>
									</c:forEach>
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
<
<script type="text/javascript">
	$("#salaryDownload").click(function() {
		var date = $("#salaryId").val()
		//var url = $(this).attr('data-url');
		//$("#salaryDownload").attr('href',k+"/"+j)
		//alert(k + "/" + j);
		//data-url="${pageContext.request.contextPath}/admin/payroll/salaryDownload"
		//console.log(url+'/'+salaryId);
		$(this).attr('href', "/admin/payroll/salaryDownload"+ "/" + date)
	})
</script>

</body>
</html>
