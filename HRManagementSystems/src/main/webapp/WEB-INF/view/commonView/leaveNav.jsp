

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
</style>
<div class="base-bar bg-primary" style="">
	<div class="navBody"
		style="border-radius: 5px; height: 75px; padding: 10px;">
	
		<div id="on_leave">
		<!--   Start leave Links -->
			<nav class="navbar navbar-expand-lg"
				style="background: transparent; color: white !important">
				<ul class="nav navbar-nav">
					<li class="nav-item active"><a class="nav-link"
						href="${pageContext.request.contextPath}/admin/leave/dashboard">Overview
					</a></li>
					<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/leave/viewLeaveLog">Leaves Logs</a>
					<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/leave/appliedLeaveLog">Applied Leaves</a></li>
					<li class="nav-item"><a class="nav-link" href="">My Team List</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="${pageContext.request.contextPath}/admin/leave/approvedLeaveLog">Leave Approved</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="${pageContext.request.contextPath}/admin/leave/rejectedLeaveLog">Leave Rejected</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Holiday</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Rules</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Balance</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Setting</a></li>
				</ul>
			</nav>
			<!--   End leave Links -->
		</div>
		
	</div>
</div>

