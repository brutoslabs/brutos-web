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
	    	
			<form class="form-horizontal" 
						method="post" 
						action="${pageContext.request.contextPath}/">
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input name="user.password" type="password" value="${user.password}" 
									class="form-control ${!empty exception.causes['user.password'] ? 'is-invalid' : ''}" 
									placeholder="Password">
								<c:forEach var="ex" items="${exception.causes['user.password']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Confirm Password</label>
							<div class="col-sm-10">
								<input name="user.confirmPassword"  type="password" value="${user.confirmPassword}"
								    placeholder="Confirm password"
									class="form-control ${!empty exception.causes['user.confirmPassword'] ? 'is-invalid' : ''}">
								<c:forEach var="ex" items="${exception.causes['user.confirmPassword']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>

						  <div class="form-group row">
							<div class="col-sm-2 control-label"></div>
							<div class="col-sm-10">
								<c:forEach var="ex" items="${exception.causes['user']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
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