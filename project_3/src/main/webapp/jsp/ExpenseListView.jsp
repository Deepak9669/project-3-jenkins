<%@page import="in.co.rays.project_3.dto.ExpenseDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.ExpenseListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Expense List</title>

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

<form action="<%=ORSView.EXPENSE_LIST_CTL%>" method="post">

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ExpenseDTO"
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

		Iterator<ExpenseDTO> it = list.iterator();
	%>

	<center>
		<h1 class="text-dark font-weight-bold pt-3">
			<u>Expense List</u>
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
			<input type="text" name="expenseCode" placeholder="Expense Code"
				class="form-control"
				value="<%=ServletUtility.getParameter("expenseCode", request)%>">
		</div>

		<div class="col-sm-2">
			<input type="text" name="expenseType" placeholder="Expense Type"
				class="form-control"
				value="<%=ServletUtility.getParameter("expenseType", request)%>">
		</div>

		<div class="col-sm-3">
			<input type="submit" class="btn btn-primary" name="operation"
				value="<%=ExpenseListCtl.OP_SEARCH%>">
			<input type="submit" class="btn btn-dark" name="operation"
				value="<%=ExpenseListCtl.OP_RESET%>">
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
					<th class="text">Expense Code</th>
					<th class="text">Expense Type</th>
					<th class="text">Amount</th>
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
					<td class="text"><%=dto.getExpenseCode()%></td>
					<td class="text"><%=dto.getExpenseType()%></td>
					<td class="text"><%=dto.getAmount()%></td>
					<td class="text"><%=dto.getExpenseStatus()%></td>
					<td class="text"><a
						href="<%=ORSView.APP_CONTEXT%>/ctl/ExpenseCtl?id=<%=dto.getId()%>">Edit</a>
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
				class="btn btn-warning" value="<%=ExpenseListCtl.OP_PREVIOUS%>"
				<%=pageNo > 1 ? "" : "disabled"%>></td>

			<td><input type="submit" name="operation"
				class="btn btn-primary" value="<%=ExpenseListCtl.OP_NEW%>"></td>

			<td><input type="submit" name="operation"
				class="btn btn-danger" value="<%=ExpenseListCtl.OP_DELETE%>">
			</td>

			<td align="right"><input type="submit" name="operation"
				class="btn btn-warning" value="<%=ExpenseListCtl.OP_NEXT%>"
				<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

		</tr>
	</table>

	<%
		} else {
	%>

	<!-- ================= EMPTY LIST AREA ================= -->

	<center>
		<h3 style="color: #162390;">No Record Found</h3>
	</center>

	<br>

	<div style="text-align: center;">
		<input type="submit" name="operation" class="btn btn-primary"
			value="<%=ExpenseListCtl.OP_BACK%>">
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