<%@page import="in.co.rays.project_3.controller.AuditCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.AuditDTO"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Audit View</title>

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
	<form action="<%=ORSView.AUDIT_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.AuditDTO"
			scope="request"></jsp:useBean>

		<div class="row pt-3">

			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card input-group-addon">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Audit</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Audit</h3>
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
							value="<%=dto.getCreatedBy()%>"> <input type="hidden"
							name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
							type="hidden" name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<div class="md-form">

							<!-- Action By -->
							<span class="pl-sm-5"><b>Action By</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="actionBy"  placeholder="enter action by"
									value="<%=DataUtility.getStringData(dto.getActionBy())%>">
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("actionBy", request)%>
							</font><br>

							<!-- Action Type -->
							<span class="pl-sm-5"><b>Action Type</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="actionType" placeholder="enter action type"
									value="<%=DataUtility.getStringData(dto.getActionType())%>">
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("actionType", request)%>
							</font><br>

							<!-- Created Date -->
							<span class="pl-sm-5"><b>Created Date</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="createdDate" placeholder="enter create date"
									value="<%=DataUtility.getDateString(dto.getCreatedDate())%>"> 
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("createdDate", request)%>
							</font><br>

							<!-- Updated Date -->
							<span class="pl-sm-5"><b>Updated Date</b></span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="updatedDate" placeholder="enter update date"
									value="<%=DataUtility.getDateString(dto.getUpdatedDate())%>">
							</div>
							<br>

							<!-- Remarks -->
							<span class="pl-sm-5"><b>Remarks</b></span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="remarks" placeholder="enter remark"
									value="<%=DataUtility.getStringData(dto.getRemarks())%>">
							</div>
							<br>

							<!-- Buttons -->
							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=AuditCtl.OP_UPDATE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=AuditCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=AuditCtl.OP_SAVE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=AuditCtl.OP_RESET%>">
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
