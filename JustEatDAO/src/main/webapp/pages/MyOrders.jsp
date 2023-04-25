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
                <h1 class = "login-title">Your orders</h1>
                <c:forEach var = "order" items = "${orderList}" varStatus="status">
   					<c:set var="nombreIndex" value="${status.index + 1}" />
   					<c:set var="nombreConcatenado" value="Pedido ${nombreIndex}" />
                	<form action = "MyOrdersServlet.do?Or=${order.id}" method = "post"
                			class = "invidivual-dish-input" id = "order-form">
                		<h1>${nombreConcatenado}</h1>
                		<h2>${order.totalPrice}€</h2>
                		<input type = "submit" class = "login-button" value = "Check it out">
                	</form>
                </c:forEach>
                <a class = "delete-link" href = "<c:url value = "SearchServlet.do"/>">Go back to main page</a>
            </div>
        </div>
    </body>
</html>