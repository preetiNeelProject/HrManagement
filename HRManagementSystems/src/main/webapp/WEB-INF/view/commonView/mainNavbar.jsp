
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="main-bar text-center">
	<div class="sidenav bg-primary">
		<a
			href="${pageContaddEmployeeext.request.contextPath}/commonView/dashboard"
			id="home"> <i class="fa fa-home" aria-hidden="true">
				<p>Home</p>
		</i>
		</a> <a href="${pageContext.request.contextPath}/admin/company/dashboard"
			id="company"> <i class="fa fa-building" aria-hidden="true">
				<p>Company</p>
		</i>
		</a> <a href="${pageContext.request.contextPath}/employee/dashboard"
			id="profile"> <i class="fa fa-user-circle-o" aria-hidden="true">
				<p>My Pofile</p>
		</i>
		</a> <a
			href="${pageContext.request.contextPath}/admin/attendance/dashboard"
			id="attendance"> <i class="fa fa-calendar" aria-hidden="true">
				<p>Attendance</p>
		</i>
		</a> <a
			href="${pageContext.request.contextPath}/admin/directory/dashboard"
			id="directory"> <i class="fa fa-sitemap" aria-hidden="true">
				<p>Directory</p>
		</i>
		</a>
		
		
		<sec:authorize access="hasAuthority('ADMIN')">
		  <a href="${pageContext.request.contextPath}/admin/leave/dashboard" id="leave"> 
		<i class="fa fa-taxi" aria-hidden="true">
				<p>Leave</p>
		</i>
		</a> 
		
		</sec:authorize>
		
		<sec:authorize access="hasAuthority('ADMIN')">
			<a href="${pageContext.request.contextPath}/admin/payroll/dashboard"
			id="payroll"> <i class="fa fa-money" aria-hidden="true">
				<p>Payroll</p>
		</i>
		</a>
		</sec:authorize>
		
	
		<%--
		<a href="${pageContext.request.contextPath}/admin/training/dashboard" id="training"> <i class="fa fa-codepen"
			aria-hidden="true">
				<p>Training</p>
		</i>
		</a>
		  
		<a href="/exit/dashboard" id="exit"> <i class="fa fa-times-circle-o"
			aria-hidden="true">
				<p>Exit</p>
		</i>
		</a> 
	
		<a href="../performance/dashboard" id="performance"> <i class="fa fa-trophy"
			aria-hidden="true">
				<p>Performance</p>
		</i>
		</a> <a href="../setting/dashboard" id="setting"> <i class="fa fa-cog" aria-hidden="true">
				<p>Setting</p>
		</i>
		</a>
		--%>

		<form action="/logout" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
			<input type="submit" onclick="this.form.submit()" class="btn btn-sm btn-danger" value="logout"/>			
		</form>

	</div>
</div>

<a ></a>

<style>
.sidenav {
	height: 100%;
	position: fixed;
	background-color: #387403;
	z-index: 1;
	top: 0;
	left: 0;
	overflow-x: hidden;
	padding-top: 10px;
	overflow-y: hidden;
}

.sidenav a {
	padding: 0px 8px 0px 16px;
	text-decoration: none;
	font-size: 25px;
	color: #818181;
	display: block;
	margin-bottom: 12%;
}

.sidenav a:hover {
	color: #f1f1f1;
}

.sidenav i {
	font-size: 30px;
	color: whitesmoke;
	text-align: center;
	margin: 2px auto;
}

.sidenav p {
	font-size: 10px;
	color: whitesmoke;
	line-height: 10px;
	text-align: center;
}

.main {
	margin-left: 160px; /* Same as the width of the sidenav */
	font-size: 28px; /* Increased text to enable scrolling */
	padding: 0px 10px;
}

@media screen and (max-height: 450px) {
	.sidenav {
		padding-top: 15px;
	}
	.sidenav a {
		font-size: 18px;
	}
}

@media screen {
}
</style>