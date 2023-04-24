<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang = "es">
    <head>
        <meta charset = "UTF-8">
        <title>Just Eat</title>
        <link rel = "stylesheet" href = "css/general.css">
        <link rel = "stylesheet" href = "css/login.css">
        <link rel = "stylesheet" href = "css/update-restaurant.css">
    </head>
    <body>
        <div class = "full-screen-container">
            <div class = "login-container">
                <h1 class = "login-title">Create your restaurant</h1>
                <form class = "form" action = "AddRestaurantServlet.do" method = "post">
                    <div class = "input-group">
                        <label for = "name">Name</label>
                        <input type = "text" name = "name" required>
                    </div>
                    <div class = "input-group">
                        <label for = "address">Address</label>
                        <textarea name = "address" required></textarea>
                    </div>
                    <div class = "input-group">
                    	<label for = "telephone">Telephone</label>
                    	<input type = "tel" name = "telephone" required>
                    </div>
                    <div class = "input-group">
                    	<label for = "city">City</label>
                    	<input type = "text" name = "city" required>
                    </div>
                    <div class = "input-group">
                        <label for = "contactEmail">Email</label>
                        <input type = "email" name = "contactEmail" id = "email" required>
                    </div>
                    <div class = "input-group">	
  						<label for="image">Take a picture: </label>
  						<input type="file" id="image" name="image">
                    </div>
                    <div class = "input-group">
                    	<h2 class = "login-title" id = "bike-parking-question">What kind of food do you serve?</h2>
                    	<c:forEach var = "category" items = "${categoryList}">
                    		<div class = "category-container">
                    			<label for = "category-checkbox" class = "features-text">${category.name}</label>
                    			<input type = "checkbox" value = "${category.name}" name = "selectedCategory">	
                    		</div>
                    	</c:forEach>
                    </div>
                    <div class = "input-group">
                    	<h2 class = "login-title" id = "bike-parking-question">Do you have a bike parking lot?</h2>
                    	<div class = "bike-friendly-radio">
                        	<label for = "bike-friendly" class = "features-text">Yes</label>
                        	<input type = "radio" value = "yes-bike-friendly-value" name = "bike-friendly">
                    	</div>
                    	<div class = "bike-friendly-radio">
                    		<label for = "bike-friendly" class = "features-text">No</label>
	    	                <input type = "radio" value = "no-bike-friendly-value" name = "bike-friendly">
                    	</div>
                    </div>
                    <div class = "input-group">
                    	<h2 class = "login-title">Can you take orders?</h2>
                    	<div class = "available-radio">
                    		<label for = "available" class = "features-text">Yes</label>
                    		<input type = "radio" value = "yes-available" name = "available">
                    	</div>
                    	<div class = "bike-friendly-radio">
                    		<label for = "available" class = "features-text">No</label>
                    		<input type = "radio" value = "no-available" name = "available">
                    	</div>
                    </div>
                    <button type = "submit" class = "login-button">Create restaurant</button>
                    <a class = "delete-link" href = "<c:url value = "MyRestaurantsServlet.do"/>">Go back to my restaurants page</a>
                </form>
                <p class = error>${requestScope.error}</p>
            </div>
        </div>
    </body>
</html>