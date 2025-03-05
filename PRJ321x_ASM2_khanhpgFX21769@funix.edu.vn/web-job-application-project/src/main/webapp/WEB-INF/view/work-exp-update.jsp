<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>

<head>
<title>Save work experience</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Work experience Manager</h2>
		</div>

		<div id="container">
			<h3>Update and save work experience</h3>

			<form:form action="${pageContext.request.contextPath}/workexp/update"
				modelAttribute="workexperience" method="POST">

				<form:hidden path="id" />

				<table>
					<tbody>
						<tr>
							<td><label>Company</label></td>
							<td><form:input path="companyName" /> <form:errors
									path="companyName" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label>Start date:</label></td>
							<td><form:input path="startDate" type="date" /> <form:errors
									path="startDate" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label>End date:</label></td>
							<td><form:input path="endDate" type="date" /> <form:errors
									path="endDate" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label>Position:</label></td>
							<td><form:input path="position" /> <form:errors
									path="position" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label>Description:</label></td>
							<td><form:input path="description" type="text" /> <form:errors
									path="description" cssClass="error" /></td>
						</tr>

						<tr>
							<td><label></label></td>
							<td><input type="submit" value="Update" class="save"></td>

						</tr>

					</tbody>
				</table>


			</form:form>

		</div>
	</div>
</body>

</html>