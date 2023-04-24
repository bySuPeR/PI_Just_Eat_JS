<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang = "es">
    <head>
        <meta charset = "UTF-8">
        <title>Just Eat</title>
        <link rel = "stylesheet" href = "css/general.css">
        <link rel = "stylesheet" href = "css/res_details.css">
        <link rel = "stylesheet" href = "css/load_review.css">
    </head>
    <body>
        <div class = "full-screen-container">
            <div class = "login-container">
                <h1 class = "login-title">Welcome</h1>
                <c:forEach var = "singleReview" items = "${reviewList}">
                	<div class = "single-comment">
                		<h1 class = "restaurant-text">${singleReview.first.name}
                			<span class = "number-value">${singleReview.second.grade}</span>
                		</h1>
                		<h2>${singleReview.second.review}</h2>
                		<c:if test = "${currentUser.id eq singleReview.second.idu}">
                			<a class = "delete-link" id = "update-review" href = "<c:url value = 'UpdateReviewServlet.do?idR=${singleReview.third.id}&idU=${currentUser.id}'/>">Update review</a>
                		</c:if>
                	</div>
                </c:forEach>
                <c:if test = "${(currentUser.id ne restaurant.idu) and (addreview eq true)}">
                	<form class = "form" action = "LoadReviewsServlet.do?idR=${restaurant.id}" method = "post">
                		<div class = "input-group">
                			<label for = "review">Review</label>
                        	<input type = "text" name = "review" required>
                		</div>
                		<div class = "input-group">
                			<label for = "grade">Grade</label>
                        	<input type = "range" name = "grade" min = "1.0" max = "5.0" step = "0.1"  value = "5.0">
                		</div>
                		<input type = "submit" class = "login-button" value = "Add review">
                	</form>
                </c:if>
                <a class = "delete-link" href = "<c:url value = 'RestaurantDetailsServlet.do?id=${restaurant.id}'/>">Go back</a>
            </div>
        </div>
    </body>
</html>