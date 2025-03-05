<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
	<title>luv2code - Access Denied</title>
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
	<h2>Access Denied - You are not authorized to access this resource </h2>
	<div>
		<p>This is also a landing page for any errors</p>
	</div>
	<hr>
	<a href="${pageContext.request.contextPath}/showMyLoginPage">Log out</a>
</body>

</html>