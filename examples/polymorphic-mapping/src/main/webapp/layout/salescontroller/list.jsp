<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<body>
	<h2>Polymorphic Mapping Example</h2>
	<hr>
	<table>
		<thead>
			<tr>
				<th>#ID</th>
				<th>Date</th>
				<th>Description</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${Controller.all}" var="item">
				<tr>
					<th>${item.id}</th>
					<th><fmt:formatDate value="${item.date}" pattern="yyyy-MM-dd" /></th>
					<th>${item.service}</th>
					<th><a
						href="${pageContext.servletContext.contextPath}/sales/show/${item.id}">Edit</a>
						<a
						href="${pageContext.servletContext.contextPath}/sales/remove/${item.id}">Remove</a>
					</th>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td><a href="${pageContext.servletContext.contextPath}/sales/new">
						<h3>New</h3></a></td>
			</tr>
		</tfoot>
	</table>
</body>
</html>