<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script
	src="${pageContext.request.contextPath}/staticResources/JavaScript/addEmployee.js"></script>
	
	<script src="${pageContext.request.contextPath}/staticResources/JavaScript/jquery-3.5.1.min.js" ></script>
	<script type="text/javascript"
	src="/staticResources/JavaScript/formSteps_addEmployee.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
input[type=checkbox] {
	height: 13px !important;
	width: 4% !important;
}

.modal-body{
    padding: 2%;

}
/* margin-right: 5px !important; */
</style>
<head>

</head>


<div class="modal" id="myModal">
    <div class="modal-content " style="width:30%">
        <div class="modal-body" id="modalBody" style="text-align: center;">                             

        </div>
    
    </div>

</div>


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
				<!-- MultiStep Form -->
				<div class="" id="grad1">
					<div class="row justify-content-center mt-0">
						<div class="col-1 col-md-1 col-sm-2 col-lg-1"></div>
						<div
							class="col-10 col-sm-10 col-md-9 col-lg-10 text-center p-0 mt-3 mb-2">
							<div class="card px-0 pt-4 pb-0 mt-3 mb-3">
								<h2>
									<strong>Add New Employee</strong>
								</h2>
								<p>Fill all form field to go to next step</p>
								<div class="row">
									<div class="col-md-12 mx-0">
										<form id="msform" class="allform" name="msform"
											action="${pageContext.request.contextPath}/admin/company/add_Employee"
											method="POST">
											<!-- progressbar -->
											<ul id="progressbar" style="margin-left: 25%;">
												<li class="active" id="personal"><strong>Personal</strong></li>
												<li id="work"><strong>Work</strong></li>
                                                <li id="confirm"><strong>Permission</strong></li>
												<li id="account"><strong>Account Details</strong></li>
												<!-- <li id="confirm"><strong>Finish</strong></li> -->
											</ul>
											<!-- fieldsets -->
											<fieldset>
												<div class="form-card">
													<div class="col-sm-12" style="margin: 50px 15px;">
														<div class="row">
															<div class="col-sm-3">
																<h4 class="fs-title">Personal</h4>
															</div>
															<div class="col-sm-9">
																<div class="form-group" style="padding: 0px;">
																	<label>Person Title:</label>
																	<div>
																		<select name="personTitle" id="personTitle">
																			<option value="Mr.">Mr.</option>
																			<option value="Mrs.">Mrs.</option>
																			<option value="Miss">Miss</option>
																		</select><br />
																	</div>
																</div>
																<div class="form-group col-sm-4" style="padding: 0px;">
																
																	<label>First Name:</label>
																	<div>
																		<input type="text" required="required" name="firstName" id="firstName"/>
																		<!-- <label>Name Cannot be Empty</label> -->
																	</div>
																</div>
																<div class="form-group col-sm-4" style="padding: 0px;">
																	<label>Middle Name:</label>
																	<div>
																		<input type="text" name="middleName" id="middleName"
																			th:field="*{middleName}" />
																	</div>
																</div>
																<div class="form-group col-sm-4" style="padding: 0px;">
																	<label>Last Name:</label>
																	<div>
																		<input type="text" required="required" name="lastName" id="lastName"
																			th:field="*{lastName}" />
																	</div>
																</div>
																<div class="form-group col-sm-4" style="padding: 0px;">
																	<label>Gender:</label>
																	<div>
																		<select name="gender" id="gender">
																			<option value="Male">Male</option>
																			<option value="Female">Female</option>
																			<option value="Other">Other</option>
																		</select><br />
																	</div>
																</div>
																
																
																<div class="form-group col-sm-4" style="padding: 0px;">
																<label>Allowed Leaves</label>
																<input type="number" min="0" max="12" id="allowedLeaves" name="allowedLeaves">
															</div>
																
															</div>
														</div>
														<div class="col-sm-12" style="margin: 20px 0px;">
															<div class="row">
																<div class="col-sm-3">
																	<h4 class="fs-title">Contact</h4>
																</div>
																<div class="col-sm-9">
																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>Primary Email:</label>
																		<div>
																			<input type="text" name="email1" id="email1"
																				th:field="*{email1}" />
																		</div>
																	</div>
																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>Primary Phone:</label>
																		<div>
																			<input type="text" name="phone1" id="phone1"
																				th:field="*{phone1}" />
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<div class="col-sm-12" style="margin: 20px 0px;">
															<div class="row">
																<div class="col-sm-3">
																	<h4 class="fs-title">Address</h4>
																</div>
																<div class="col-sm-9">
																	<div class="form-group col-sm-12" style="padding: 0px;">
																		<label>Address(Permanent):</label>
																		<div>
																			<textarea rows="3" cols="2" maxlength="50"
																				name="address" id="address" th:field="*{address}"
																				style="height: 70px"></textarea>
																		</div>
																	</div>
																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>State:</label>
																		<div>
																			<select name="stateId" id="stateId"
																				onchange="getCityByState(this.value)">
																				<c:forEach items="${states}" var="state">
																				
																					<option value="${state.getId()}">${state.getState()}</option>
																				</c:forEach>

																			</select>
																		</div>
																	</div>
																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>City:</label>
																		<div>
																			<select name="cityData" id="cityData">

																				<option value=""></option>

																			</select>
																			
																			
																		</div>
																	</div>
																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>Country:</label>
																		<div>
																		<select name="countryId" id="countryId">
																		  <option value="1">India</option>
																		</select>																
																		</div>
																	</div>
																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>Pincode:</label>
																		<div>
																			<input type="text" name="pincode" id="pincode"
																				th:field="*{pincode}" /><br />
																		</div>
																	</div>
																	<div class="form-group col-sm-12" style="padding: 0px;">
																		<div>
																			<input type="hidden" name="addressType"
																				id="addressType" th:field="*{addressType}" /><br />
																		</div>
																	</div>
													

																</div>
															</div>
														</div>
													</div>

												</div>
												<input type="button" name="next" class="next action-button " 
													value="Next Step"  data-form="1"/>
											</fieldset>
											<!-- 	End Employee Personal Information -->

											<!-- 	Start Employee Bank Details Information -->

											<fieldset>
												<div class="form-card">
													<div class="col-sm-12" style="margin: 50px 15px;">
														<div class="row">
															<div class="col-sm-3">
																<h4 class="fs-title">BASIC INFO</h4>
															</div>
															<div class="col-sm-9">
																<div class="form-group col-sm-8" style="padding: 0px;">
																	<label>Logon-Id(Official):</label>
																	<div>
																		<input type="text" name="logonId" id="logonId"
																			th:field="*{logonId}"  oninput="checkDuplicateLogonId()"/>
																	</div>
																</div>
																<div class="form-group col-sm-4" style="padding: 0px;">
																	<label>Logon-Password(Official):</label>
																	<div>
																		<input type="text" name="logonPassword"
																			id="logonPassword" th:field="*{logonPassword}" />
																	</div>
																</div>
																<div class="form-group col-sm-4" style="padding: 0px;">
																	<label>Employee Code:</label>
																	<div>
																		<input type="text" name="employeeCode"
																			id="employeeCode" th:field="*{employeeCode}" />
																	</div>
																</div>
																<div class="form-group col-sm-4" style="padding: 0px;">
																	<label>Employee Type:</label>
																	<div>
																		<select name="employeeTypeId" id="employeeTypeId"
																			th:field="*{employeeTypeId}">
																			<c:forEach items="${empType}" var="empType">
      																  <option value="${empType.employeeTypeId}">${empType.typeName}</option>
  																  </c:forEach>
																		</select><br />
																	</div>
																</div>
																<div class="form-group col-sm-4" style="padding: 0px;">
																	<label>Employee Status:</label>
																	<div>
																		<select name="employeeStatus" id="employeeStatus">
																			<option value="Active">Active</option>
																			<option value="Inactive">Inactive</option>
																		</select>
																	</div>
																</div>
															
																<div class="form-group col-sm-4" style="padding: 0px;">
																	<label>Work Experience:</label>
																	<div>
																		<input type="text" name="experience" id="experience"
																			style="width: 25%; margin-right: 2%;"
																			th:field="*{experience}" />Year
																		<!-- <input type="text"
																	style="width: 25%; margin-right: 2%;" name="experience"
																	th:field="*{experience}" />Month -->
																	</div>
																</div>
																<div class="form-group col-sm-4" style="padding: 0px;">
																	<label>Probation Period:</label>
																	<div>
																		<select name="probationPeriod" id="probationPeriod">
																			<option value="0">0</option>
																			<option value="15">15</option>
																			<option value="30">30</option>
																			<option value="45">45</option>
																			<option value="60">60</option>
																			<option value="75">75</option>
																			<option value="90">90</option>
																			<option value="120">120</option>
																			<option value="150">150</option>
																			<option value="180">180</option>
																		</select><br />
																	</div>
																</div>

															</div>
														</div>
														<div class="col-sm-12" style="margin: 20px 0px;">
															<div class="row">
																<div class="col-sm-3">
																	<h4 class="fs-title">Role</h4>
																</div>
																<div class="col-sm-9">
																	<div class="form-group col-sm-12" style="padding: 0px;">
																		<label>Role:</label>
																		<div>
																			<select name="roles" id="roles"
																				onchange="setDepartment();">
																				<option value="0">Select Role</option>
																				<c:forEach items="${role}" var="role">
      																  <option value="${role.roleName}">${role.roleName}
																					</option>
  																  </c:forEach>
																			</select><br/>
																		</div>
																	</div>
																	<div class="form-group col-sm-12" style="padding: 0px;">
																		<label>Work Location:</label>
																		<div>

																			<select name="workLocationId" id="workLocationId"
																				onChange="department();">
																				<option value="0">----------Select---------</option>
																				<c:forEach items="${workLocation}"
																					var="workLocation">
      																           <option id="workLocation"
																						value="${workLocation.workLocationId}">${workLocation.addressTitle}
																					</option>
  																  </c:forEach>
																			</select><br />

																		</div>
																	</div>
																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>Department:</label>
																		<div>
																			<select name="departmentId" id="departmentId"
																				multiple>
																				<c:forEach items="${department}" var="department">
      																  <option value="${department.departmentId}">${department.deptName}
																					</option>
  																  </c:forEach>
																			</select><br />
																			<br />
																		</div>
																	</div>
																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>Manager:</label>
																		<div>
																			<input type="text" name="manager" id="manager"
																				th:field="*{manager}" />
																		</div>
																	</div>
																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>Designation:</label>
																		<div>
																			<input type="text" name="designation"
																				id="designation" th:field="*{designation}" />
																		</div>
																	</div>
																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>Job Title:</label>
																		<div>
																			<select name="jobTitleId" id="jobTitleId">
																				<c:forEach items="${jobTitle}" var="jobTitle">
      																  <option value="${jobTitle.jobTitleId}">${jobTitle.jobTitle}</option>
  																  </c:forEach>
																			</select><br />
																		</div>
																	</div>


																	<div class="form-group col-sm-6" style="padding: 0px;">
																		<label>Project:</label>
																		<div>
																			<select name="projectId" id="projectId" multiple>
																				<c:forEach items="${project}" var="project">
      																  <option value="${project.projectId}">${project.projectCode}</option>
  																  </c:forEach>
																			</select><br />
																		</div>
																	</div>

																	<!-- <div class="form-group col-sm-6" style="padding: 0px;">
																<label>Sub Department:</label>
																<div>
																	<input type="text" name="" th:field="*{}" />
																</div>
															</div> -->
																</div>
															</div>
														</div>
													</div>
												</div>
												<input type="button" name="previous"
													class="previous action-button-previous" value="Previous" />
												<input type="button" name="next" class="next action-button"
													value="Next Step" data-form="2" />
											</fieldset>

											<!-- 	End Employee Work Information -->



																						<!-- 	End Role Information -->
											<fieldset>
												<div class="form-card">
													<div class="col-sm-12" style="margin: 50px 15px 0px;">
														<div class="row">
															<div class="col-sm-12">
															<div class="form-group">
																<label for="allPermit" class="fs-title"> All Permissions</label>
													<input type="checkbox"  id="allPermit" onclick="enablePermitAll()" >
															</div>
															
																<h4 class="fs-title">Company</h4>
															</div>
															<div class="col-sm-12">
															
																<div class="form-group col-sm-12" style="padding: 0px;">
																	<label>Company(Branch):</label>
																	<div>
																		<input type="checkbox" name="addBranch" id="addBranch" />Add
																		&nbsp;&nbsp; <input type="checkbox" name="viewBranch"
																			id="viewBranch" />View &nbsp;&nbsp; <input
																			type="checkbox" name="editBranch" id="editBranch" />Edit
																		&nbsp;&nbsp; <input type="checkbox"
																			name="deleteBranch" id="deleteBranch" />Delete
																		&nbsp;&nbsp;

																	</div>
																	<br> <label>Department:</label>
																	<div>
																		<input type="checkbox" name="addDepartment"
																			id="addDepartment" />Add &nbsp;&nbsp; <input
																			type="checkbox" name="viewDepartment"
																			id="viewDepartment" />View &nbsp;&nbsp; <input
																			type="checkbox" name="editDepartment"
																			id="editDepartment" />Edit &nbsp;&nbsp; <input
																			type="checkbox" name="deleteDepartment"
																			id="deleteDepartment" />Delete &nbsp;&nbsp;

																	</div>
																	<br> <label>Job-Title:</label>
																	<div>
																		<input type="checkbox" name="addJobTitle"
																			id="addJobTitle" />Add &nbsp;&nbsp; <input
																			type="checkbox" name="viewJobTitle" id="viewJobTitle" />View
																		&nbsp;&nbsp; <input type="checkbox"
																			name="editJobTitle" id="editJobTitle" />Edit
																		&nbsp;&nbsp; <input type="checkbox"
																			name="deleteJobTitle" id="deleteJobTitle" />Delete
																		&nbsp;&nbsp;

																	</div>
																	<br> <label>Announcement:</label>
																	<div>
																		<input type="checkbox" name="addAnnouncement"
																			id="addAnnouncement" />Add &nbsp;&nbsp; <input
																			type="checkbox" name="viewAnnouncement"
																			id="viewAnnouncement" />View &nbsp;&nbsp; <input
																			type="checkbox" name="editAnnouncement"
																			id="editAnnouncement" />Edit &nbsp;&nbsp; <input
																			type="checkbox" name="deleteAnnouncement"
																			id="deleteAnnouncement" />Delete &nbsp;&nbsp;

																	</div>
																	<br> <label>Policies:</label>
																	<div>
																		<input type="checkbox" name="addPolicies"
																			id="addPolicies" />Add &nbsp;&nbsp; <input
																			type="checkbox" name="viewPolicies" id="viewPolicies" />View
																		&nbsp;&nbsp; <input type="checkbox"
																			name="editPolicies" id="editPolicies" />Edit
																		&nbsp;&nbsp; <input type="checkbox"
																			name="deletePolicies" id="deletePolicies" />Delete
																		&nbsp;&nbsp; <input type="checkbox"
																			name="uploadPolicies" id="uploadPolicies" />Upload
																		&nbsp;&nbsp; <input type="checkbox"
																			name="downloadPolicies" id="downloadPolicies" />Download
																		&nbsp;&nbsp;

																	</div>
																	<br>
																</div>



															</div>
														</div>
													</div>
													<div class="col-sm-12" style="margin: 0px 15px;">
														<div class="row">
															<div class="col-sm-3">
																<h4 class="fs-title">Directory</h4>
															</div>
															<div class="col-sm-12">
																<div class="form-group col-sm-12" style="padding: 0px;">
																	<label>Employee:</label>
																	<div>
																		<input type="checkbox" name="addEmployee"
																			id="addEmployee" />Add &nbsp;&nbsp; <input
																			type="checkbox" name="viewEmployee" id="viewEmployee" />View
																		&nbsp;&nbsp; <input type="checkbox"
																			name="editEmployee" id="editEmployee" />Edit
																		&nbsp;&nbsp; <input type="checkbox"
																			name="deleteEmployee" id="deleteEmployee" />Delete
																		&nbsp;&nbsp; <input type="checkbox"
																			name="uploadEmployee" id="uploadEmployee" />Upload
																		&nbsp;&nbsp; <input type="checkbox"
																			name="downloadEmployee" id="downloadEmployee" />Download
																		&nbsp;&nbsp;

																	</div>
																	<br>

																</div>



															</div>
														</div>
													</div>
													<div class="col-sm-12" style="margin: 0px 15px;">
														<div class="row">
															<div class="col-sm-3">
																<h4 class="fs-title">Attendance</h4>
															</div>
															<div class="col-sm-12">
																<div class="form-group col-sm-12" style="padding: 0px;">
																	<label>Attendance:</label>
																	<div>
																		<input type="checkbox" name="addAttendance"
																			id="addAttendance" />Add &nbsp;&nbsp; <input
																			type="checkbox" name="viewAttendance"
																			id="viewAttendance" />View &nbsp;&nbsp; <input
																			type="checkbox" name="editAttendance"
																			id="editAttendance" />Edit &nbsp;&nbsp; <input
																			type="checkbox" name="deleteAttendance"
																			id="deleteAttendance" />Delete &nbsp;&nbsp; <input
																			type="checkbox" name="uploadAttendance"
																			id="uploadAttendance" />Upload &nbsp;&nbsp; <input
																			type="checkbox" name="downloadAttendance"
																			id="downloadAttendance" />Download &nbsp;&nbsp; <input
																			type="checkbox" name="approvelAttendance"
																			id="approvelAttendance" />Approvel &nbsp;&nbsp; <input
																			type="checkbox" name="setRulesAttendance"
																			id="setRulesAttendance" />Set Rules &nbsp;&nbsp; <input
																			type="checkbox" name="settingAttendance"
																			id="settingAttendance" />Setting &nbsp;&nbsp;
																	</div>
																	<br>
																</div>



															</div>
														</div>
													</div>
													<div class="col-sm-12" style="margin: 0px 15px;">
														<div class="row">
															<div class="col-sm-3">
																<h4 class="fs-title">Leave</h4>
															</div>
															<div class="col-sm-12">
																<div class="form-group col-sm-12" style="padding: 0px;">
																	<label>Leave:</label>
																	<div>
																		<input type="checkbox" name="addLeave" id="addLeave" />Add
																		&nbsp;&nbsp; <input type="checkbox" name="viewLeave"
																			id="viewLeave" />View &nbsp;&nbsp; <input
																			type="checkbox" name="editLeave" id="editLeave" />Edit
																		&nbsp;&nbsp; <input type="checkbox" name="deleteLeave"
																			id="deleteLeave" />Delete &nbsp;&nbsp; <input
																			type="checkbox" name="uploadLeave" id="uploadLeave" />Upload
																		&nbsp;&nbsp; <input type="checkbox"
																			name="downloadLeave" id="downloadLeave" />Download
																		&nbsp;&nbsp; <input type="checkbox"
																			name="approvelLeave" id="approvelLeave" />Approvel
																		&nbsp;&nbsp; <input type="checkbox"
																			name="balanceLeave" id="balanceLeave" />Balance
																		&nbsp;&nbsp; <input type="checkbox"
																			name="setRulesLeave" id="setRulesLeave" />Set Rules
																		&nbsp;&nbsp; <input type="checkbox"
																			name="settingLeave" id="settingLeave" />Setting
																		&nbsp;&nbsp;
																	</div>
																	<br>
																</div>



															</div>
														</div>
													</div>
													<div class="col-sm-12" style="margin: 0px 15px;">
														<div class="row">
															<div class="col-sm-3">
																<h4 class="fs-title">Payroll</h4>
															</div>
															<div class="col-sm-12">
																<div class="form-group col-sm-12" style="padding: 0px;">
																	<label>Run Payroll:</label>
																	<div>
																		<input type="checkbox" name="addRunPayroll"
																			id="addRunPayroll" />Add &nbsp;&nbsp; <input
																			type="checkbox" name="viewRunPayroll"
																			id="viewRunPayroll" />View &nbsp;&nbsp; <input
																			type="checkbox" name="editRunPayroll"
																			id="editRunPayroll" />Edit &nbsp;&nbsp; <input
																			type="checkbox" name="deleteRunPayroll"
																			id="deleteRunPayroll" />Delete &nbsp;&nbsp; <input
																			type="checkbox" name="uploadRunPayroll"
																			id="uploadRunPayroll" />Upload &nbsp;&nbsp; <input
																			type="checkbox" name="downloadRunPayroll"
																			id="downloadRunPayroll" />Download &nbsp;&nbsp; <input
																			type="checkbox" name="approvelRunPayroll"
																			id="approvelRunPayroll" />Approvel &nbsp;&nbsp; <input
																			type="checkbox" name="balanceRunPayroll"
																			id="balanceRunPayroll" />Balance &nbsp;&nbsp; <input
																			type="checkbox" name="setRulesRunPayroll"
																			id="setRulesRunPayroll" />Set Rules &nbsp;&nbsp; <input
																			type="checkbox" name="settingRunPayroll"
																			id="settingRunPayroll" />Setting &nbsp;&nbsp;
																	</div>
																	<br> <label>Salary Structure:</label>
																	<div>
																		<input type="checkbox" name="addSalaryStructure"
																			id="addSalaryStructure" />Create &nbsp;&nbsp; <input
																			type="checkbox" name="assignSalaryStructure"
																			id="assignSalaryStructure" />Assign &nbsp;&nbsp; <input
																			type="checkbox" name="viewSalaryStructure"
																			id="viewSalaryStructure" />View &nbsp;&nbsp; <input
																			type="checkbox" name="editSalaryStructure"
																			id="editSalaryStructure" />Edit &nbsp;&nbsp; <input
																			type="checkbox" name="deleteSalaryStructure"
																			id="deleteSalaryStructure" />Delete &nbsp;&nbsp; <input
																			type="checkbox" name="uploadSalaryStructure"
																			id="uploadSalaryStructure" />Upload &nbsp;&nbsp; <input
																			type="checkbox" name="downloadSalaryStructure"
																			id="downloadSalaryStructure" />Download &nbsp;&nbsp;
																		<input type="checkbox" name="approvelSalaryStructure"
																			id="approvelSalaryStructure" />Approvel &nbsp;&nbsp;
																	</div>
																	<br> <label>Salary Component:</label>
																	<div>
																		<input type="checkbox" name="addSalaryComponent"
																			id="addSalaryComponent" />Create &nbsp;&nbsp; <input
																			type="checkbox" name="viewSalaryComponent"
																			id="viewSalaryComponent" />View &nbsp;&nbsp; <input
																			type="checkbox" name="editSalaryComponent"
																			id="editSalaryComponent" />Edit &nbsp;&nbsp; <input
																			type="checkbox" name="deleteSalaryComponent"
																			id="deleteSalaryComponent" />Delete &nbsp;&nbsp;
																	</div>
																	<br> <label>Salary Overview:</label>
																	<div>
																		<input type="checkbox" name="addSalaryOverview"
																			id="addSalaryOverview" />Create &nbsp;&nbsp; <input
																			type="checkbox" name="viewSalaryOverview"
																			id="viewSalaryOverview" />View &nbsp;&nbsp; <input
																			type="checkbox" name="editSalaryOverview"
																			id="editSalaryOverview" />Edit &nbsp;&nbsp; <input
																			type="checkbox" name="deleteSalaryOverview"
																			id="deleteSalaryOverview" />Delete &nbsp;&nbsp; <input
																			type="checkbox" name="uploadSalaryOverview"
																			id="uploadSalaryOverview" />Upload &nbsp;&nbsp; <input
																			type="checkbox" name="downloadSalaryOverview"
																			id="downloadSalaryOverview" />Download &nbsp;&nbsp;
																		<input type="checkbox" name="approvelSalaryOverview"
																			id="approvelSalaryOverview" />Approvel &nbsp;&nbsp;
																	</div>
																	<br> <label>Declaration:</label>
																	<div>
																		<input type="checkbox" name="addDeclaration"
																			id="addDeclaration" />Create &nbsp;&nbsp; <input
																			type="checkbox" name="viewDeclaration"
																			id="viewDeclaration" />View &nbsp;&nbsp; <input
																			type="checkbox" name="editDeclaration"
																			id="editDeclaration" />Edit &nbsp;&nbsp; <input
																			type="checkbox" name="deleteDeclaration"
																			id="deleteDeclaration" />Delete &nbsp;&nbsp; <input
																			type="checkbox" name="uploadDeclaration"
																			id="uploadDeclaration" />Upload &nbsp;&nbsp; <input
																			type="checkbox" name="downloadDeclaration"
																			id="downloadDeclaration" />Download &nbsp;&nbsp; <input
																			type="checkbox" name="approvelDeclaration"
																			id="approvelDeclaration" />Approvel &nbsp;&nbsp;
																	</div>
																	<br>
																</div>



															</div>
														</div>
													</div>
												</div>

												<input type="button" name="previous"
													class="previous action-button-previous" value="Previous" />
												<input type="button" name="next" class="next action-button"
													value="Next Step"  data-form="3"/>
											</fieldset>
											<!-- 	End Role Information -->
											<!-- 	Start Employee Work Information -->

											<fieldset>
												<div class="form-card">
													<div class="col-sm-12" style="margin: 50px 15px;">
														<div class="row">
															<div class="col-sm-3">
																<h4 class="fs-title">Bank Details</h4>
															</div>
															<div class="col-sm-9">
																<div class="form-group col-sm-12" style="padding: 0px;">
																	<label>CTC</label>
																	<div>
																		<input type="text" name="ctc" id="ctc"
																			th:field="*{ctc}" />
																	</div>
																</div>
																<div class="form-group col-sm-12" style="padding: 0px;">
																	<label>Account Holder's Name:</label>
																	<div>
																		<input type="text" name="holderName" id="holderName"
																			th:field="*{holderName}" />
																	</div>
																</div>
																<div class="form-group col-sm-6" style="padding: 0px;">
																	<label>Bank Name:</label>
																	<div>
																		<input type="text" name="bankName" id="bankName"
																			th:field="*{bankName}" />
																	</div>
																</div>
																<div class="form-group col-sm-6" style="padding: 0px;">
																	<label>City:</label>
																	<div>
																		<input type="text" name="city" id="city"
																			th:field="*{city}" />
																	</div>
																</div>
																<div class="form-group col-sm-6" style="padding: 0px;">
																	<label>Branch Name:</label>
																	<div>
																		<input type="text" name="branchName" id="branchName"
																			th:field="*{branchName}" />
																	</div>
																</div>
																<div class="form-group col-sm-6" style="padding: 0px;">
																	<label>IFSC Code:</label>
																	<div>
																		<input type="text" name="ifscCode" id="ifscCode"
																			th:field="*{ifscCode}" />
																	</div>
																</div>
																<div class="form-group col-sm-12" style="padding: 0px;">
																	<label>Account No:</label>
																	<div>
																		<input type="text" name="accountNumber"
																			id="accountNumber" th:field="*{accountNumber}" />
																	</div>
																</div>
																<div class="form-group col-sm-6" style="padding: 0px;">
																	<label>Deducation</label>
																</div>
																<div class="form-group col-sm-3" style="padding: 0px;">
																	<label>Pf:</label> <input type="checkbox" name="pf"
																		id="pf" th:field="pf" style="width: 50% !important;" />
																</div>
																<div class="form-group col-sm-3" style="padding: 0px;">
																	<label>Esi:</label> <input type="checkbox" name="esi"
																		id="esi" style="width: 50% !important;"
																		th:field="*{esi}" />
																</div>
															</div>
														</div>
													</div>
												</div>
												<input type="button" name="previous"
													class="previous action-button-previous" value="Previous" />
												<input type="button" name="submit" value="Submit"
													onclick="submitForm();" data-form="4" class="next action-button"
													 />
											</fieldset>

											<!-- 	End Employee Bank Details Information -->
											<fieldset>
												<div class="form-card">
													<h2 class="fs-title text-center">Success !</h2>
													<br> <br>
													<div class="row justify-content-center">
														<div class="col-3">
															<img
																src="https://img.icons8.com/color/96/000000/ok--v2.png"
																style="width: 50%; height: 50%" class="fit-image">
														</div>
													</div>
													<br> <br>
													<div class="row justify-content-center">
														<div class="col-7 text-center">
															<h5>You Have Successfully Add Employee</h5>
														</div>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>



