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
                <input type = "hidden" name = "id" value = "${restaurant.id}">
                <form class = "form" action = "UpdateDishesServlet.do" method = "post">
                	<c:forEach var = "dish" items = "${dishList}">
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
                			<a class = "delete-link" href = "<c:url value = 'DeleteDishServlet.do?id=${dish.id}'/>">Delete dish</a>
                		</div>
                	</c:forEach>
                    <a id = "manage-dishes-link" href = "<c:url value = 'AddDishServlet.do?id=${restaurant.id}'/>">Add new dish</a>
                    <button type = "submit" class = "login-button">Update dishes</button>
                </form>
                <p class = error>${requestScope.error}</p>
            </div>
        </div>
    </body>
</html>