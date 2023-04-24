<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang = es>
    <head>
        <meta charset = "UTF-8">
        <title>Just Eat</title>
        <link rel = "stylesheet" href = "css/general.css">
        <link rel = "stylesheet" href = "css/res_details.css">
    </head>
    <body>
        <div class = "full-screen-container">
            <div class = "main-page-container">
            	<div class = "restaurant-pic"></div>
                <input type = "hidden" name = "id" value = "${restaurant.id}">
				<div class = "restaurant-container">
					<h1 class = "restaurant-text">${restaurant.name}
						<span class = "number-value" id = "grade-average">${restaurant.gradesAverage}</span>
					</h1>
					<h1 class = "restaurant-text">${restaurant.contactEmail}</h1>
					<h1 class = "restaurant-text">${restaurant.telephone}</h1>
					<h1 class = "restaurant-text">${restaurant.address}, ${restaurant.city}</h1>
					<h1 class = "restaurant-text" id = "bike-parking-lot">Has bike parking lot?
						<span class = "restaurant-text">
							<c:if test = "${restaurant.bikeFriendly eq 1}">
								Yes!
							</c:if>
							<c:if test = "${restaurant.bikeFriendly eq 0}">
								Sorry, no :(
							</c:if>
						</span>
					</h1>
					<c:if test = "${restaurant.idu eq requestScope.currentUser.id}">
						<a class = "manage-restaurant" href = "<c:url value = 'UpdateRestaurantServlet.do?id=${restaurant.id}'/>">Manage restaurant</a>
					</c:if>
				</div>
				<div class = "dishes-container">
					<c:forEach var = "dish" items = "${dishList}">
						<div class = "individual-dish">
							<h2>${dish.name}
								<span class = "number-value">${dish.price}</span>
							</h2>
							<h3>${dish.description}</h3>
						</div>
					</c:forEach>
				</div>
				<a class = "manage-restaurant" id = "back-to-my-restaurants" href = "<c:url value = 'MyRestaurantsServlet.do'/>">Back to My Restaurants</a>
            </div>
        </div>
    </body>
</html>