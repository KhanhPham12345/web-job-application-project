<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<title>All Recruitments</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
	<h2>All Recruitments</h2>
	<hr>

	<!-- Search Form -->
	<form action="${pageContext.request.contextPath}/recruitment/search"
		method="GET">
		Search: <input type="text" name="theSearchName"
			placeholder="Search by job name, company, location..." /> <input
			type="submit" value="Search" />
	</form>

	<!-- Form to select number of entries per page -->
	<form
		action="${pageContext.request.contextPath}/recruitment/showAllRecruitments"
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

	<!-- Scrollable Table -->
	<div class="scrollable-table">
		<table border="1" cellspacing="0" cellpadding="5">
			<thead>
				<tr>
					<th>Type</th>
					<th>Title</th>
					<th>Category</th>
					<th>Company</th>
					<th>Address</th>
					<th>Deadline</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="recruitment" items="${allRecruitments}">
					<tr>
						<td>${recruitment.type}</td>
						<td>${recruitment.title}</td>
						<td>${recruitment.category.category}</td>
						<td>${recruitment.company.nameCompany}</td>
						<td>${recruitment.address}</td>
						<td>${recruitment.deadline}</td>
						<td>${recruitment.status}</td>
						<td>
							<!-- Form to apply for recruitment -->
							<form
								action="${pageContext.request.contextPath}/applyPost/applyPostForm"
								method="GET">
								<input type="hidden" name="recruitmentId"
									value="${recruitment.id}" /> <input type="hidden"
									name="userId" value="${userId}" />
								<button type="submit">Apply</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<hr>

	<!-- Logout Form -->
	<form:form action="${pageContext.request.contextPath}/logout"
		method="POST">
		<input type="submit" value="Logout" />
	</form:form>

</body>
</html>
