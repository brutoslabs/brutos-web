<%@page session="false" pageEncoding="UTF-8"%>
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
	    	
			<form class="form-horizontal" 
						method="post" 
						action="${pageContext.request.contextPath}/contact">
				
				  <div class="form-group row">
						<div class="col-sm-3">
							<input name="contactForm[firstname]" type="text" 
								value="Afonso"
								class="form-control" placeholder="First name">
						</div>
						<div class="col-sm-3">
							<input name="contactForm[lastname]" type="text"  
								value="Brandao"
								class="form-control" placeholder="Last name">
						</div>
						<div class="col-sm-3">
							<input name="contactForm[email]" type="text"  
								value="afonso@brutosframework.com.br"
								class="form-control" placeholder="Email">
						</div>
						<div class="col-sm-3">
							<input name="contactForm[phone]" type="text"  value="123-123-123-123"
								class="form-control" placeholder="Phone">
						</div>
				  </div>

				<div class="form-group row">
				  <div class="col-sm-offset-2 col-sm-12">
				     <button type="submit" class="btn-lg btn-primary float-right">Submit</button>
				  </div>
				</div>

			</form>	    	
    	</section>
    	
    </div>

	<jsp:include page="../fragments/footer.jsp"/>

</body>

</html>