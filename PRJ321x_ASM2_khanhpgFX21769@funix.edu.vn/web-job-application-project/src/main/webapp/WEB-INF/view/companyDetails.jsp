<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Company Details</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<h1>Company Details</h1>
	<c:if test="${not empty company}">
		<p>
			<img src="${company.logo}" alt="${company.nameCompany} Logo"
			class="fixed-size-img" />
		</p>
		<p>
			<strong>Company Name:</strong> ${company.nameCompany}
		</p>
		<p>
			<strong>Address:</strong> ${company.address}
		</p>
		<p>
			<strong>Email:</strong> ${company.email}
		</p>
		<p>
			<strong>Phone Number:</strong> ${company.phoneNumber}
		</p>
		<p>
			<strong>Description:</strong> ${company.description}
		</p>
	</c:if>

	<h2>Recruitments</h2>
	<c:if test="${not empty recruitments}">
		<table border="1">
			<tr>
				<th>Title</th>
				<th>Description</th>
				<th>Experience</th>
				<th>Salary</th>
				<th>Created At</th>
				<th>Deadline</th>
				<th>Actions</th>
			</tr>
			<c:forEach var="recruitment" items="${recruitments}">

				<c:url var="updateLink" value="/recruitment/UpdateRecruitment">
					<c:param name="id" value="${recruitment.id}" />
				</c:url>

				<c:url var="deleteLink" value="/recruitment/DeleteRecruitment">
					<c:param name="id" value="${recruitment.id}" />
					<c:param name="userId" value="${user.id}" />
					<!-- Pass userId here -->
				</c:url>
				<tr>
					<td>${recruitment.title}</td>
					<td>${recruitment.description}</td>
					<td>${recruitment.experience}</td>
					<td>${recruitment.salary}</td>
					<td>${recruitment.createdAt}</td>
					<td>${recruitment.deadline}</td>
					<td><a href="${updateLink}">Edit</a> | <a href="${deleteLink}"
						onclick="if(!(confirm('Are you sure you want to delete this recruitment'))) return false">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<br>
	<form:form action="${pageContext.request.contextPath}/" method="get">
        <input type="hidden" name="userId" value="${user.id}" />
        <button type="submit">Back to Home</button>
    </form:form>
	
</body>
</html>
