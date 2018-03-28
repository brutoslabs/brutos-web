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
					<h1>Contacts</h1>
                </div>
	    	</div>
	    	
			<form class="form-horizontal" 
						method="post" 
						action="${pageContext.request.contextPath}/contacts">
				
				<c:forEach items="${contacts}" var="contact" varStatus="status">
				  <div class="form-group row">
						<div class="col-sm-3">
							<input name="contacts[${status.index}].firstname" type="text" value="${contact.firstname}" 
								class="form-control" placeholder="First name">
						</div>
						<div class="col-sm-3">
							<input name="contacts[${status.index}].lastname" type="text" value="${contact.lastname}" 
								class="form-control" placeholder="Last name">
						</div>
						<div class="col-sm-3">
							<input name="contacts[${status.index}].email" type="text" value="${contact.email}" 
								class="form-control" placeholder="Email">
						</div>
						<div class="col-sm-3">
							<input name="contacts[${status.index}].phone" type="text" value="${contact.phone}" 
								class="form-control" placeholder="Phone">
						</div>
				  </div>
				</c:forEach>
				<c:forEach begin="${fn:length(contacts)}" end="${fn:length(contacts) + 1}" varStatus="status">
				  <div class="form-group row">
						<div class="col-sm-3">
							<input name="contacts[${status.index}].firstname" type="text" value="" 
								class="form-control" placeholder="First name">
						</div>
						<div class="col-sm-3">
							<input name="contacts[${status.index}].lastname" type="text" value="" 
								class="form-control" placeholder="Last name">
						</div>
						<div class="col-sm-3">
							<input name="contacts[${status.index}].email" type="text" value="" 
								class="form-control" placeholder="Email">
						</div>
						<div class="col-sm-3">
							<input name="contacts[${status.index}].phone" type="text" value="" 
								class="form-control" placeholder="Phone">
						</div>
				  </div>
				</c:forEach>
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