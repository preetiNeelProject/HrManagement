

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%-- <jsp:include page="../commonView/header.jsp" /> --%>
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
<div class="base-bar" style="">
	<div class="navBody" style="border-radius: 5px;" class="bg-primary">

		<div id="on_company">
			<!--  Start Company links -->
			<nav class="navbar navbar-expand-lg bg-primary"
				style="color: white !important">
				<ul class="nav navbar-nav">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/admin/company/dashboard">Overview
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/admin/company/workLocation">Address</a>
					</li>
					<sec:authorize access="hasAuthority('ADMIN')">
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/admin/company/department">Department</a></li>
						<li class="nav-item"><a class="nav-link disabled"
							href="${pageContext.request.contextPath}/admin/company/role">Designation</a></li>
					</sec:authorize>


					<li class="nav-item"><a class="nav-link disabled"
						href="${pageContext.request.contextPath}/admin/company/announcement">

							Announcement<span
							class=" badge rounded-pill btn-danger disabled">
                          ${totalAnnoucement}
						</span>

					</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Policies</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Admin</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Statutory</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">My
							Plan</a></li>
				</ul>
			</nav>
			<!--   End Company Links -->
		</div>

	</div>
</div>

