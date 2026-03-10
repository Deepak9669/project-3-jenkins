<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.VendorListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.dto.VendorDTO"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Vendor List</title>

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

	<form action="<%=ORSView.VENDOR_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.VendorDTO"
			scope="request"></jsp:useBean>

		<%
			HashMap statusMap = (HashMap) request.getAttribute("statusMap");

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

			Iterator<VendorDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-dark font-weight-bold pt-3">
				<u>Vendor List</u>
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

			<div class="col-sm-1"></div>

			<div class="col-sm-2">
				<input type="text" name="vendorName" placeholder="Vendor Name"
					class="form-control"
					value="<%=ServletUtility.getParameter("vendorName", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="contactNumber"
					placeholder="Contact Number" class="form-control"
					value="<%=ServletUtility.getParameter("contactNumber", request)%>">
			</div>

			<div class="col-sm-2">
				<select class="form-control" name="vendorStatus">

					<option value="">--Status--</option>

					<%
						if (statusMap != null) {
							for (Object key : statusMap.keySet()) {
					%>

					<option value="<%=key%>"
						<%=key.equals(ServletUtility.getParameter("vendorStatus", request)) ? "selected" : ""%>>
						<%=statusMap.get(key)%>
					</option>

					<%
						}
						}
					%>

				</select>
			</div>

			<div class="col-sm-3">
				<input type="submit" class="btn btn-primary" name="operation"
					value="<%=VendorListCtl.OP_SEARCH%>"> <input type="submit"
					class="btn btn-dark" name="operation"
					value="<%=VendorListCtl.OP_RESET%>">
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
						<th class="text">Vendor Name</th>
						<th class="text">Contact Number</th>
						<th class="text">Vendor Status</th>
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
						<td class="text"><%=dto.getVendorName()%></td>
						<td class="text"><%=dto.getContactNumber()%></td>
						<td class="text"><%=dto.getVendorStatus()%></td>

						<td class="text"><a
							href="<%=ORSView.APP_CONTEXT%>/ctl/VendorCtl?id=<%=dto.getId()%>">
								Edit </a></td>
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
					class="btn btn-warning" value="<%=VendorListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary" value="<%=VendorListCtl.OP_NEW%>"></td>

				<td><input type="submit" name="operation"
					class="btn btn-danger" value="<%=VendorListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-warning" value="<%=VendorListCtl.OP_NEXT%>"
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
				value="<%=VendorListCtl.OP_BACK%>">
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
