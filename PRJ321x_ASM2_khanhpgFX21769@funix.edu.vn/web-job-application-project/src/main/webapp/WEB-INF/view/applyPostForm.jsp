<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Apply for Recruitment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        function setFileNameText() {
            // Get the selected option text
            var cvSelect = document.getElementById("cvId");
            var selectedOption = cvSelect.options[cvSelect.selectedIndex].text;

            // Set the value of the hidden input to the selected option text
            document.getElementById("fileNameText").value = selectedOption;
        }
    </script>
</head>

<body>
    <h1>Apply for ${recruitment.title} at ${recruitment.company.nameCompany}</h1>
    <form:form modelAttribute="applyPost"
        action="${pageContext.request.contextPath}/applyPost/submitApplyPost"
        method="POST" onsubmit="setFileNameText()">
        
        <input type="hidden" name="recruitmentId" value="${recruitment.id}" />
        <input type="hidden" name="userId" value="${user.id}" />
        
        <!-- Table Layout -->
        <table>
            <tr>
                <td><label for="cvId">Select CV</label></td>
                <td>
                    <select id="cvId" name="cvId" required>
                        <c:choose>
                            <c:when test="${not empty user.cvs}">
                                <c:forEach var="cv" items="${user.cvs}">
                                    <option value="${cv.id}">${cv.id}</option>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option value="">No CVs available</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </td>
            </tr>
            
            <tr>
                <td><label for="status">Application Status</label></td>
                <td><input type="text" id="status" name="status" value="1" readonly /></td>
            </tr>

            <tr>
                <td><label for="createdAt">Application Date:</label></td>
                <td><input type="date" id="createdAt" name="createdAt" value="${applyPost.createdAt}" /></td>
            </tr>

            <tr>
                <td><input type="hidden" id="fileNameText" name="fileNameText" /></td>
                <td></td>
            </tr>

            <tr>
                <td><label for="text">Additional Information:</label></td>
                <td><textarea id="text" name="text">${applyPost.text}</textarea></td>
            </tr>

            <tr>
                <td colspan="2" style="text-align: center;">
                    <button type="submit">Submit Application</button>
                </td>
            </tr>
        </table>
    </form:form>
    
    <div>
        <a href="${pageContext.request.contextPath}/?userId=${user.id}" class="button">Back to Home</a>
    </div>
    
</body>
</html>
