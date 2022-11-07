

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <jsp:include page="../commonView/header.jsp" /> --%>

<style>
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

</style>

<div class="base-bar" style="">
	<div class="navBody"
		style="border-radius: 5px;" class="bg-primary">
			
		<div id="on_attendance">
			<!--   Start Profile Links -->
			<nav class="navbar navbar-expand-lg bg-primary" 
				style=" color: white !important">
				<ul class="nav navbar-nav">
					<li class="nav-item active"><a class="nav-link"
						href="${pageContext.request.contextPath}/admin/attendance/dashboard">Overview
					</a></li>
					<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/attendance/viewAttendanceLog">Attendance Logs</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Approvals</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Rules</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Analytics</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Setting</a></li>
				</ul>
			</nav>
			<!--   End Profile Links -->
		</div>
		
	</div>
</div>

