<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">

<head>
<title>Login Page</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Reference Bootstrap files -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
	<div>
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">Sign In</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">
					<!-- Login Form -->
					<form
						action="${pageContext.request.contextPath}/authenticateTheUser"
						method="POST" class="form-horizontal">
						<!-- Place for messages: error, alert etc ... -->
						<div class="form-group">
							<div class="col-xs-12">
								<div>
									<c:if test="${not empty param.error}">
                                        <div class="alert alert-danger">
                                            Invalid username and password.
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty param.logout}">
                                        <div class="alert alert-success">
                                            You have been logged out.
                                        </div>
                                    </c:if>

								</div>
							</div>
						</div>
						
						<!-- User name -->
						<div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input type="text" name="email" placeholder="email" class="form-control" required>
                        </div>
                		
                		<!-- Password -->
                        <div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input type="password" name="password" placeholder="password" class="form-control" required>
                        </div>

						<!-- Login/Submit Button -->
						<div style="margin-top: 10px" class="form-group">
							<div class="col-sm-12 controls">
								<button type="submit" class="btn btn-success">Login</button>
							</div>
						</div>
						<div>
							<a
								href="${pageContext.request.contextPath}/register/showRegistrationForm"
								class="btn btn-primary" role="button" aria-pressed="true">Register
								New User</a>
						</div>
						<!-- CSRF Token -->
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- Error handling script -->
    <script>
        // Check for 'error' parameter
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('error')) {
            alert('Invalid email or password. Please try again.');
        }
    </script>
</body>
</html>