<script src="${pageContext.request.contextPath}/staticResources/JavaScript/SalaryStructure.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container-fluid">
	<div class="row">
		<div class="col-lg-1 col-sm-12" 
   ><jsp:include page="../../commonView/mainNavbar.jsp" /></div>
		<div class="col-lg-11 col-sm-12">
			<div class="col-lg-11 col-sm-12"
				style="width: 100%; padding-right: 0px;"><jsp:include
					page="../../commonView/header.jsp" /></div>
			<div class="col-lg-11 col-sm-12" style="width: 100%;"><jsp:include
					page="../../commonView/payrollNav.jsp" /></div>

			<div class="col-lg-11 col-sm-12">
				<!-- MultiStep Form -->
				<div class="" id="grad1">
						
		
			
				<div class="form-box">
					<center><h3>Salary Increment </h3></center>
					<hr>
					<div class="row">
					<div class="col-sm-5">Hi</div><div class="col-sm-7">
						<form class="form-horizontal" action="${pageRequest.request.contextPath}/admin/payroll/addEmployeeSalary" th:action="@{addEmployeeSalary}"
							method="POST" th:object="${emloyeeSalary}">
							<h4>Employee Details</h4>
							<div class="form-group">
								<label class="control-label col-sm-4">Employee-Id:</label>
								<div class="col-sm-8">
									<input type="number" id="employeeId" name="employeeId" th:field="*{employeeId}" /><br />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4">Full name:</label>
								<div class="col-sm-8">
									<input type="text" name="name" th:field="*{name}" /><br />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4">Increment Date:</label>
								<div class="col-sm-8">
									<input type="date" name="incrementDate" th:field="*{incrementDate}" /><br />
								</div>
							</div>
							<h4>Salary Details</h4>
							<div class="form-group">
								<label class="control-label col-sm-4">Old Salary:</label>
								<div class="col-sm-8">
									<input type="number" name="oldCtc" id="oldCtc"
										th:field="*{newCtc}" /><br />
								</div>
							</div>
							
							<h4>New Details</h4>
							<div class="form-group">
								<label class="control-label col-sm-4">New Salary:</label>
								<div class="col-sm-8" >
									<select style="width: 30%" id="salaryIncrementPercentage" onchange="setCtc()">
									<option value="0" >0%</option>
										<option value="10" >10%</option>
										<option  value="20" >20%</option>
										<option  value="30" >30%</option>
										<option  value="40" >40%</option>
										<option  value="50" >  50%</option>
										<option  value="60">60%</option>
										<option   value="70">70%</option>
										<option  value="80" >80%</option>
										<option   value="90" >90%</option>
										<option   value="100">100%</option>
									</select>
<input type="number" id="cTC" name="cTC" onchange="createEmpSalaryStructure();"
										
										style="width: 60%; float: right;"  th:field="*{cTC}" min="1000" /><br />
									<label for="cTC"
										style="float: right; color: red; margin-top: -7px;">CTC
										more than 999...</label>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4">Basic Salary:</label>
								<div class="col-sm-8">
									<input type="number" name="annualBasicSalary" id="annualBasicSalary"
										  /><br />
								</div>
							</div>
							<h4>Allowances</h4>							
							<div class="form-group">
								<label class="control-label col-sm-4">HRA:</label>
								<div class="col-sm-8">
									<input type="number" name="hra" id="hra" /><br />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4">Conveyance:</label>
								<div class="col-sm-8">
									<input type="number" name="convey" id="convey" /><br />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4">Other:</label>
								<div class="col-sm-8">
									<input type="number" name="annualFlexibleBenifits" id="annualFlexibleBenifits" /><br />
								</div>
							</div>
							<h4>Deductions</h4>
							<!-- <div class="form-group">
								<label class="control-label col-sm-4">TDS:</label>
								<div class="col-sm-8">
									<input type="number" id="tds" th:field="*{tds}" readonly="true"/><br />
								</div>
							</div> -->
							<div class="form-group">
								<label class="control-label col-sm-4">PF:</label>
								<div class="col-sm-8">
									<input type="number" name="pf" id="pf" /><br />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4">ESI:</label>
								<div class="col-sm-8">
									<input type="number" name="esi" id="esi"  /><br />
								</div>
							</div>
							
								<div class="form-group">
										<label class="control-label col-sm-4">Mediclaim</label>
										<div class="col-sm-8">
											<input type="number" name="mediclame" id="mediclame"
												onkeyup="totalCalculation();" />
										</div>
										
							<h5 ><b>Areas Remboursement</b></h5>
									<div class="from-group">
										<div class="col-sm-4">
											<label for="travelExpensive" class="control-label">
												Travel Expensis:</label>										</div>
										<div class="col-sm-8">
											<input type="text" class="form-control" name="travelExpensis"  onkeyup="totalCalculation();" id="travelExpensive">
										</div>
									</div>

									<div class="form-group">
										<div class="col-sm-4">
											<label class="control-label">Bonus:</label>
										</div>
										<div class="col-sm-8">
											<input type="text" class="form-control" onkeyup="totalCalculation();"   name="bonus" id="bonus">
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-sm-4">
											<label class="control-label">Incentives:</label>
										</div>
										<div class="col-sm-8">
											<input type="text" name="incentives" class="form-control" onkeyup="totalCalculation();" id="incentives">
										</div>
									</div>
									
								<div class="form-group">
										<div class="col-sm-4">
											<label class="control-label">TDS:</label>
										</div>
										<div class="col-sm-8">
											<input type="text" name="" class="form-control" onkeyup="totalCalculation();" id="tds">
										</div>
									</div>

                                  

                                    
									<div class="form-group">
										<label class="control-label col-sm-4"><h6>
												<b>Take Home Salary</b>
											</h6></label>
										<div class="col-sm-8">
											<input type="number" name="annualRefSalary"
												id="annualRefSalary" readonly="true" /><br />
										</div>
									</div>

							<div class="form-group">
								<label class="control-label col-sm-4">Comments:</label>
								<div class="col-sm-8">
									<textarea rows="3" cols="2" name="comments"  /></textarea><br />
								</div>
							</div>
							
							<button type="submit" class="btn btn-success" style="float:right;margin: 5% 3%;">Submit</button>
							<button type="reset" class="btn-cancel" style="float:right;margin: 5% 3%;">Cancel</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

				</div>
			</div>

		</div>
		
		
<script type="text/javascript">

function setCtc(){	
	var oldCtc=Number($("#oldCtc").val());
	var incrementPercenetage= Number( $("#salaryIncrementPercentage").val());
	var incrementSalary=Number((oldCtc*(incrementPercenetage/100)));
    $("#cTC").val(incrementSalary+oldCtc);
    createEmpSalaryStructure();
	
	
	
	
	

}


</script>
</body>
</html>