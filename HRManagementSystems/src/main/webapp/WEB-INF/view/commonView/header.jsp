<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib  prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet"
	href="<c:url value='/staticResources/styleSheets/base.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/staticResources/styleSheets/bootstrap.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/staticResources/styleSheets/bootstrap-theme.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/staticResources/styleSheets/modelSheet.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/staticResources/styleSheets/msc-style.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/staticResources/styleSheets/hra-style.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/staticResources/styleSheets/formPopUp.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/staticResources/styleSheets/formSteps.css'/>" />

<link rel="stylesheet"
	href="<c:url value='/staticResources/styleSheets/font-awesome.min.css'/>" />


</head>
<body style="background: powderBlue;">
	<div class="container">

		<div class="row">
			<div class="col-lg-6 col-md-8">
				<p
					style="text-align: center; margin-top: 15px; font-size: 35px;  font-family: cambria math;">
					<b style="color: #283655;">&nbsp;&nbsp;<ins>H.R Management </ins></b> <span  class="h4" style="margin-left:3% "><ins> Neel Data Pro It Soltions</ins></span>
			</div>
		
			
			<div class="col-lg-6 col-md-8 text-right">
				<b><a href="#"  class="btn btn-sm text-white btn-light text-right font-weight-bold ">
				<b>
				<ins>
			
				  <sec:authentication property="principal.username"/>
				  <sec:authentication property="principal.authorities"/>
				  
				  </ins>
				 </b>
				</a>
				</b> 
			</div>
		</div>



	</div>
	</body>