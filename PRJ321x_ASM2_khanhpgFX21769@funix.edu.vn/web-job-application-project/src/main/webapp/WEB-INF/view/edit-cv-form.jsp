<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
    <title>Edit CV</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Add script for AJAX upload -->
    <script>
        $(document).ready(function() {
            // Upload CV using AJAX
            $('#uploadCvForm').on('submit', function(event) {
                event.preventDefault(); // Prevent the form from submitting the traditional way

                // Get CSRF token and header name from meta tags
                var csrfToken = $('meta[name="_csrf"]').attr('content');
                var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

                // Create a FormData object to hold the file and form data
                var formData = new FormData(this);

                // Send the AJAX request to upload the CV
                $.ajax({
                    url: $(this).attr('action'),  // Get the form action URL
                    type: 'POST',
                    data: formData,
                    processData: false,  // Prevent jQuery from processing the data
                    contentType: false,  // Set content type to false as we're sending FormData
                    beforeSend: function(xhr) {
                        // Add the CSRF token to the request header
                        xhr.setRequestHeader(csrfHeader, csrfToken);
                    },
                    success: function(response) {
                        alert("CV uploaded successfully!");
                        // Optionally redirect or update UI here
                    },
                    error: function(xhr, status, error) {
                        alert("Error uploading CV: " + xhr.responseText); // Log the full error response
                    }
                });
            });
        });
    </script>
</head>

<body>

    <h2>Edit CV</h2>

    <form:form id="uploadCvForm" action="${pageContext.request.contextPath}/update-cv" method="POST" enctype="multipart/form-data" modelAttribute="cv">
		<input type="hidden" name="id" value="${cv.id}" />
		
        <!-- Add a file input field for the new CV -->
        <label for="cvFile">Upload New CV (PDF):</label>
        <input type="file" id="cvFile" name="cvFile" accept="application/pdf" required />
        <br>

        <!-- Hidden field for userId -->
        <input type="hidden" name="userId" value="${userId}" />

        <input type="submit" value="Update CV" />
   </form:form>

</body>
</html>
