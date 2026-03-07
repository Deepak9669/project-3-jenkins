<%@page import="in.co.rays.project_3.controller.BranchManagerCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.BranchManagerDTO"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Branch Manager View</title>

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
	</div>

	<main>
	<form action="<%=ORSView.BRANCH_MANAGER_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BranchManagerDTO"
			scope="request"></jsp:useBean>

		<div class="row pt-3">

			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card input-group-addon">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Branch Manager</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Branch Manager</h3>
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
						<input type="hidden" name="createdBy"
							value="<%=dto.getCreatedBy()%>"> 
						<input type="hidden" name="modifiedBy"
							value="<%=dto.getModifiedBy()%>"> 
						<input type="hidden" name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<div class="md-form">

							<!-- Manager Name -->
							<span class="pl-sm-5"><b>Manager Name</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="managerName"
									placeholder="Enter Manager Name"
									value="<%=DataUtility.getStringData(dto.getManagerName())%>">
							</div>
							<font color="red" class="pl-sm-5">
								<%=ServletUtility.getErrorMessage("managerName", request)%>
							</font><br>

							<!-- Branch Name -->
							<span class="pl-sm-5"><b>Branch Name</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="branchName"
									placeholder="Enter Branch Name"
									value="<%=DataUtility.getStringData(dto.getBranchName())%>">
							</div>
							<font color="red" class="pl-sm-5">
								<%=ServletUtility.getErrorMessage("branchName", request)%>
							</font><br>

							<!-- Contact Number -->
							<span class="pl-sm-5"><b>Contact Number</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="contactNumber"
									placeholder="Enter Contact Number"
									value="<%=DataUtility.getStringData(dto.getContactNumber())%>">
							</div>
							<font color="red" class="pl-sm-5">
								<%=ServletUtility.getErrorMessage("contactNumber", request)%>
							</font><br>

							<!-- Buttons -->
							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=BranchManagerCtl.OP_UPDATE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md"
									value="<%=BranchManagerCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=BranchManagerCtl.OP_SAVE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md"
									value="<%=BranchManagerCtl.OP_RESET%>">
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