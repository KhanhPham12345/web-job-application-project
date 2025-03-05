<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<title>User's profile</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Update user's details</h2>
		</div>

		<div id="container">
			<h3>Save new user's details</h3>

			<!-- Error message display -->
			<c:if test="${not empty errorMessage}">
				<div class="error-message">${errorMessage}</div>
			</c:if>

			<form:form action="${pageContext.request.contextPath}/user/saveUser"
				modelAttribute="user" method="POST">
				<form:hidden path="id" />

				<table>
					<tbody>
						<tr>
							<td><label>User image:</label></td>
							<td><img src="${user.image}" alt="${user.fullName} Logo"
								class="fixed-size-img" /></td>
						</tr>

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
							<td><label>Password:</label></td>
							<td><form:input path="password" readonly="true" /> <form:errors
									path="password" cssClass="error" /></td>
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

						<tr>
							<td><label>Google Drive Image Link(to change profile
									pic):</label></td>
							<td><form:input path="image" /> <form:errors path="image"
									cssClass="error" /></td>
						</tr>

						<tr>
							<td><label></label></td>
							<td><input type="submit" value="Update" class="save" /></td>
						</tr>

					</tbody>
				</table>
			</form:form>

			<br>
			<!-- Back to home -->
			<form:form action="${pageContext.request.contextPath}/" method="GET">
				<input type="hidden" name="userId" value="${user.id}" />
				<button type="submit">Back to Home</button>
			</form:form>
			<br>

			<form:form action="${pageContext.request.contextPath}/logout"
				method="POST">
				<input type="submit" value="Logout" />
			</form:form>
		</div>
	</div>
</body>

</html>