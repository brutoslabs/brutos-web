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
					<c:choose>
						<c:when test="${empty user.id}">
							<h1>Add User</h1>
						</c:when>
						<c:otherwise>
							<h1>Update User</h1>
						</c:otherwise>
					</c:choose>
                </div>
	    	</div>
	    	
			<form class="form-horizontal" 
						method="post" 
						action="${pageContext.request.contextPath}/users">
				
				
						<input name="user.id" type="hidden" name="user.id" value="${user.id}">
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">First Name</label>
							<div class="col-sm-10">
								<input name="user.firstname" type="text" value="${user.firstname}" 
									class="form-control ${!empty exception.causes['user.firstname'] ? 'is-invalid' : ''}" 
									placeholder="First Name">
								<c:forEach var="ex" items="${exception.causes['user.firstname']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Last Name</label>
							<div class="col-sm-10">
								<input name="user.lastname" type="text" value="${user.lastname}" 
									class="form-control ${!empty exception.causes['user.lastname'] ? 'is-invalid' : ''}" 
									placeholder="Last Name">
								<c:forEach var="ex" items="${exception.causes['user.lastname']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>
						  
						<div class="form-group row">
						  <div class="col-sm-offset-2 col-sm-12">
							<c:choose>
							  <c:when test="${empty user.id}">
							     <button type="submit" class="btn-lg btn-primary float-right">Add
				                             </button>
							  </c:when>
							  <c:otherwise>
							     <button type="submit" class="btn-lg btn-primary float-right">Update
				                             </button>
							  </c:otherwise>
							</c:choose>
						  </div>
						</div>
				
			</form>	    	
    	</section>
    	
    </div>

	<jsp:include page="../fragments/footer.jsp"/>

</body>

</html>