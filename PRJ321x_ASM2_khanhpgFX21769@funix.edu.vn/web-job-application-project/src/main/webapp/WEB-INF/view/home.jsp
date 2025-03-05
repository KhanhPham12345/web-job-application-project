<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>

<head>
<title>luv2code Company Home Page</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">

<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Add script for AJAX upload -->
<script>
	$(document).ready(function() {
		// Upload CV using AJAX
		$('#uploadCvForm').on('submit', function(event) {
			event.preventDefault(); // Prevent the form from submitting the traditional way

			// Get CSRF token and header name from meta tags
			var csrfToken = $('meta[name="_csrf"]').attr('content');
			var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

			// Create a FormData object to hold the file and form data
			var formData = new FormData(this);

			// Send the AJAX request to upload the CV
			$.ajax({
				url : $(this).attr('action'), // Get the form action URL
				type : 'POST',
				data : formData,
				processData : false, // Prevent jQuery from processing the data
				contentType : false, // Set content type to false as we're sending FormData
				beforeSend : function(xhr) {
					// Add the CSRF token to the request header
					xhr.setRequestHeader(csrfHeader, csrfToken);
				},
				success : function(response) {
					alert("CV uploaded successfully!");
				},
				error : function(xhr, status, error) {
					alert("Error uploading CV: " + error);
				}
			});
		});
	});
</script>

</head>

<body>
	<h2>luv2code Company Home Page</h2>
	<hr>
	<p>Welcome to the luv2code Company Home Page</p>

	<hr>
	<!-- display user name and role -->
	<security:authorize access="isAuthenticated()">
		<p>
			User:
			<security:authentication property="principal.username" />
			<br> <br> Role(s):
			<security:authentication property="principal.authorities" />
		</p>
	</security:authorize>

	<security:authorize access="!isAuthenticated()">
		<p>User: anonymousUser</p>
	</security:authorize>

	<hr>
	<!-- Display the list of companies -->
	<h3>Top popular companies</h3>

	<c:forEach var="company" items="${popularCompanies}">
		<!-- Display the company logo -->
		<img src="${company.logo}" alt="${company.nameCompany} Logo"
			class="fixed-size-img" />
		<!-- add code to show company name and no.recruitment -->
		<p>
			<strong>${company.nameCompany}</strong> <br> Total recruitment:
			${company.totalRecruitments}
		</p>
	</c:forEach>

	<h3>List of popular recruitments</h3>
	<c:forEach var="recruitment" items="${popularRecruitments}">
		<p>
			<strong>Type:</strong> ${recruitment.type}<br> <strong>Title:</strong>
			${recruitment.title}<br> <strong>Category:</strong>
			${recruitment.category.category}<br> <strong>Company:</strong>
			${recruitment.company.nameCompany}<br> <strong>Address:</strong>
			${recruitment.address}<br> <strong>Deadline:</strong>
			${recruitment.deadline}<br> <strong>Status:</strong>
			${recruitment.status}<br>
		</p>
		<!-- Apply button linking to the application form page -->

		<c:choose>
			<c:when test="${not empty userId}">
				<!-- User is logged in, show the Apply button -->
				<form
					action="${pageContext.request.contextPath}/applyPost/applyPostForm"
					method="GET">
					<input type="hidden" name="recruitmentId" value="${recruitment.id}" />
					<input type="hidden" name="userId" value="${userId}" />
					<button type="submit">Apply</button>
				</form>
			</c:when>
			<c:otherwise>
				<!-- User is not logged in, show a login prompt -->
				<p>
					<a href="${pageContext.request.contextPath}/login">Log in to
						apply</a>
				</p>
			</c:otherwise>
		</c:choose>

	</c:forEach>


	<!-- Display the only user  -->

	<p>
		<security:authorize access="hasRole('APPLICANT')">
			<c:forEach var="user" items="${users}">
				<c:if test="${user.id == userId}">
					<!-- Check if user.id matches userId -->
					<form
						action="${pageContext.request.contextPath}/user/showFormForUpdate"
						method="GET" style="display: inline;">
						<input type="hidden" name="id" value="${user.id}" /> <input
							type="submit" value="User Profile Detail" class="update-button" />
					</form>


					<!-- button leads to page showing all recruitment in database  -->
					<form
						action="${pageContext.request.contextPath}/recruitment/showAllRecruitments"
						method="GET" style="display: inline;">
						<input type="hidden" name="id" value="${user.id}" /> <input
							type="submit" value="All active recruitment"
							class="update-button" />
					</form>

					<!-- Button to view uploaded CVs -->
					<form action="${pageContext.request.contextPath}/user/cvList"
						method="GET" style="display: inline;">
						<input type="hidden" name="userId" value="${user.id}" /> <input
							type="submit" value="View Uploaded CVs" class="view-button" />
					</form>

					<!-- Form to upload PDF CV file using AJAX -->
					<form id="uploadCvForm"
						action="${pageContext.request.contextPath}/user/uploadCv"
						method="POST" enctype="multipart/form-data"
						style="display: inline;">
						<input type="hidden" name="userId" value="${user.id}" /> <input
							type="file" name="cvFile" accept="application/pdf" required /> <input
							type="submit" value="Upload CV" class="upload-button" />
					</form>

					<!-- New Button for Applicants -->
					<form
						action="${pageContext.request.contextPath}/applyPost/applyPostList"
						method="GET" style="display: inline;">
						<input type="hidden" name="userId" value="${user.id}" />
						<button type="submit" class="applicant-button">View My
							Applications</button>
					</form>

				</c:if>
			</c:forEach>
		</security:authorize>
	</p>

	<hr>
	<!-- Display the list of popular categories  -->
	<p>
	<h3>Top popular categories on demand</h3>
	<c:forEach var="categoryData" items="${popularCategories}">
		<p>
			<strong>Category:</strong> ${categoryData[0]}<br> <strong>No.
				of recruitments:</strong> ${categoryData[1]}<br>
		</p>
	</c:forEach>
	</p>


	<hr>
	<security:authorize access="hasRole('HR')">
		<!-- Button to view all recruitments -->
		<form action="${pageContext.request.contextPath}/companies"
			method="GET" style="display: inline;">
			<input type="hidden" name="userId" value="${userId}" />
			<button type="submit">All Recruitments (Only for HR
				Managers)</button>
		</form>

		<!-- Button to edit HR user details -->
		<form
			action="${pageContext.request.contextPath}/user/showFormForUpdateHR"
			method="GET" style="display: inline;">
			<input type="hidden" name="userId" value="${userId}" />
			<button type="submit">Edit User Details (Only for HR
				Managers)</button>
		</form>

		<!-- Button to view companies with linked recruitments -->
		<form
			action="${pageContext.request.contextPath}/companies/linkedRecruitments"
			method="GET" style="display: inline;">
			<input type="hidden" name="userId" value="${userId}" />
			<button type="submit">View Companies with Linked
				Recruitments (Only for HR Managers)</button>
		</form>

	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<!-- Add a link to point to /systems ... this is for the admin  -->
		<p>
			<a href="${pageContext.request.contextPath}/systems">IT Systems
				meeting</a> (Only for Admins people)
		</p>
	</security:authorize>

	<hr>
	<form:form action="${pageContext.request.contextPath}/logout"
		method="POST">
		<input type="submit" value="Logout" />
	</form:form>
</body>

</html>
