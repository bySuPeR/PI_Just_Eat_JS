<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang = es>
    <head>
        <meta charset = "UTF-8">
        <title>Just Eat</title>
        <link rel = "stylesheet" href = "css/general.css">
        <link rel = "stylesheet" href = "css/search.css">
    </head>
    <body>
        <div class = "full-screen-container">
            <div class = "main-page-container">
            	<a class = "pages-link" id = "account-link" href = "<c:url value = 'UpdateUserServlet.do'/>">Account</a>
                <a class = "pages-link" id = "my-restaurants-link" href = "<c:url value = 'MyRestaurantsServlet.do'/>">My Rests.</a>
                <a class = "pages-link" id = "my-orders-link" href = "<c:url value = 'MyOrdersServlet.do?id=${user.id}'/>">My orders</a>
                <div class = "pic-container">
                	<div class = "search-input-container">
                        <h1>Search for our best restaurants!</h1>
                        <form class = "form" action = "SearchServlet.do" method = "post">
                            <input type = "text" name = "search">
                            <input type = "submit" value = "Search">
                        </form>
                    </div>
                </div>
                <div class = "category-prev-text">
                    <h2>WHATEVER YOU WANT</h2>
                    <h4>Get what you want from our best restaurants</h4>
                </div>
                <div class = "categories-list">
                	<c:forEach var = "category" items = "${categoryList}">
                		<form action = "CategoryServlet.do?idC=${category.id}" method = "post">
                				<input type = "submit" class = "single-category" value = "${category.name}">
	                	</form>
                	</c:forEach>
                </div>
                <form class = "restaurant-state-radio" action = "StateServlet.do" method = "post">
                	<div class = "state-entry">
                		<label class = "state-label" for = "state-value">All restaurants</label>
                		<input type = "radio" value = "all-restaurants" name = "state-value" ${allrestaurants}>
                	</div>
                	<div class = "state-entry">
                		<label class = "state-label" for = "state-value">Take orders</label>
                		<input type = "radio" value = "take-orders" name = "state-value" ${takeorders}>
                	</div>
                	<div class = "state-entry">
                		<label class = "state-label" for = "state-value">No take orders</label>
                		<input type = radio value = "no-take-orders" name = "state-value" ${notakeorders}>
                	</div>
                	<div class = "state-entry">
                		<label class = "state-label" for = "state-value">Sort on reviews</label>
                		<input type = "radio" value = "reviews" name = "state-value" ${reviews}>
                	</div>
                	<div class = "state-entry">
                		<label class = "state-label" for = "state-value">My Favorites</label>
                		<input type = "radio" value = "myfavorites" name = "state-value" ${myfavorites}>
                	</div>
                	<input type = "submit" value = "Search" class = "search-state">
                </form>
                <div class = "restaurant-grid-container">
                	<c:forEach var = "restaurant" items = "${restaurantList}">
                		<div class = "single-restaurant">
                        	<a href = "RestaurantDetailsServlet.do?id=${restaurant.id}"><img src = "images/${restaurant.restaurantPic}" class = "restaurant-photo"></a>
                        	<h3>${restaurant.name}</h3>
                    	</div>
                	</c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>