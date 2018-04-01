<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

	<head>
		<jsp:include page="../fragments/header.jsp"/>
	</head>

<body>

	<jsp:include page="../fragments/navigation.jsp"/>
	
    <div class="container">

		<h1>Brutos MVC form select, option, options example</h1>
		<br />
	
		<div class="row">
			<label class="col-sm-2">Country</label>
			<div class="col-sm-10">${user.country}</div>
		</div>
	
		<div class="row">
			<label class="col-sm-2">Skill</label>
			<div class="col-sm-10">${user.skill}</div>
		</div>
    
   	</div>
	
	<jsp:include page="../fragments/footer.jsp"/>

</body>

</html>