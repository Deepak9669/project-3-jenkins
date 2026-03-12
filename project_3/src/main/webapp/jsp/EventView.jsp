<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.EventCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.dto.EventDTO"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>

<title>Event View</title>

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

	<form action="<%=ORSView.EVENT_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.EventDTO"
			scope="request"></jsp:useBean>

		<%
			HashMap eventMap = (HashMap) request.getAttribute("eventMap");
		%>

		<div class="row pt-3">

			<div class="col-md-4"></div>

			<div class="col-md-4">

				<div class="card input-group-addon">

					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>

						<h3 class="text-center text-primary">Update Event</h3>

						<%
							} else {
						%>

						<h3 class="text-center text-primary">Add Event</h3>

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

						<input type="hidden" name="id" value="<%=dto.getId()%>"> <input
							type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">

						<input type="hidden" name="modifiedBy"
							value="<%=dto.getModifiedBy()%>"> <input type="hidden"
							name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">


						<div class="md-form">


							<!-- Participant Name -->

							<span class="pl-sm-5"><b>Participant Name</b> *</span><br>

							<div class="col-sm-12">

								<input type="text" class="form-control" name="participentName"
									placeholder="Enter participant name"
									value="<%=DataUtility.getStringData(dto.getParticipentName())%>">

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("participentName", request)%>

							</font><br>


							<!-- Event Name -->

							<span class="pl-sm-5"><b>Event Name</b> *</span><br>

							<div class="col-sm-12">

								<input type="text" class="form-control" name="eventName"
									placeholder="Enter event name"
									value="<%=DataUtility.getStringData(dto.getEventName())%>">

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("eventName", request)%>

							</font><br>


							<!-- Email -->

							<span class="pl-sm-5"><b>Email</b> *</span><br>

							<div class="col-sm-12">

								<input type="text" class="form-control" name="email"
									placeholder="Enter email"
									value="<%=DataUtility.getStringData(dto.getEmail())%>">

							</div>

							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("email", request)%>

							</font><br>


							<!-- Registration Date -->

							<span class="pl-sm-5"><b>Registration Date</b></span><br>

							<div class="col-sm-12">

								<input type="date" class="form-control" name="registrationDate"
									value="<%=DataUtility.getDateString(dto.getRegistrationDate())%>">

							</div>

							<br>


							<!-- Event Type Dropdown (Preload) -->

							<span class="pl-sm-5"><b>Event Type</b></span><br>

							<div class="col-sm-12">

								<select class="form-control" name="eventType">

									<option value="">--Select Event--</option>

									<%
										if (eventMap != null) {

											for (Object key : eventMap.keySet()) {
									%>

									<option value="<%=key%>">

										<%=eventMap.get(key)%>

									</option>

									<%
										}

										}
									%>

								</select>

							</div>

							<br>


							<!-- Buttons -->

							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=EventCtl.OP_UPDATE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=EventCtl.OP_CANCEL%>">

							</div>

							<%
								} else {
							%>

							<div class="text-center">

								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=EventCtl.OP_SAVE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=EventCtl.OP_RESET%>">

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