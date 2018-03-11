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
				
				
						<input type="hidden" name="${user.id}">
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<input name="name" type="text" value="${user.name}" class="form-control" placeholder="Name">
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Email</label>
							<div class="col-sm-10">
								<input name="email" type="email" value="${user.email}" class="form-control">
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" name="user.password">				
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">confirm Password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" name="user.confirmPassword">				
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Address</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" value="${user.address}" name="user.address">
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Newsletter</label>
							<div class="col-sm-10">
								<div class="checkbox">
								  <label>
									<input type="checkbox" name="user.newsletter" ${user.newsletter? 'checked' : ''}>
								  </label>
								</div>
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Web Frameworks</label>
							<div class="col-sm-10">
			                   <c:forEach var="framework" items="${frameworksList}">
			                   		<label class="checkbox-inline">
										<input type="checkbox" name="user.newsletter" 
											${user.framework.contains(framework)? 'checked' : ''}>
			                   			${framework}
			                   		</label>
							  </c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Sex</label>
							<div class="col-sm-10">
								<label class="radio-inline">
									<input type="radio" name="sex" ${user.sex == 'M'? 'checked' : ''} value="M"> Male
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" ${user.sex == 'F'? 'checked' : ''} value="F"> Female
								</label>
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Number</label>
							<div class="col-sm-10">
			                   <c:forEach var="number" items="${numberList}">
			                   		<label class="radio-inline">
										<input type="radio" name="user.number" 
											${number == user.number? 'checked' : ''}>
										${number}
									</label>
							  </c:forEach>
							</div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Country</label>
							<div class="col-sm-5">
								<select name="country" class="form-control">
									<option value="">--- Select ---</option>
				                   	<c:forEach var="country" items="${countryList}">
										<option value="${country.key}" ${user.country == country.key? 'selected' : ''}>${country.value}</option>
								  	</c:forEach>
								</select>
							</div>
							<div class="col-sm-5"></div>
						  </div>
				
						  <div class="form-group row ${!empty exception ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Java Skills</label>
							<div class="col-sm-5">
								<select name="skill" multiple size="5" class="form-control">
				                   <c:forEach var="skill" items="${javaSkillList}">
									<option value="${skill.key}" ${user.skill.contains(skill.key)? 'selected' : ''}>${skill.value}</option>
								  </c:forEach>
								</select>
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