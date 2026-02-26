<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.DoctorListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.DoctorDTO"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Doctor List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/books1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}

.text {
	text-align: center;
}
</style>
</head>

<%@include file="Header.jsp"%>

<body class="hm">

	<div>
		<form class="pb-5" action="<%=ORSView.DOCTOR_LIST_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.DoctorDTO"
				scope="request"></jsp:useBean>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List list = ServletUtility.getList(request);
				Iterator<DoctorDTO> it = list.iterator();
			%>

			<%
				if (list.size() != 0) {
			%>

			<center>
				<h1 class="text-dark font-weight-bold pt-3">
					<u>Doctor List</u>
				</h1>
			</center>

			<!-- Success Message -->
			<div class="row">
				<div class="col-md-4"></div>
				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>
				<div class="col-md-4 alert alert-success">
					<h4><%=ServletUtility.getSuccessMessage(request)%></h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>

			<!-- Error Message -->
			<div class="row">
				<div class="col-md-4"></div>
				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger">
					<h4><%=ServletUtility.getErrorMessage(request)%></h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>

			<!-- Search Fields -->
			<div class="row">

				<div class="col-sm-1"></div>

				<div class="col-sm-2">
					<input type="text" name="doctorName" placeholder="Doctor Name"
						class="form-control"
						value="<%=ServletUtility.getParameter("doctorName", request)%>">
				</div>

				<div class="col-sm-2">
					<input type="text" name="hospitalName" placeholder="Hospital Name"
						class="form-control"
						value="<%=ServletUtility.getParameter("hospitalName", request)%>">
				</div>

				<div class="col-sm-2">
					<input type="text" name="patientName" placeholder="Patient Name"
						class="form-control"
						value="<%=ServletUtility.getParameter("patientName", request)%>">
				</div>

				<div class="col-sm-2">
					<input type="text" name="diseases" placeholder="Diseases"
						class="form-control"
						value="<%=ServletUtility.getParameter("diseases", request)%>">
				</div>

				<div class="col-sm-2">
					<input type="submit" class="btn btn-primary" name="operation"
						value="<%=DoctorListCtl.OP_SEARCH%>"> <input type="submit"
						class="btn btn-dark" name="operation"
						value="<%=DoctorListCtl.OP_RESET%>">
				</div>

				<div class="col-sm-1"></div>
			</div>

			<br>

			<!-- Table -->
			<div class="table-responsive">
				<table class="table table-bordered table-dark table-hover">

					<thead>
						<tr style="background-color: #8C8C8C;">

							<th width="10%"><input type="checkbox" id="select_all">
								Select All</th>

							<th class="text">S.NO</th>
							<th class="text">Doctor Name</th>
							<th class="text">Hospital Name</th>
							<th class="text">Patient Name</th>
							<th class="text">Diseases</th>
							<th class="text">Appointment Date</th>
							<th class="text">Edit</th>

						</tr>
					</thead>

					<%
						while (it.hasNext()) {
								dto = it.next();
					%>

					<tbody>
						<tr>
							<td align="center"><input type="checkbox" class="checkbox"
								name="ids" value="<%=dto.getId()%>"></td>

							<td class="text"><%=index++%></td>
							<td class="text"><%=dto.getDoctorName()%></td>
							<td class="text"><%=dto.getHospitalName()%></td>
							<td class="text"><%=dto.getPatientName()%></td>
							<td class="text"><%=dto.getDiseases()%></td>
							<td class="text"><%=DataUtility.getDateString(dto.getAppointmentDate())%></td>
							<td class="text"><a href="DoctorCtl?id=<%=dto.getId()%>">Edit</a></td>
						</tr>
					</tbody>

					<%
						}
					%>

				</table>
			</div>

			<!-- Buttons -->
			<table width="100%">
				<tr>

					<td><input type="submit" name="operation"
						class="btn btn-warning" value="<%=DoctorListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td><input type="submit" name="operation"
						class="btn btn-primary" value="<%=DoctorListCtl.OP_NEW%>">
					</td>

					<td><input type="submit" name="operation"
						class="btn btn-danger" value="<%=DoctorListCtl.OP_DELETE%>">
					</td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-warning" value="<%=DoctorListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

				</tr>
			</table>

			<%
				}
				if (list.size() == 0) {
			%>

			<center>
				<h1 style="font-size: 40px; color: #162390;">Doctor List</h1>
			</center>

			<br>

			<div style="padding-left: 48%;">
				<input type="submit" name="operation" class="btn btn-primary"
					value="<%=DoctorListCtl.OP_BACK%>">
			</div>

			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

		</form>
	</div>

</body>

<%@include file="FooterView.jsp"%>
</html>
