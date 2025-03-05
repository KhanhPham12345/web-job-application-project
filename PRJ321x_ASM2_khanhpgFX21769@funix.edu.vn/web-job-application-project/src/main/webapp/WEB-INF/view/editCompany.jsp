<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Edit Company</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
	<h1>Edit Company Details</h1>

	<c:if test="${not empty company}">

		<form:form modelAttribute="company"
			action="${pageContext.request.contextPath}/companies/updateCompany"
			method="post">
			<table>
				<!-- Hidden Fields -->
				<tr>
					<td colspan="2"><form:hidden path="id"/></td>
				</tr>
				
				<!-- Passing userId directly in hidden field -->
                <tr>
                    <td colspan="2">
                    	<input type="hidden" name="companyId" value="${company.id}" />
                        <input type="hidden" name="userId" value="${user.id}" />
                    </td>
                </tr>

				<!-- Company Name -->
				<tr>
					<td><label for="nameCompany">Company Name:</label></td>
					<td><form:input path="nameCompany" id="nameCompany"
							required="true" /> <form:errors path="nameCompany"
							cssClass="error" /></td>
				</tr>

				<!-- Address -->
				<tr>
					<td><label for="address">Address:</label></td>
					<td><form:input path="address" id="address" required="true" />
						<form:errors path="address" cssClass="error" /></td>
				</tr>

				<!-- Email -->
				<tr>
					<td><label for="email">Email:</label></td>
					<td><form:input path="email" id="email" type="email"
							required="true" /> <form:errors path="email" cssClass="error" />
					</td>
				</tr>

				<!-- Phone Number -->
				<tr>
					<td><label for="phoneNumber">Phone Number:</label></td>
					<td><form:input path="phoneNumber" id="phoneNumber"
							required="true" /> <form:errors path="phoneNumber"
							cssClass="error" /></td>
				</tr>

				<!-- Description -->
				<tr>
					<td><label for="description">Description:</label></td>
					<td><form:textarea path="description" id="description"
							required="true"></form:textarea> <form:errors path="description"
							cssClass="error" /></td>
				</tr>

				<!-- Logo -->
				<tr>
					<td><label for="logo">New Logo (Google Drive link):</label></td>
					<td><form:input path="logo" id="logo" /> <form:errors
							path="logo" cssClass="error" /></td>
				</tr>

				<!-- Current Logo -->
				<tr>
					<td>Current Logo:</td>
					<td><img src="${company.logo}"
						alt="${company.nameCompany} Logo" class="fixed-size-img" /></td>
				</tr>

				<!-- Submit Button -->
				<tr class="button-row">
					<td colspan="2">
						<button type="submit">Update Company</button>
					</td>
				</tr>
			</table>
		</form:form>
	</c:if>

	<c:if test="${empty company}">
		<p>Company not found.</p>
	</c:if>
</body>
</html>
