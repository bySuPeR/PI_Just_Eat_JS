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
        <link rel = "stylesheet" href = "css/update-dishes.css">
    </head>
    <body>
        <div class = "full-screen-container">
            <div class = "login-container">
                <h1 class = "login-title">Manage your dishes</h1>
                <c:forEach var = "dish" items = "${dishList}">
                	<form class = "form" action = "UpdateDishesServlet.do?idR=${restaurant.id}&idU=${user.id}&P=${dish.id}" method = "post">
                		<div class = "invidivual-dish-input">
                			<div class = "input-group">
                				<label for = "name">Name</label>
                				<input type = "text" name = "name" value = "${dish.name}" required>
                			</div>
                			<div class = "input-group">
                				<label for = "price">Price</label>
                				<input type = "number" name = "price" value = "${dish.price}" required>
                			</div>
                			<div class = "input-group">
                				<label for = "description">Description</label>
                				<textarea name = "description" required>${dish.description}</textarea>
                			</div>
                			<div class = "input-group">	
  								<label for="image">Take a picture: </label>
  								<input type="file" id="image" name="image">
  								<a class = "delete-link" href = "<c:url value = 'DeleteDishPictureServlet.do?P=${dish.id}'/>">Delete Picture</a>
                    		</div>
                			<a class = "delete-link" href = "<c:url value = 'DeleteDishServlet.do?id=${dish.id}'/>">Delete dish</a>
                    		<input type = "submit" class = "login-button" value = "Update dish" id = "update-dish">
                		</div>
                	</form>
                </c:forEach>
                <a id = "manage-dishes-link" href = "<c:url value = 'AddDishServlet.do?idR=${restaurant.id}'/>">Add new dish</a>
                <a class = "delete-link" href = "<c:url value = "UpdateRestaurantServlet.do?idR=${restaurant.id}&idU=${user.id}"/>">Go back to update restaurante page</a>
                <p class = error>${requestScope.error}</p>
            </div>
        </div>
    </body>
</html>