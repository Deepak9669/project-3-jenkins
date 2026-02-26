<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.ExpenseCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expense View</title>

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
	<form action="<%=ORSView.EXPENSE_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ExpenseDTO"
			scope="request"></jsp:useBean>
			
			<%
			HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("map");
			%>

		<div class="row pt-3">

			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card input-group-addon">
					<div class="card-body">

						<%
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Expense</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Expense</h3>
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

							<!-- Expense Code -->
							<span class="pl-sm-5"><b>Expense Code</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="expenseCode"
									placeholder="enter expense code"
									value="<%=DataUtility.getStringData(dto.getExpenseCode())%>">
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("expenseCode", request)%>
							</font><br>

							<!-- Expense Type -->
							<span class="pl-sm-5"><b>Expense Type</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="expenseType"
									placeholder="enter expense type"
									value="<%=DataUtility.getStringData(dto.getExpenseType())%>">
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("expenseType", request)%>
							</font><br>

							<!-- Amount -->
							<span class="pl-sm-5"><b>Amount</b> *</span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="amount"
									placeholder="enter amount"
									value="<%=DataUtility.getStringData(dto.getAmount())%>">
							</div>
							<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("amount", request)%>
							</font><br>

							<!-- Expense Date -->
							<span class="pl-sm-5"><b>Expense Date</b></span><br>
							<div class="col-sm-12">
								<input type="text" class="form-control" name="expenseDate"
									placeholder="enter expense date"
									value="<%=DataUtility.getDateString(dto.getExpenseDate())%>">
							</div>
							<br>

							<!-- Expense Status -->
							<span class="pl-sm-5"><b>Expense Status</b></span><br>
							<div class="col-sm-12">
								<div class="input-group">
									<div class="input-group-prepend">
										<div class="input-group-text">
											<i class="fa fa-id-card grey-text" style="font-size: 1rem;"></i>
										</div>
									</div>

									<%
										String bt = DataUtility.getStringData(dto.getExpenseStatus());
									%>
									<%=HTMLUtility.getList("expenseStatus", dto.getExpenseStatus(), map)%>

								</div>
							</div>
							<br>

							<!-- Buttons -->
							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md"
									value="<%=ExpenseCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning btn-md"
									value="<%=ExpenseCtl.OP_CANCEL%>">
							</div>
							<%
								} else {
							%>
							<div class="text-center">
								<input type="submit" name="operation"
									class="btn btn-success btn-md" value="<%=ExpenseCtl.OP_SAVE%>">

								<input type="submit" name="operation"
									class="btn btn-warning btn-md" value="<%=ExpenseCtl.OP_RESET%>">
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