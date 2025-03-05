<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<title>Recruitments by HR</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	
	<h2>Recruitments Managed by: ${hrUser.fullName}</h2>

	<c:if test="${not empty hrRecruitments}">
	
	<form action="${pageContext.request.contextPath}/searchRecruitmentsForHR"
		method="GET">
		Search: <input type="text" name="theSearchName"
			placeholder="Search by job name, company, location..." /> <input
			type="submit" value="Search" />
	</form>
	
	<!-- Form to select number of entries per page -->
	<form
		action="${pageContext.request.contextPath}/showAllRecruitmentsForHR"
		method="GET">
		Show entries: <select name="entriesPerPage"
			onchange="this.form.submit()">
			<option value="5" ${param.entriesPerPage == '5' ? 'selected' : ''}>5</option>
			<option value="10" ${param.entriesPerPage == '10' ? 'selected' : ''}>10</option>
			<option value="15" ${param.entriesPerPage == '15' ? 'selected' : ''}>15</option>
			<option value="20" ${param.entriesPerPage == '20' ? 'selected' : ''}>20</option>
			<option value="all"
				${param.entriesPerPage == 'all' ? 'selected' : ''}>All</option>
		</select>
	</form>
	
		<table border="1">
			<thead>
				<tr>
					<th>Title</th>
					<th>Type</th>
					<th>Category</th>
					<th>Company</th>
					<th>Address</th>
					<th>Deadline</th>
					<th>Status</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="recruitment" items="${hrRecruitments}">

					<c:url var="updateLink" value="/recruitment/UpdateRecruitment">
						<c:param name="id" value="${recruitment.id}" />
					</c:url>
					
					<c:url var="detailLink" value="/recruitment/DetailRecruitmentHR">
						<c:param name="id" value="${recruitment.id}" />
						<c:param name="userId" value="${hrUser.id}" />
						<!-- Pass userId here -->
					</c:url>

					<c:url var="deleteLink" value="/recruitment/DeleteRecruitment">
						<c:param name="id" value="${recruitment.id}" />
						<c:param name="userId" value="${hrUser.id}" />
						<!-- Pass userId here -->
					</c:url>

					<tr>
						<td><a
							href="${pageContext.request.contextPath}/applyPost/listApplicants?recruitmentId=${recruitment.id}">
								${recruitment.title} </a></td>
						<td>${recruitment.type}</td>
						<td>${recruitment.category.category}</td>
						<td>${recruitment.company.nameCompany}</td>
						<td>${recruitment.address}</td>
						<td>${recruitment.deadline}</td>
						<td>${recruitment.status}</td>
						<td><a href="${detailLink}">Detail</a> | <a href="${updateLink}">Edit</a> | <a
							href="${deleteLink}"
							onclick="if(!(confirm('Are you sure you want to delete this recruitment'))) return false">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	<c:if test="${empty hrRecruitments}">
		<p>No recruitments found for this HR.</p>
	</c:if>

	<hr>
	<!-- Add new post link, visible only to HR managers using Spring Security -->
	<security:authorize access="hasRole('ROLE_HR')">
		<a
			href="${pageContext.request.contextPath}/recruitment/apply-post?userId=${hrUser.id}">Add
			New Post</a>
	</security:authorize>
	<br>
	<br>

	<div>
		<form action="${pageContext.request.contextPath}/" method="GET">
			<input type="hidden" name="userId" value="${hrUser.id}" />
			<button type="submit">Back to Home</button>
		</form>
	</div>

</body>
</html>
