<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Create Recruitment Post</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
<style>
.error {
	color: red;
}
</style>
</head>
<body>

	<h2>${recruitment.id == null ? "Create New Recruitment Post" : "Update Recruitment Post"}</h2>

	<!-- Recruitment Form -->
	<form:form
		action="${pageContext.request.contextPath}/recruitment/save-post"
		method="POST" modelAttribute="recruitment">

		<table>
			<!-- Hidden field for Recruitment ID (used for updates) -->
			<tr>
				<td colspan="2"><form:hidden path="id" /></td>
			</tr>
			<tr>
				<td>Title:</td>
				<td><form:input path="title" /> <form:errors path="title"
						cssClass="error" /></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><form:input path="address" /> <form:errors path="address"
						cssClass="error" /></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><form:textarea path="description" /> <form:errors
						path="description" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Experience:</td>
				<td><form:input path="experience" /> <form:errors
						path="experience" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Quantity:</td>
				<td><form:input path="quantity" /> <form:errors
						path="quantity" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Rank:</td>
				<td><form:input path="rank" /> <form:errors path="rank"
						cssClass="error" /></td>
			</tr>
			<tr>
				<td>Salary:</td>
				<td><form:input path="salary" /> <form:errors path="salary"
						cssClass="error" /></td>
			</tr>
			<tr>
				<td>Status:</td>
				<td><form:input path="status" /> <form:errors path="status"
						cssClass="error" /></td>
			</tr>
			<tr>
				<td>Type:</td>
				<td><form:input path="type" /> <form:errors path="type"
						cssClass="error" /></td>
			</tr>
			<tr>
				<td>Created (YY-MM-DD):</td>
				<td><form:input path="createdAt" type="date" /> <form:errors
						path="createdAt" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Deadline (YY-MM-DD):</td>
				<td><form:input path="deadline" type="date" /> <form:errors
						path="deadline" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Category:</td>
				<td><form:select path="category.id">
						<c:forEach var="category" items="${categories}">
							<option value="${category.id}"
								<c:if test="${category.id == recruitment.category.id}">selected</c:if>>
								${category.category}</option>
						</c:forEach>
					</form:select> <form:errors path="category.id" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Company:</td>
				<td><form:select path="company.id">
						<c:forEach var="company" items="${companies}">
							<option value="${company.id}"
								<c:if test="${company.id == recruitment.company.id}">selected</c:if>>
								${company.nameCompany}</option>
						</c:forEach>
					</form:select> <form:errors path="company.id" cssClass="error" /></td>
			</tr>

			<!-- Hidden field for user ID -->
			<tr>
				<td><input type="hidden" name="user.id"
					value="${recruitment.user.id}" /></td>
			</tr>

			<!-- Submit button -->
			<tr>
				<td><input type="submit" value="Create or Update" class="save" /></td>
			</tr>

		</table>
	</form:form>

	<hr>

	<!-- Separate form for Back to Home -->
	<form:form action="${pageContext.request.contextPath}/" method="GET">
		<input type="hidden" name="userId" value="${recruitment.user.id}" />
		<button type="submit">Back to Home</button>
	</form:form>


</body>
</html>
