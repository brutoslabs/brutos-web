<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>

	<head>
		<jsp:include page="../fragments/header.jsp"/>
	</head>

<body>

	<jsp:include page="../fragments/navigation.jsp"/>
	
    <div class="container">
    	<section>
	    	<div class="col-lg-8 col-lg-offset-2">
	    		<div class="page-header">
					<h1>Contact</h1>
                </div>
	    	</div>
			<c:forEach items="${contactForm}" var="contactForm">
				  <div class="form-group row">
					<label class="col-sm-2 control-label">${contactForm.key}</label>
					<div class="col-sm-10">
						${contactForm.value}
					</div>
				  </div>
			
			</c:forEach>
    	</section>
    	
    </div>

	<jsp:include page="../fragments/footer.jsp"/>

</body>

</html>