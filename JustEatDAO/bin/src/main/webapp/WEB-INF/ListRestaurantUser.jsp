<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html id="list">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/stylesheet.css"   />
		<title>List of Restaurants</title>
	</head>
	<body>
		<h1> Different Lists:</h1> 
		<h2> List of Restaurants:</h2>
		<table>
			<thead>
				<tr>
					<th>Name</th>
					<th>Address</th>
					<th>Telephone</th>
					<th>City</th>
					<th>Min Price</th>
					<th>Max Price</th>
					<th>Punctuation</th>
					<th>Contact Email</th>
					<th>Bike Friendly</th>
					<th>Available</th>
					<th>User</th>
					<th>Category Ids</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="restaurant" items="${restaurantsList}">
					<tr> 
						<td>${restaurant.first.name}</td>
						<td>${restaurant.first.address}</td>
						<td>${restaurant.first.telephone}</td>
						<td>${restaurant.first.city}</td>
						<td>${restaurant.first.minPrice}</td>
						<td>${restaurant.first.maxPrice}</td>
						<td>${restaurant.first.gradesAverage}</td>
						<td>${restaurant.first.contactEmail}</td>
						<td>
						<c:choose>
		    				<c:when test="${restaurant.first.bikeFriendly=='1'}">
		    					Yes
		    				</c:when>
		    				<c:otherwise>
		    					No
		    				</c:otherwise>	
		    			</c:choose>
						</td>
						<td>
						<c:choose>
		    				<c:when test="${restaurant.first.available=='1'}">
		    					Yes
		    				</c:when>
		    				<c:otherwise>
		    					No
		    				</c:otherwise>	
		    			</c:choose>
						</td>

						<td>${restaurant.second.name}</td>
						<td> <c:forEach var="category" items="${restaurant.third}">
							${category.idct} - 		    	
							</c:forEach>
						</td>
						
					</tr>	
		    	</c:forEach>
		    	</tbody>	
			</table>	
		<h2> List of Restaurant by users:</h2>
		<c:forEach var="restaurantsByUser" items="${usersMap}"> 
			<h3>User: ${restaurantsByUser.key.name}</h3>
			<h3>Email: ${restaurantsByUser.key.email}</h3>
			<c:forEach var="restaurant" items="${restaurantsByUser.value}">
				<div>
					<h4>${restaurant.name}</h4>
		    		<p>${restaurant.address}</p> 
		    		<p>${restaurant.telephone}</p> 
		    		<p>${restaurant.city}</p>
		    		<p>${restaurant.minPrice}</p>
		    		<p>${restaurant.maxPrice}</p>
		    		<p>${restaurant.gradesAverage}</p> 
		    		<p>${restaurant.contactEmail}</p>
		    		
		    		<p>
		    			<c:choose>
		    				<c:when test="${restaurant.bikeFriendly=='1'}">
		    					BikeFriendly
		    				</c:when>
		    				<c:otherwise>
		    					Not BikeFriendy
		    				</c:otherwise>	
		    			</c:choose>
					</p> 
		    		<p>
		    			<c:choose>
		    				<c:when test="${restaurant.available=='1'}">
		    					Available
		    				</c:when>
		    				<c:otherwise>
		    					Not Available
		    				</c:otherwise>	
		    			</c:choose>
					</p> 

				</div>	
		    </c:forEach>
	    </c:forEach>
	</body>
</html>
