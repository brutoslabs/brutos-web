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
					<h1>Brutos MVC form select, option, options example</h1>
                </div>
	    	</div>
	    	
			<form class="form-horizontal" 
						method="post" 
						action="${pageContext.request.contextPath}/">
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Country</label>
							<div class="col-sm-5">
								<select name="user.country" class="form-control ${!empty exception.causes['user.country'] ? 'is-invalid' : ''}">
									<option value="">--- Select ---</option>
				                   	<c:forEach var="country" items="${countryList}">
										<option value="${country.key}" ${user.country == country.key? 'selected' : ''}>${country.value}</option>
								  	</c:forEach>
								</select>
								<c:forEach var="ex" items="${exception.causes['user.country']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
							<div class="col-sm-5"></div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Java Skills</label>
							<div class="col-sm-5">
								<select name="user.skill" multiple size="5" class="form-control ${!empty exception.causes['user.skill'] ? 'is-invalid' : ''}">
				                   <c:forEach var="skill" items="${javaSkillList}">
									<option value="${skill.key}" ${user.skill.contains(skill.key)? 'selected' : ''}>${skill.value}</option>
								  </c:forEach>
								</select>
								<c:forEach var="ex" items="${exception.causes['user.skill']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
							<div class="col-sm-5"></div>
						  </div>
				
						<div class="form-group row">
						  <div class="col-sm-offset-2 col-sm-12">
						     <button type="submit" class="btn-lg btn-primary float-right">Submit
			                             </button>
						  </div>
						</div>
				
			</form>	    	
    	</section>
    	
    </div>

	<jsp:include page="../fragments/footer.jsp"/>

</body>

</html>