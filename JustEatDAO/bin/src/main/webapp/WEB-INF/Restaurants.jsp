<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang = es>
    <head>
        <meta charset = "UTF-8">
        <title>Just Eat</title>
        <link rel = "stylesheet" href = "css/general.css">
        <link rel = "stylesheet" href = "css/restaurants.css">
    </head>
    <body>
        <div class = "full-screen-container">
            <div class = "main-page-container">
                <div class = "restaurants-grid">
                	<c:forEach var = "restaurant" items = "${restaurantList}">
                		<div class = "single-restaurant">
                        	<a href = "RestaurantDetailsServlet.do?id=${restaurant.id}"><img src = "images/canteria.jpg" class = "restaurant-photo"></a>
                        	<h3>${restaurant.name}</h3>
                    	</div>
                	</c:forEach>
                </div>
                <div class = "add-restaurant-div">
                	<a class = "add-restaurant" href = "<c:url value = 'AddRestaurantServlet.do'/>">Add restaurant</a>
                </div>
            </div>
        </div>
    </body>
</html>