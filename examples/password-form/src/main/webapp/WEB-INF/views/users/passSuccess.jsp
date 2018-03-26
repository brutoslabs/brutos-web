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
					<h1>Brutos form password example</h1>
                </div>
	    	</div>

		  <div class="form-group row">
			<label class="col-sm-2 control-label">Password</label>
			<div class="col-sm-10">
				${user.password}
			</div>
		  </div>

		  <div class="form-group row">
			<label class="col-sm-2 control-label">Confirm Password</label>
			<div class="col-sm-10">
				${user.confirmPassword}
			</div>
		  </div>
    	</section>
    	
    </div>

	<jsp:include page="../fragments/footer.jsp"/>

</body>

</html>