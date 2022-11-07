
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

<div class="container-fluid">


	<div class="row">
		<div class="col-lg-1 col-sm-12"><jsp:include
				page="../commonView/mainNavbar.jsp" /></div>
		<div class="col-lg-11 col-sm-12">
			 <div class="col-lg-11 col-sm-12"
				style="width: 100%; padding-right: 0px;"><jsp:include
					page="../commonView/header.jsp" /></div> 
			<div class="col-lg-11 col-sm-12" style="width: 100%;"><jsp:include
					page="../commonView/profileNav.jsp" /></div>

			<!-- ----------------------Heading-------------------- -->
			<div class="col-md-12" style="">
				<div class="row" style=";">
					<div class="col-sm-6">
						<div class="row">
							<div style="margin-left: 29px;">
								<h2>
									<b>Profile</b>
								</h2>
							</div>
						</div>
					</div>
					<div class="col-sm-6" id="addEmp">
						<div class="row">
							<ul style="float: right; margin-right: 10%;">
								<li>
								<!-- <button class="btn btn-danger" style="color: white;"
										onclick="location.href='../employee/editProfile/111'">
										Edit Profile</button> -->
									
										

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
					Profile is under constructor......
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
	var selDiv = "";

	document.addEventListener("DOMContentLoaded", init, false);

	function init() {
		document.querySelector('#files').addEventListener('change',
				handleFileSelect, false);
		selDiv = document.querySelector("#selectedFiles");
	}

	function handleFileSelect(e) {

		if (!e.target.files)
			return;

		selDiv.innerHTML = "";

		var files = e.target.files;
		for (var i = 0; i < files.length; i++) {
			var f = files[i];

			selDiv.innerHTML += f.name + "<br/>";

		}

	}
</script>