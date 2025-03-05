<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Application Submitted</title>
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1>Thank you for applying!</h1>
    <p>Your application has been submitted successfully.</p>
    
    <form:form action="${pageContext.request.contextPath}/" method="GET" modelAttribute="user">
		<input type="hidden" name="userId" value="${user.id}" />
		<button type="submit">Back to Home</button>
	</form:form>
	
	<br>
	
	<form:form action="${pageContext.request.contextPath}/showMyLoginPage" method="GET">
		<button type="submit">Logout</button>
	</form:form>
</body>
</html>
