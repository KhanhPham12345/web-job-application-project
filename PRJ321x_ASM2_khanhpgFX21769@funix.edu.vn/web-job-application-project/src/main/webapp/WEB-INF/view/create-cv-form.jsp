<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>

<head>
<title>User and work experience Details</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Applicant details</h2>
		</div>

		<div id="container">
			<h3>Applicant Details</h3>

			<form:form modelAttribute="user">
				<table>
					<tbody>
						<tr>
							<td><label>Full name:</label></td>
							<td><form:input path="fullName" /> <form:errors
									path="fullName" cssClass="error" /></td>
						</tr>
						
						<tr>
							<td><label>Username(unique in database):</label></td>
							<td><form:input path="userName" /> <form:errors
									path="userName" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label>Email:</label></td>
							<td><form:input path="email" /> <form:errors path="email"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td><label>Address:</label></td>
							<td><form:input path="address" /> <form:errors
									path="address" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label>Phone no.:</label></td>
							<td><form:input path="phoneNumber" /> <form:errors
									path="phoneNumber" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label>Role:</label></td>
							<td><c:forEach var="role" items="${user.roles}">
									<span>${role.name}</span>
									<br />
								</c:forEach></td>
						</tr>
						<tr>
							<td><label>Status:</label></td>
							<td><form:select path="status" readonly="true">
									<form:option value="1" label="Active" />
								</form:select> <form:errors path="status" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label>Description:</label></td>
							<td><form:input path="description" /></td>
						</tr>
					</tbody>
				</table>
			</form:form>

			<!-- Button to Add New Work Experience with userId as a parameter -->
			<form
				action="${pageContext.request.contextPath}/workexp/showFormForAdd"
				method="GET">
				<input type="hidden" name="userId" value="${user.id}" /> <input
					type="submit" value="Add New Work Experience" class="action-button" />
			</form>

			<h3>List of users work experience</h3>

			<div id="content" class="scrollable-table">

				<table>
					<tr>
						<th>Company</th>
						<th>Position</th>
						<th>Start date</th>
						<th>End date</th>
						<th>Description</th>
						<th>Actions</th>
					</tr>

					<c:forEach var="workexp" items="${workExperiences}">

						<tr>
							<td>${workexp.companyName}</td>
							<td>${workexp.position}</td>
							<td>${workexp.startDate}</td>
							<td>${workexp.endDate}</td>
							<td>${workexp.description}</td>
							<td>
								<form
									action="${pageContext.request.contextPath}/workexp/showFormForUpdate"
									method="GET" style="display: inline;">
									<input type="hidden" name="id" value="${workexp.id}" /> <input
										type="submit" value="Update" class="button" />
								</form> <br>
								<form action="${pageContext.request.contextPath}/workexp/delete"
									method="POST" style="display: inline;"
									onsubmit="return confirm('Are you sure you want to delete this item?');">
									<input type="hidden" name="id" value="${workexp.id}" /> <input
										type="hidden" name="userId" value="${user.id}" /> <input
										type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" /> <input type="submit" value="Delete"
										class="button" />
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>

			</div>
			<br>
			<!-- Button to generate PDF of user details and work experiences -->
			<form:form modelAttribute="user"
				action="${pageContext.request.contextPath}/user/generatePdf"
				method="POST" style="margin-top: 20px;">
				<form:hidden path="id" value="${user.id}" />
				<input type="submit" value="Generate PDF and save in database" class="button" />
			</form:form>
			<br>
			<form:form action="${pageContext.request.contextPath}/" method="GET"
				modelAttribute="user">
				<input type="hidden" name="userId" value="${user.id}" />
				<button type="submit">Back to Home</button>
			</form:form>

		</div>
	</div>
</body>

</html>
