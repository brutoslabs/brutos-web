<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<script type="text/javascript">
	function showServiceData() {
		var s = document.getElementById("serviceType");
		var type = s.options[s.selectedIndex].value;
		if (type == "air") {
			document.getElementById("air").style.display = "";
			document.getElementById("hosting").style.display = "none";
		} else {
			document.getElementById("air").style.display = "none";
			document.getElementById("hosting").style.display = "";
		}
	}
</script>
<body onload="javascript:showServiceData()">
	<h2>Polymorphic Mapping Example</h2>
	<hr>
	<a href="${pageContext.servletContext.contextPath}/sales"><h3>Back</h3></a>
	<form method="POST"
		action="${pageContext.servletContext.contextPath}/sales/save">
		<table>
			<tbody>
				<tr>
					<td>Id</td>
					<td>${entity.id}</td>
				</tr>
				<tr>
					<td>Price</td>
					<td><input type="text" name="entity.price"
						value="${empty entity.price? '0' : entity.price}"></td>
				</tr>
				<tr>
					<td>Service type</td>
					<td><select id="serviceType" name="entity.service.serviceType"
						onchange="javascript:showServiceData()">
							<option ${entity.service.serviceType == 'air'? "selected " : ""} 
							    value="air">Air</option>
							<option ${entity.service.serviceType == 'hosting'? "selected " : ""} 
							    value="hosting">Hosting</option>
					</select></td>
				</tr>
				<tr id="air">
					<td>
						<table width="100%">
							<tbody>
								<tr>
									<td>Airplane</td>
									<td><input type="text" name="entity.service.airplane"
										value="${entity.service.serviceType != 'air'? null : entity.service.airplane}"></td>
								</tr>
								<tr>
									<td>Departure date</td>
									<td>
									 <input type="text" name="entity.service.departureDate"
									  value="<fmt:formatDate 
										value="${entity.service.serviceType != 'air'? null : entity.service.departureDate}" 
										  pattern="yyyy-MM-dd hh:mm" />"></td>
								</tr>
								<tr>
									<td>Arrival date</td>
									<td><input type="text" name="entity.service.arrivalDate"
										value="<fmt:formatDate 
										  value="${entity.service.serviceType != 'air'? null : entity.service.arrivalDate}" 
										   pattern="yyyy-MM-dd hh:mm" />"></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr id="hosting">
					<td>
						<table width="100%">
							<tbody>
								<tr>
									<td>Hotel</td>
									<td><input type="text" name="entity.service.hotel"
										value="${entity.service.serviceType != 'hosting'? null : entity.service.hotel}"></td>
								</tr>
								<tr>
									<td>Checkin</td>
									<td><input type="text" name="entity.service.checkin"
										value="<fmt:formatDate 
										  value="${entity.service.serviceType != 'hosting'? null : entity.service.checkin}" 
										   pattern="yyyy-MM-dd" />"></td>
								</tr>
								<tr>
									<td>Checkout</td>
									<td><input type="text" name="entity.service.checkout"
										value="<fmt:formatDate 
										  value="${entity.service.serviceType != 'hosting'? null : entity.service.checkout}" 
										  pattern="yyyy-MM-dd" />"></td>
								</tr>
								<tr>
									<td>Mealplan</td>
									<td><input type="text" name="entity.service.mealPlan"
										value="${entity.service.serviceType != 'hosting'? null : entity.service.mealPlan}">
							  	   </td>
								</tr>
								<tr>
									<td>Room</td>
									<td><input type="text" name="entity.service.room"
										value="${entity.service.serviceType != 'hosting'? null : entity.service.room}">
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td><c:if test="${!empty entity.id}">
							<input type="hidden" name="entity.id" value="${entity.id}">
						</c:if> <input type="submit" value="Submit"></td>
				</tr>
			</tfoot>

		</table>
	</form>
</body>
</html>