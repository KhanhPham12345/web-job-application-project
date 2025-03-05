<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>Uploaded CVs</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <h2>List of CVs Uploaded</h2>
    
    <br>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>File</th>
                <th>User ID</th>
                <th>Actions</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="cv" items="${cvs}">
                <tr>
                    <td>${cv.id}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/download-cv?cvId=${cv.id}">${cv.fileLink}</a>
                    </td>
                    <td>${cv.user.id}</td>
                    <td>
                        <!-- Include userId in edit and delete links -->
                        <a href="${pageContext.request.contextPath}/edit-cv?cvId=${cv.id}&userId=${cv.user.id}">Edit</a>
                        |
                        <a href="${pageContext.request.contextPath}/delete-cv?cvId=${cv.id}&userId=${cv.user.id}" 
                           onclick="return confirm('Are you sure you want to delete this CV?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	
	<br>
    <div>
        <!-- Only show the "Create new CV" link if there are CVs or a userId is present -->
        <c:choose>
            <c:when test="${not empty cvs}">
                <a href="${pageContext.request.contextPath}/create-newcv?userId=${cvs[0].user.id}" class="button">Create new CV</a>
            </c:when>
            <c:otherwise>
                <!-- You might need to pass userId via model in case there are no CVs -->
                <a href="${pageContext.request.contextPath}/create-newcv?userId=${userId}" class="button">Create new CV</a>
            </c:otherwise>
        </c:choose>
    </div>
    
    <br>
    <!-- Back to Home Button -->
    <div>
        <a href="${pageContext.request.contextPath}/?userId=${user.id}" class="button">Back to Home</a>
    </div>
</body>
</html>
