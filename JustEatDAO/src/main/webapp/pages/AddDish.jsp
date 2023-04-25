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
                <h1 class = "login-title">Create your dish</h1>
                <form class = "form" action = "AddDishServlet.do" method = "post">
                	<div class = "invidivual-dish-input">
                		<div class = "input-group">
                			<label for = "name">Name</label>
                			<input type = "text" name = "name" required>
                		</div>
                		<div class = "input-group">
                			<label for = "price">Price</label>
                			<input type = "number" name = "price" required>
                		</div>
                		<div class = "input-group">
                			<label for = "description">Description</label>
                			<textarea name = "description" required></textarea>
                		</div>
                		<div class = "input-group">	
  							<label for="image">Take a picture: </label>
  							<input type="file" id="image" name="image">
                    	</div>
                	</div>
                	<button type = "submit" class = "login-button">Add dish</button>
                </form>
                <p class = error>${requestScope.error}</p>
            </div>
        </div>
    </body>
</html>