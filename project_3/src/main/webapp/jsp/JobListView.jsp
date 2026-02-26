<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.JobListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.JobDTO"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Job List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

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

	<form action="<%=ORSView.JOB_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.JobDTO"
			scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;

			int nextPageSize = 0;
			if (request.getAttribute("nextListSize") != null) {
				nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			}

			List list = ServletUtility.getList(request);
			if (list == null) {
				list = new ArrayList();
			}

			HashMap statusMap = (HashMap) request.getAttribute("statusMap");

			Iterator<JobDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-dark font-weight-bold pt-3">
				<u>Job List</u>
			</h1>
		</center>

		<!-- ================= MESSAGE AREA ================= -->

		<div class="row">
			<div class="col-md-4"></div>

			<%
				if (!ServletUtility.getSuccessMessage(request).equals("")) {
			%>
			<div class="col-md-4 alert alert-success">
				<%=ServletUtility.getSuccessMessage(request)%>
			</div>
			<%
				}
			%>

			<%
				if (!ServletUtility.getErrorMessage(request).equals("")) {
			%>
			<div class="col-md-4 alert alert-danger">
				<%=ServletUtility.getErrorMessage(request)%>
			</div>
			<%
				}
			%>

			<div class="col-md-4"></div>
		</div>

		<br>

		<!-- ================= SEARCH AREA ================= -->

		<div class="row">

			<div class="col-sm-2"></div>

			<div class="col-sm-2">
				<input type="text" name="title" placeholder="Title"
					class="form-control"
					value="<%=ServletUtility.getParameter("title", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="experience" placeholder="Experience"
					class="form-control"
					value="<%=ServletUtility.getParameter("experience", request)%>">
			</div>

			<div class="col-sm-2">
				<select name="status" class="form-control">
					<option value="">--Status--</option>
					<%
						if (statusMap != null) {
							for (Object key : statusMap.keySet()) {
								String val = (String) key;
					%>
					<option value="<%=val%>"
						<%=(val.equals(ServletUtility.getParameter("status", request))) ? "selected" : ""%>>
						<%=val%>
					</option>
					<%
							}
						}
					%>
				</select>
			</div>

			<div class="col-sm-3">
				<input type="submit" class="btn btn-primary" name="operation"
					value="<%=JobListCtl.OP_SEARCH%>"> 
				<input type="submit"
					class="btn btn-dark" name="operation"
					value="<%=JobListCtl.OP_RESET%>">
			</div>
		</div>

		<br>

		<!-- ================= TABLE AREA ================= -->

		<%
			if (!list.isEmpty()) {
		%>

		<div class="table-responsive">
			<table class="table table-bordered table-dark table-hover">

				<thead>
					<tr>
						<th width="10%"><input type="checkbox" id="select_all">
							Select All</th>
						<th class="text">S.NO</th>
						<th class="text">Title</th>
						<th class="text">DOB</th>
						<th class="text">Experience</th>
						<th class="text">Status</th>
						<th class="text">Edit</th>
					</tr>
				</thead>

				<tbody>

					<%
						while (it.hasNext()) {
							dto = it.next();
					%>

					<tr>
						<td align="center"><input type="checkbox" class="checkbox"
							name="ids" value="<%=dto.getId()%>"></td>
						<td class="text"><%=index++%></td>
						<td class="text"><%=dto.getTitle()%></td>
						<td class="text"><%=DataUtility.getDateString(dto.getDob())%></td>
						<td class="text"><%=dto.getExperience()%></td>
						<td class="text"><%=dto.getStatus()%></td>
						<td class="text"><a
							href="<%=ORSView.APP_CONTEXT%>/ctl/JobCtl?id=<%=dto.getId()%>">Edit</a>
						</td>
					</tr>

					<%
						}
					%>

				</tbody>
			</table>
		</div>

		<!-- ================= BUTTON AREA ================= -->

		<table width="100%">
			<tr>

				<td><input type="submit" name="operation"
					class="btn btn-warning" value="<%=JobListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary" value="<%=JobListCtl.OP_NEW%>"></td>

				<td><input type="submit" name="operation"
					class="btn btn-danger" value="<%=JobListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-warning" value="<%=JobListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

			</tr>
		</table>

		<%
			} else {
		%>

		<center>
			<h3 style="color: #162390;">No Record Found</h3>
		</center>

		<br>

		<div style="text-align: center;">
			<input type="submit" name="operation" class="btn btn-primary"
				value="<%=JobListCtl.OP_BACK%>">
		</div>

		<%
			}
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>">
		<input type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

</body>

<%@include file="FooterView.jsp"%>
</html>