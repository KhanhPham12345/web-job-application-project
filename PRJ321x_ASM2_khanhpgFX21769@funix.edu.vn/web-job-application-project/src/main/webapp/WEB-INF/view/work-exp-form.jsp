<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Work Experience Form</title>
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h2>Work Experience Form</h2>
        </div>
        
        <div id="container">
            <h3>${workexperience.id != null ? "Edit Work Experience" : "Add New Work Experience"}</h3>

            <!-- Form for WorkExperience with modelAttribute "workexperience" -->
            <form:form action="${pageContext.request.contextPath}/workexp/save" modelAttribute="workexperience" method="POST">
                
                <!-- Include userId as a hidden field for new or updated records -->
                <form:hidden path="id"/>
                <input type="hidden" name="userId" value="${userId}" />

                <table>
                    <tbody>
                        <tr>
                            <td><label for="companyName">Company Name:</label></td>
                            <td>
                                <form:input path="companyName" />
                                <form:errors path="companyName" cssClass="error" />
                            </td>
                        </tr>

                        <tr>
                            <td><label for="position">Position:</label></td>
                            <td>
                                <form:input path="position" />
                                <form:errors path="position" cssClass="error" />
                            </td>
                        </tr>

                        <tr>
                            <td><label for="startDate">Start Date:</label></td>
                            <td>
                                <form:input path="startDate" type="date" />
                                <form:errors path="startDate" cssClass="error" />
                            </td>
                        </tr>

                        <tr>
                            <td><label for="endDate">End Date:</label></td>
                            <td>
                                <form:input path="endDate" type="date" />
                                <form:errors path="endDate" cssClass="error" />
                            </td>
                        </tr>

                        <tr>
                            <td><label for="description">Description:</label></td>
                            <td>
                                <form:textarea path="description" rows="4" cols="50"></form:textarea>
                                <form:errors path="description" cssClass="error" />
                            </td>
                        </tr>

                        <tr>
                            <td colspan="2">
                                <input type="submit" value="${workexperience.id != null ? 'Update' : 'Save'}" class="action-button" />
                                <a href="${pageContext.request.contextPath}/?userId=${userId}" class="action-button">Cancel</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form:form>
        </div>
    </div>
</body>
</html>
