<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit ApplyPost - CV</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h2>Edit ApplyPost CV</h2>
    
    <!-- Form for editing the CV of the ApplyPost -->
    <form:form action="${pageContext.request.contextPath}/applyPost/edit-applypost" method="POST">
        <!-- Hidden fields to pass ApplyPost ID and User ID -->
        <input type="hidden" name="id" value="${applyPost.id}" />
        <input type="hidden" name="userId" value="${userId}" />
        
        <!-- Label for CV selection -->
        <label for="cvId">Select New CV:</label>
        
        <!-- Dropdown for selecting CV -->
        <select name="cvId" id="cvId">
            <c:forEach var="cv" items="${cvs}">
                <option value="${cv.id}" ${cv.id == applyPost.cv.id ? 'selected' : ''}>
                    ${cv.id} <!-- Display the name of the CV -->
                </option>
            </c:forEach>
        </select>
        
        <br><br>
        <!-- Submit button to save changes -->
        <button type="submit">Save Changes</button>
    </form:form>
    
    <br><br>
    <!-- Cancel button to go back to the previous page -->
    <a href="javascript:history.back()">Cancel</a>
</body>
</html>
