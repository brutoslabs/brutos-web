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
								<input name="user.name" type="text" value="${user.name}" 
									class="form-control ${!empty exception.causes['user.name'] ? 'is-invalid' : ''}" 
									placeholder="Name">
								<c:forEach var="ex" items="${exception.causes['user.name']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Email</label>
							<div class="col-sm-10">
								<input name="user.email" type="text" value="${user.email}" placeholder="Email"
									class="form-control ${!empty exception.causes['user.email'] ? 'is-invalid' : ''}">
								<c:forEach var="ex" items="${exception.causes['user.email']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Address</label>
							<div class="col-sm-10">
								<input type="text" name="user.address" placeholder="address" 
									class="form-control ${!empty exception.causes['user.address'] ? 'is-invalid' : ''}" 
									value="${user.address}">
								<c:forEach var="ex" items="${exception.causes['user.address']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Newsletter</label>
							<div class="col-sm-10">
			                   <div class="form-check form-check-inline">
									<input type="checkbox" name="user.newsletter" value="true" 
										${user.newsletter? 'checked' : ''}
										class="form-check-input ${!empty exception.causes['user.newsletter'] ? 'is-invalid' : ''}">
								</div>
								<c:forEach var="ex" items="${exception.causes['user.newsletter']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Web Frameworks</label>
							<div class="col-sm-10">
			                   <c:forEach var="framework" items="${frameworksList}">
								<div class="form-check form-check-inline">
  									<input type="checkbox" name="user.framework" value="${framework}" 
  										${user.framework.contains(framework)? 'checked' : ''} 
  										class="form-check-input ${!empty exception.causes['user.framework'] ? 'is-invalid' : ''}">
								  <label class="form-check-label">
										 ${framework}
								  </label>
								</div>			                   
							  </c:forEach>
								<c:forEach var="ex" items="${exception.causes['user.framework']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Sex</label>
							<div class="col-sm-10">
			                   <div class="form-check form-check-inline">
									<input type="radio" name="user.sex" value="M"
										${empty user || user.sex == 'M'? 'checked' : ''} 
										class="form-check-input ${!empty exception.causes['user.sex'] ? 'is-invalid' : ''}">
									<label class="form-check-label"> Male</label>
								</div>
			                   <div class="form-check form-check-inline">
									<input type="radio" name="user.sex" value="F" 
										${user.sex == 'F'? 'checked' : ''} 
										class="form-check-input ${!empty exception.causes['user.sex'] ? 'is-invalid' : ''}">
									<label class="form-check-label"> Female</label>
								</div>
								<c:forEach var="ex" items="${exception.causes['user.sex']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row">
							<label class="col-sm-2 control-label">Number</label>
							<div class="col-sm-10">
			                   <c:forEach var="number" items="${numberList}">
			                   <div class="form-check form-check-inline">
									<input type="radio" name="user.number" value="${number}"
										${number == user.number? 'checked' : ''}
										class="form-check-input ${!empty exception.causes['user.number'] ? 'is-invalid' : ''}">
			                   		<label class="form-check-label">
										${number}
									</label>
								</div>
							  </c:forEach>
								<c:forEach var="ex" items="${exception.causes['user.number']}">
									<div class="text-danger"><small>${ex.message}</small></div>
								</c:forEach>
							</div>
						  </div>
				
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