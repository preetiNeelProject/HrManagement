
<script src="${pageContext.request.contextPath}/staticResources/JavaScript/addEmployee.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<div class="container-fluid">
<div class="row">
<div class="col-lg-1 col-sm-12" 
   ><jsp:include page="../commonView/mainNavbar.jsp" /></div>
		<div class="col-lg-11 col-sm-12">
			<div class="col-lg-11 col-sm-12"
				style="width: 100%; padding-right: 0px;"><jsp:include
					page="../commonView/header.jsp" /></div>
			<div class="col-lg-11 col-sm-12" style="width: 100%;"><jsp:include
					page="../commonView/profileNav.jsp" /></div>

			<div class="col-lg-11 col-sm-12">

				<div class="" id="grad1">
					<div class="row justify-content-center mt-0">Employee Dashboard...</div>
				</div>
			</div>

		</div>
	</div>
</div>


</body>
</html>
<script>
	
</script>