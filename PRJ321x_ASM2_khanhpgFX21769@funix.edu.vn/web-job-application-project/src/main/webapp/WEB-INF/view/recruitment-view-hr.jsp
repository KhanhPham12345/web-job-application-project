<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Recruitment Details</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<h2>Recruitment Details</h2>

	<table>
		<tr>
			<td>Title:</td>
			<td>${recruitment.title}</td>
		</tr>
		<tr>
			<td>Address:</td>
			<td>${recruitment.address}</td>
		</tr>
		<tr>
			<td>Description:</td>
			<td>${recruitment.description}</td>
		</tr>
		<tr>
			<td>Experience:</td>
			<td>${recruitment.experience}</td>
		</tr>
		<tr>
			<td>Quantity:</td>
			<td>${recruitment.quantity}</td>
		</tr>
		<tr>
			<td>Rank:</td>
			<td>${recruitment.rank}</td>
		</tr>
		<tr>
			<td>Salary:</td>
			<td>${recruitment.salary}</td>
		</tr>
		<tr>
			<td>Status:</td>
			<td>${recruitment.status}</td>
		</tr>
		<tr>
			<td>Type:</td>
			<td>${recruitment.type}</td>
		</tr>
		<tr>
			<td>Created:</td>
			<td>${recruitment.createdAt}</td>
		</tr>
		<tr>
			<td>Deadline:</td>
			<td>${recruitment.deadline}</td>
		</tr>
		<tr>
			<td>Category:</td>
			<td>${recruitment.category.category}</td>
		</tr>
		<tr>
			<td>Company:</td>
			<td>${recruitment.company.nameCompany}</td>
		</tr>
	</table>

	<hr>

	<!-- Back Button -->
	<form:form action="${pageContext.request.contextPath}/" method="GET">
		<input type="hidden" name="userId" value="${recruitment.user.id}" />
		<button type="submit">Back to Home</button>
	</form:form>
</body>
</html>
