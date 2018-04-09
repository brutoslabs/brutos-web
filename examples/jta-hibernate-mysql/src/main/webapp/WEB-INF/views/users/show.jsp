<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

	<head>
		<jsp:include page="../fragments/header.jsp"/>
	</head>

<body>

	<jsp:include page="../fragments/navigation.jsp"/>
	
    <div class="container">

		<c:if test="${!empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
	                                aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<strong>${msg}</strong>
			</div>
		</c:if>
	
		<h1>User Detail</h1>
		<br />
	
		<div class="row">
			<label class="col-sm-2">ID</label>
			<div class="col-sm-10">${user.id}</div>
		</div>
	
		<div class="row">
			<label class="col-sm-2">First Name</label>
			<div class="col-sm-10">${user.firstname}</div>
		</div>
	
		<div class="row">
			<label class="col-sm-2">Last Name</label>
			<div class="col-sm-10">${user.lastname}</div>
		</div>
    
   	</div>
	
	<jsp:include page="../fragments/footer.jsp"/>

</body>

</html>