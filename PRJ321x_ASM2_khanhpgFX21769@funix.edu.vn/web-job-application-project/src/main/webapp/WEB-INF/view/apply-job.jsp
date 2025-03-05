<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>

<head>
	<title>Save User's CV</title>
</head>

<body> 
	<div id="wrapper">
		<div id="header">
			<h2> Input user's cv here </h2>
		</div>
		
		<div id="container">
			<h3> Save user's cv </h3>
			
			<form:form action="saveCvpost" modelAttribute="user" method="POST">
				
				<form:hidden path="id"/>
				
				<table>
					<tbody>
						<tr>
							<td><label>Full name:</label></td>
							<td>
								<form:input path="fullName"/>
								<form:errors path="fullName" cssClass="error"/>
							</td>
						</tr>
						
						<tr>
							<td><label>Email:</label></td>
							<td>
								<form:input path="email"/>
								<form:errors path="email" cssClass="error"/>
							</td>
						</tr>
						
						<tr>
							<td><label>Address:</label></td>
							<td>
								<form:input path="address"/>
								<form:errors path="address" cssClass="error"/>
							</td>
						</tr>
						
						<tr>
							<td><label>Phone number:</label></td>
							<td>
								<form:input path="phoneNumber"/>
								<form:errors path="phoneNumber" cssClass="error"/>
							</td>
						</tr>
						
						<tr>
							<td><label>Link to CV:</label></td>
							<td>
								<form:input path="cv"/>
								<form:errors path="cv" cssClass="error"/>
							</td>
						</tr>
						
						<tr>
							<td><label>Note:</label></td>
							<td><form:input path="note"/></td>
						</tr>
						
						<tr>
							<td><label></label></td>
							<td><input type="submit" value="Save" class="save"></td>
						</tr>
						
					</tbody>
				</table>
			</form:form>
			
			<div style="clear; both;"></div>
			<p>
				<a href="${pageContext.request.contextPath}/showMyLoginPage">Log out</a>
			</p>
			
		</div>
	</div>
</body>

</html>