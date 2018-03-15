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
							<label class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<input name="user.name" type="text" value="${user.name}" class="form-control ${!empty exception ? 'is-invalid' : ''}" placeholder="Name">
								<c:forEach var="ex" items="${exception.causes['user.id']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Email</label>
							<div class="col-sm-10">
								<input name="user.email" type="email" value="${user.email}" class="form-control ${!empty exception ? 'is-invalid' : ''}">
								<c:forEach var="ex" items="${exception.causes['user.email']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control ${!empty exception ? 'is-invalid' : ''}" name="user.password">				
								<c:forEach var="ex" items="${exception.causes['user.password']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">confirm Password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control ${!empty exception ? 'is-invalid' : ''}" name="user.confirmPassword">				
								<c:forEach var="ex" items="${exception.causes['user.confirmPassword']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Address</label>
							<div class="col-sm-10">
								<input type="text" class="form-control ${!empty exception ? 'is-invalid' : ''}" value="${user.address}" name="user.address">
								<c:forEach var="ex" items="${exception.causes['user.address']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Newsletter</label>
							<div class="col-sm-10">
								<div class="checkbox ${!empty exception ? 'is-invalid' : ''}">
									<input type="checkbox" name="user.newsletter" value="true" ${user.newsletter? 'checked' : ''}>
								</div>
								<c:forEach var="ex" items="${exception.causes['user.newsletter']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Web Frameworks</label>
							<div class="col-sm-10">
			                   <c:forEach var="framework" items="${frameworksList}">
			                   		<label class="checkbox-inline ${!empty exception ? 'is-invalid' : ''}">
										<input type="checkbox" name="user.framework" value="${framework}" 
											${user.framework.contains(framework)? 'checked' : ''}>
			                   			${framework}
			                   		</label>
							  </c:forEach>
								<c:forEach var="ex" items="${exception.causes['user.framework']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Sex</label>
							<div class="col-sm-10">
								<label class="radio-inline ${!empty exception ? 'is-invalid' : ''}">
									<input type="radio" name="user.sex" ${user.sex == 'M'? 'checked' : ''} value="M"> Male
								</label>
								<label class="radio-inline ${!empty exception ? 'is-invalid' : ''}">
									<input type="radio" name="user.sex" ${user.sex == 'F'? 'checked' : ''} value="F"> Female
								</label>
								<c:forEach var="ex" items="${exception.causes['user.sex']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Number</label>
							<div class="col-sm-10">
			                   <c:forEach var="number" items="${numberList}">
			                   		<label class="radio-inline ${!empty exception ? 'is-invalid' : ''}">
										<input type="radio" name="user.number" value="${number}"
											${number == user.number? 'checked' : ''}>
										${number}
									</label>
							  </c:forEach>
								<c:forEach var="ex" items="${exception.causes['user.number']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Country</label>
							<div class="col-sm-5">
								<select name="user.country" class="form-control ${!empty exception ? 'is-invalid' : ''}">
									<option value="">--- Select ---</option>
				                   	<c:forEach var="country" items="${countryList}">
										<option value="${country.key}" ${user.country == country.key? 'selected' : ''}>${country.value}</option>
								  	</c:forEach>
								</select>
								<c:forEach var="ex" items="${exception.causes['user.country']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
							<div class="col-sm-5"></div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Java Skills</label>
							<div class="col-sm-5">
								<select name="user.skill" multiple size="5" class="form-control ${!empty exception ? 'is-invalid' : ''}">
				                   <c:forEach var="skill" items="${javaSkillList}">
									<option value="${skill.key}" ${user.skill.contains(skill.key)? 'selected' : ''}>${skill.value}</option>
								  </c:forEach>
								</select>
								<c:forEach var="ex" items="${exception.causes['user.skill']}">
									<small class="invalid-feedback">${ex.message}</small>
								</c:forEach>
							</div>
							<div class="col-sm-5"></div>
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
    	
		<jsp:include page="../fragments/footer-container.jsp"/>
    	
    </div>

	<jsp:include page="../fragments/footer.jsp"/>

</body>

</html>