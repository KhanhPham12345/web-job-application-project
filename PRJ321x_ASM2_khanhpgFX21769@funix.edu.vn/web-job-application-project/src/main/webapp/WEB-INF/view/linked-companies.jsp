<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Companies with Linked Recruitments</title>
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h2>Companies with Linked Recruitments</h2>

    <c:if test="${not empty linkedCompanies}">
        <table border="1" cellpadding="10">
            <thead>
                <tr>
                    <th>Company Name</th>
                    <th>Address</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="company" items="${linkedCompanies}">
                    <tr>
                        <!-- Company name linked to companyDetails page -->
                        <td>
                            <a href="${pageContext.request.contextPath}/companies/companyDetails?companyId=${company.id}&userId=${user.id}">
                                <strong>${company.nameCompany}</strong>
                            </a>
                        </td>
                        <td>${company.address}</td>
                        
                        <!-- Action links for editing and deleting -->
                        <td>
                            <a href="${pageContext.request.contextPath}/companies/editCompany?companyId=${company.id}&userId=${user.id}">Edit</a>
                            |
                            <a href="${pageContext.request.contextPath}/companies/deleteCompany?companyId=${company.id}&userId=${user.id}" 
                               onclick="return confirm('Are you sure you want to delete this company?');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty linkedCompanies}">
        <p>No companies with recruitments linked to this user.</p>
    </c:if>
</body>
</html>

