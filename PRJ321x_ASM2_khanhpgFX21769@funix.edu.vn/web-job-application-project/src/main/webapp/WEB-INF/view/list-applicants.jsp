<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Applicants for Recruitment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h2>Applicants for Recruitment ID: ${recruitmentId}</h2>

    <c:if test="${empty applyPosts}">
        <p>No applicants found for this recruitment.</p>
    </c:if>

    <c:if test="${not empty applyPosts}">
        <table border="1">
            <thead>
                <tr>
                    <th>Applicant Name</th>
                    <th>Applicant Email</th>
                    <th>CV Name</th>
                    <th>Application Text</th>
                    <th>Status</th>
                    <th>Created At</th>
                    <th>Download Applicant's CV</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="applyPost" items="${applyPosts}">
                    <tr>
                        <td>${applyPost.user.fullName}</td>
                        <td>${applyPost.user.email}</td>
                        <td>${applyPost.nameCv}</td>
                        <td>${applyPost.text}</td>
                        <td>${applyPost.status}</td>
                        <td>${applyPost.createdAt}</td>
                        <td> <c:if test="${not empty applyPost.cv}">
                <a href="${pageContext.request.contextPath}/download-cv?cvId=${applyPost.cv.id}">
                    Download CV
                </a>
            </c:if>
            <c:if test="${empty applyPost.cv}">
                No CV
            </c:if></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    
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