</body>
</html>
<script>

function check(){
	 /* var firstName = document.forms["msform"]["firstName"].value;
	if(firstName==''){
		
		  $("[name='previous']")[0].false;
		}  */
}

	function setDepartment() {
		var roleId = document.getElementById('roleId').value;
		var departmentId = document.getElementById('departmentId');
		if (roleId == '1') {
			var checkboxes = document
					.querySelectorAll('input[type="checkbox"]');
			for (var i = 0, n = checkboxes.length; i < n; i++) {
				checkboxes[i].checked = true;
			}
			for (var i = 0; i < departmentId.length; i++)
				departmentId.options[i].selected = true;
		} else
			for (var i = 0; i < departmentId.length; i++)
				departmentId.options[i].selected = false;
	}

	function setdepartment() {
		alert("================");
		/* var workLocation = $("#workLocation").val();
		
		$.ajax({
		    type: "POST",
		    contentType: "application/json",
		    url: "/admin/company/department",
		    data: workLocation,
		    dataType: 'json',
		    cache: false,
		    success : function(data) {
		        console.log("success : "+data);
		        var department=$('#department'), option="";
		        for(var i=0; i<data.length; i++){
		            option = option + "<option value='"+data[i].departmentId + "'>"+data[i].deptName + "</option>";
		        }
		        department.append(option);
		    },
		    
			 error : function(error) {
				
		     }

		}); */
	};
	
</script>