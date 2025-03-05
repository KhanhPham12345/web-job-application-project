<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>ApplyPost Table</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<h2>Applied Post Manager</h2>

	<c:if test="${empty applyPosts}">
		<p>No ApplyPosts available.</p>
	</c:if>

	<c:if test="${not empty applyPosts}">
		<table border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th>Recruitment Description</th>
					<th>Company Name</th>
					<th>CV ID</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="applyPost" items="${applyPosts}">
					<tr>
						<td>${applyPost.id}</td>
						<td>${applyPost.recruitment.description}</td>
						<td>${applyPost.recruitment.company.nameCompany}</td>
						<td>${applyPost.cv.id}</td>
						<td><a
							href="${pageContext.request.contextPath}/applyPost/edit-applypost?id=${applyPost.id}&userId=${userId}">Edit</a>
							| <a
							href="${pageContext.request.contextPath}/applyPost/delete-applypost?id=${applyPost.id}&userId=${userId}"
							onclick="return confirm('Are you sure you want to delete this application?');">Delete</a>
							| <c:if test="${not empty applyPost.cv}">
								<a
									href="${pageContext.request.contextPath}/download-cv?cvId=${applyPost.cv.id}">Download
									CV</a>
							</c:if> <c:if test="${empty applyPost.cv}">
                                No CV
                            </c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	<br>
	<div>
		<form action="${pageContext.request.contextPath}/" method="GET">
			<input type="hidden" name="userId" value="${user.id}" />
			<button type="submit">Back to Home</button>
		</form>
	</div>
</body>
</html>