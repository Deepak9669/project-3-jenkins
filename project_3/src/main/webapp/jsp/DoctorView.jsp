<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.DoctorCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.DoctorDTO"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Doctor View</title>

<style type="text/css">
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
	<form action="<%=ORSView.DOCTOR_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.DoctorDTO"
			scope="request"></jsp:useBean>

		<div class="row pt-3">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card input-group-addon">
					<div class="card-body">

						<%
							if (dto.getDoctorName() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Doctor</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Doctor</h3>
						<%
							}
						%>

						<!-- Success Message -->
						<h4 align="center">
							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success">
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
							<div class="alert alert-danger">
								<%=ServletUtility.getErrorMessage(request)%>
							</div>
							<%
								}
							%>
						</h4>

						<!-- Hidden Fields -->
						<input type="hidden" name="id" value="<%=dto.getId()%>"> <input
							type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
						<input type="hidden" name="modifiedBy"
							value="<%=dto.getModifiedBy()%>"> <input type="hidden"
							name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<!-- Doctor Name -->
						<b>Doctor Name *</b> <input type="text" class="form-control"
							name="doctorName" placeholder="enter doctor name"
							value="<%=DataUtility.getStringData(dto.getDoctorName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("doctorName", request)%></font>
						<br>

						<!-- Hospital Name -->
						<b>Hospital Name *</b> <input type="text" class="form-control"
							name="hospitalName" placeholder="enter hospital name"
							value="<%=DataUtility.getStringData(dto.getHospitalName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("hospitalName", request)%></font>
						<br>

						<!-- Patient Name -->
						<b>Patient Name *</b> <input type="text" class="form-control"
							name="patientName" placeholder="enter patient name"
							value="<%=DataUtility.getStringData(dto.getPatientName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("patientName", request)%></font>
						<br>

						<!-- Diseases -->
						<b>Diseases *</b> <input type="text" class="form-control"
							name="diseases" placeholder="enter diseases name"
							value="<%=DataUtility.getStringData(dto.getDiseases())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("diseases", request)%></font>
						<br>

						<!-- Appointment Date -->
						<b>Appointment Date *</b> <input type="date"
							name="appointmentDate" class="form-control" 
							value="<%=DataUtility.getDateString(dto.getAppointmentDate())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("appointmentDate", request)%></font>
						<br>

						<!-- Buttons -->
						<%
							if (dto.getDoctorName() != null && dto.getId() > 0) {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=DoctorCtl.OP_UPDATE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=DoctorCtl.OP_CANCEL%>">
						</div>
						<%
							} else {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success"
								value="<%=DoctorCtl.OP_SAVE%>"> <input type="submit"
								name="operation" class="btn btn-warning"
								value="<%=DoctorCtl.OP_RESET%>">
						</div>
						<%
							}
						%>

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
