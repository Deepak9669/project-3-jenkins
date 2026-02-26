<%@page import="in.co.rays.project_3.controller.JobCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.JobDTO"%>
<%@page import="java.util.HashMap"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Job View</title>

<style type="text/css">
.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/bg3.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>
</head>

<body class="hm">

	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>

	<main>
	<form action="<%=ORSView.JOB_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.JobDTO"
			scope="request"></jsp:useBean>

		<%
			HashMap statusMap = (HashMap) request.getAttribute("statusMap");
		%>

		<div class="row pt-3">

			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card input-group-addon">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Job</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Job</h3>
						<%
							}
						%>

						<!-- Success Message -->
						<h4 align="center">
							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success alert-dismissible">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<%=ServletUtility.getSuccessMessage(request)%>
							</div>
							<%
								}
							%>
						</h4>

						<!-- Error Message -->
						<h4 align="center">
							<%
								if (!ServletUtility.getErrorMessage(request).equals("")) {
							%>
							<div class="alert alert-danger alert-dismissible">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<%=ServletUtility.getErrorMessage(request)%>
							</div>
							<%
								}
							%>
						</h4>

						<!-- Hidden Fields -->
						<input type="hidden" name="id" value="<%=dto.getId()%>">
						<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
						<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
						<input type="hidden" name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<div class="md-form">

							<!-- Title -->
							<span class="pl-sm-5"><b>Title</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="title"
									placeholder="enter title"
									value="<%=DataUtility.getStringData(dto.getTitle())%>">
							</div>
							<font color="red" class="pl-sm-5">
								<%=ServletUtility.getErrorMessage("title", request)%>
							</font><br>

							<!-- DOB -->
							<span class="pl-sm-5"><b>DOB</b></span><br>
							<div class="col-sm-12">
								<input type="date" class="form-control" name="dob"
									
									value="<%=DataUtility.getDateString(dto.getDob())%>">
							</div>
							<br>

							<!-- Experience -->
							<span class="pl-sm-5"><b>Experience</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="experience"
									placeholder="enter experience"
									value="<%=DataUtility.getStringData(dto.getExperience())%>">
							</div>
							<font color="red" class="pl-sm-5">
								<%=ServletUtility.getErrorMessage("experience", request)%>
							</font><br>

							<!-- Status Dropdown -->
							<span class="pl-sm-5"><b>Status</b> *</span><br>
							<div class="col-sm-12">
								<select name="status" class="form-control">
									<option value="">--Select Status--</option>
									<%
										if (statusMap != null) {
											for (Object key : statusMap.keySet()) {
												String val = (String) key;
									%>
									<option value="<%=val%>"
										<%=(val.equals(dto.getStatus())) ? "selected" : ""%>>
										<%=val%>
									</option>
									<%
											}
										}
									%>
								</select>
							</div>
							<font color="red" class="pl-sm-5">
								<%=ServletUtility.getErrorMessage("status", request)%>
							</font><br>

							<!-- Buttons -->
							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=JobCtl.OP_UPDATE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=JobCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=JobCtl.OP_SAVE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=JobCtl.OP_RESET%>">
							</div>
							<%
								}
							%>

						</div>

					</div>
				</div>
			</div>

			<div class="col-md-4"></div>

		</div>

	</form>
	</main>

</body>

<%@include file="FooterView.jsp"%>
</html>