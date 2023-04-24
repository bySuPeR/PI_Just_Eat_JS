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
                <h1 class = "login-title">Update your restaurant</h1>
                <form class = "form" action = "UpdateRestaurantServlet.do?idR=${restaurant.id}&idU=${currentUser.id}" method = "post">
                    <div class = "input-group">
                        <label for = "name">Name</label>
                        <input type = "text" name = "name" value = "${restaurant.name}" required>
                    </div>
                    <div class = "input-group">
                        <label for = "address">Address</label>
                        <textarea name = "address" required>${restaurant.address}</textarea>
                    </div>
                    <div class = "input-group">
                    	<label for = "telephone">Telephone</label>
                    	<input type = "tel" name = "telephone" value = "${restaurant.telephone}" required>
                    </div>
                    <div class = "input-group">
                    	<label for = "city">City</label>
                    	<input type = "text" name = "city" value = "${restaurant.city}" required>
                    </div>
                    <div class = "input-group">
                        <label for = "contactEmail">Email</label>
                        <input type = "email" name = "contactEmail" id = "email" value = "${restaurant.contactEmail}" required>
                    </div>
                    <div class = "input-group">	
  						<label for="image">Take a picture: </label>
  						<input type="file" id="image" name="image">
  						<a class = "delete-link" href = "<c:url value = 'DeleteRestaurantPictureServlet.do?idR=${restaurant.id}&idU=${currentUser.id}'/>">Delete Picture</a>
                    </div>
                    
                    <div class = "input-group">
                    	<h2 class = "login-title" id = "bike-parking-question">What kind of food do you serve?</h2>
                    	<c:forEach var = "category" items = "${categoryList}">
                    		<div class = "category-container">
                    			<label for = "${category.name}" class = "features-text">${category.name}</label>
                    			<input type = "checkbox" value = "${category.name}" name = "selectedCategory" ${requestScope[category.name]}>	
                    		</div>
                    	</c:forEach>
                    </div>
                    <div class = "input-group">
                    	<h2 class = "login-title" id = "bike-parking-question">Do you have a bike parking lot?</h2>
                    	<div class = "bike-friendly-radio">
                        	<label for = "bike-friendly" class = "features-text">Yes</label>
                        	<input type = "radio" value = "yes-bike-friendly-value" name = "bike-friendly" ${requestScope.yesbikefriendly}>
                    	</div>
                    	<div class = "bike-friendly-radio">
                    		<label for = "bike-friendly" class = "features-text">No</label>
	    	                <input type = "radio" value = "no-bike-friendly-value" name = "bike-friendly" ${requestScope.nobikefriendly}>
                    	</div>
                    </div>
                    <div class = "input-group">
                    	<h2 class = "login-title">Can you take orders?</h2>
                    	<div class = "available-radio">
                    		<label for = "available" class = "features-text">Yes</label>
                    		<input type = "radio" value = "yes-available" name = "available" ${yesavailable}>
                    	</div>
                    	<div class = "bike-friendly-radio">
                    		<label for = "available" class = "features-text">No</label>
                    		<input type = "radio" value = "no-available" name = "available" ${noavailable}>
                    	</div>
                    </div>
                    <a id = "manage-dishes-link" href = "<c:url value = 'UpdateDishesServlet.do?idR=${restaurant.id}&idU=${currentUser.id}'/>">Manage dishes</a>
                    <button type = "submit" class = "login-button">Update restaurant</button>
                    <a class = "delete-link" href = "<c:url value = 'DeleteRestaurantServlet.do?idR=${restaurant.id}&idU=${currentUser.id}'/>">Delete restaurant</a>
                    <a class = "delete-link" href = "<c:url value = "RestaurantDetailsServlet.do?id=${restaurant.id}"/>">Go back to restaurant page</a>
                </form>
                <p class = error>${requestScope.error}</p>
            </div>
        </div>
    </body>
</html>