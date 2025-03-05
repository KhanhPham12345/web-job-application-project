<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>User's Profile</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">

<script>
        // JavaScript to toggle the visibility of the add company popup
        function toggleAddCompanyPopup() {
            var popup = document.getElementById("addCompanyPopup");
            if (popup.style.display === "none") {
                popup.style.display = "block";
            } else {
                popup.style.display = "none";
            }
        }

        // Close the popup when the user clicks on <span> (x)
        function closePopup() {
            var popup = document.getElementById("addCompanyPopup");
            popup.style.display = "none";
        }
    </script>
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Update User's Details</h2>
		</div>

		<div id="container">
			<h3>Save New User's Details for HR</h3>

			<!-- Container for user details form -->
			<div class="form-container">
				<!-- Edit User Details Form -->
				<div class="form-wrapper">

					<!-- Error message display -->
					<c:if test="${not empty errorMessage}">
						<div class="error-message">${errorMessage}</div>
					</c:if>
					<form:form
						action="${pageContext.request.contextPath}/user/saveUserHR"
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
									<td><form:input path="password" type="text"
											value="${user.password}" /> <form:errors path="password"
											cssClass="error" /></td>
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
									<td><label>Google Drive Image Link:</label></td>
									<td><form:input path="image" /> <form:errors path="image"
											cssClass="error" /></td>
								</tr>
								<tr>
									<td></td>
									<td><input type="submit" value="Update" class="save" /></td>
								</tr>
							</tbody>
						</table>
					</form:form>
					<p>
						<!-- Button to show the "Add New Company" popup -->
						<button onclick="toggleAddCompanyPopup()">Add New Company</button>
					</p>
				</div>
			</div>

			<!-- Popup Form for Adding New Company -->
			<div id="addCompanyPopup" class="popup">
				<div class="popup-content">
					<span class="close" onclick="closePopup()">&times;</span>
					<h3>Add New Company</h3>
					<form:form
						action="${pageContext.request.contextPath}/companies/saveNewCompany"
						method="POST" modelAttribute="company">
						<input type="hidden" name="userId" value="${user.id}" />

						<table>
							<tbody>
								<tr>
									<td><label>Company logo (Google Drive Image Link):</label></td>
									<td><form:input path="logo" /> <form:errors path="logo"
											cssClass="error" /></td>
								</tr>
								<tr>
									<td><label>Company Name:</label></td>
									<td><form:input path="nameCompany" required="true" /> <form:errors
											path="nameCompany" cssClass="error" /></td>
								</tr>
								<tr>
									<td><label>Address:</label></td>
									<td><form:input path="address" required="true" /> <form:errors
											path="address" cssClass="error" /></td>
								</tr>
								<tr>
									<td><label>Phone No. :</label></td>
									<td><form:input path="phoneNumber" required="true" /> <form:errors
											path="phoneNumber" cssClass="error" /></td>
								</tr>
								<tr>
									<td><label>Email :</label></td>
									<td><form:input path="email" required="true" /> <form:errors
											path="email" cssClass="error" /></td>
								</tr>
								<tr>
									<td><label>Description:</label></td>
									<td><form:textarea path="description"></form:textarea></td>
								</tr>
								<tr>
									<td></td>
									<td><input type="submit" value="Add Company" class="save" /></td>
								</tr>
							</tbody>
						</table>
					</form:form>
				</div>
			</div>
		</div>

		<!-- Back to home -->
		<form:form action="${pageContext.request.contextPath}/" method="GET">
			<input type="hidden" name="userId" value="${user.id}" />
			<button type="submit">Back to Home</button>
		</form:form>
		<br> <br>

		<!-- Logout Form -->
		<form:form action="${pageContext.request.contextPath}/logout"
			method="POST">
			<input type="submit" value="Logout" />
		</form:form>
	</div>
</body>
</html>
