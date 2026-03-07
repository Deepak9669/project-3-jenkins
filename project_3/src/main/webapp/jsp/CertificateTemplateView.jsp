<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.CertificateTemplateCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.CertificateTemplateDTO"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>

<title>Certificate Template View</title>

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

	<form action="<%=ORSView.CERTIFICATE_TEMPLATE_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.CertificateTemplateDTO"
			scope="request"></jsp:useBean>

		<%
			HashMap formatMap = (HashMap) request.getAttribute("formatMap");
		%>

		<div class="row pt-3">

			<div class="col-md-4"></div>

			<div class="col-md-4">

				<div class="card input-group-addon">

					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>

						<h3 class="text-center text-primary">Update Certificate
							Template</h3>

						<%
							} else {
						%>

						<h3 class="text-center text-primary">Add Certificate Template</h3>

						<%
							}
						%>

						<!-- SUCCESS MESSAGE -->

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

						<!-- ERROR MESSAGE -->

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

						<!-- HIDDEN FIELDS -->

						<input type="hidden" name="id" value="<%=dto.getId()%>"> <input
							type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">

						<input type="hidden" name="modifiedBy"
							value="<%=dto.getModifiedBy()%>"> <input type="hidden"
							name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<div class="md-form">

							<!-- TEMPLATE NAME -->

							<span class="pl-sm-5"><b>Template Name</b> *</span><br>

							<div class="col-sm-12">

								<input type="text" class="form-control" name="templateName"
									placeholder="Enter Template Name"
									value="<%=DataUtility.getStringData(dto.getTemplateName())%>">

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("templateName", request)%>

							</font> <br>

							<!-- FORMAT DROPDOWN -->

							<span class="pl-sm-5"><b>Format</b> *</span><br>

							<div class="col-sm-12">

								<%=HTMLUtility.getList("format", String.valueOf(dto.getFormat()), formatMap)%>

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("format", request)%>

							</font> <br>

							<!-- CREATED DATE -->

							<span class="pl-sm-5"><b>Created Date</b></span><br>

							<div class="col-sm-12">

								<input type="date" class="form-control" name="createdDate"
									placeholder="Enter Created Date"
									value="<%=DataUtility.getDateString(dto.getCreatedDate())%>">

							</div>

							<br>

							<!-- BUTTONS -->

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=CertificateTemplateCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									value="<%=CertificateTemplateCtl.OP_CANCEL%>">

							</div>

							<%
								} else {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=CertificateTemplateCtl.OP_SAVE%>"> <input
									type="submit" name="operation" class="btn btn-warning btn-md"
									value="<%=CertificateTemplateCtl.OP_RESET%>">

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

