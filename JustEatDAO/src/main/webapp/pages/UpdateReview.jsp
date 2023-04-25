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
                <h1 class = "login-title">Update your review</h1>
                <form class = "form" action = "UpdateReviewServlet.do?idR=${comment.idr}&idU=${user.id}" method = "post">
                	<div class = "input-group">
                		<label for = "review">Review</label>
                        <input type = "text" name = "review" value = "${comment.review}" required>
                	</div>
                	<div class = "input-group">
                		<label for = "grade">Grade</label>
                       <input type = "range" name = "grade" min = "1.0" max = "5.0" step = "0.1"  value = "${comment.grade}">
                	</div>
                	<input type = "submit" class = "login-button" value = "Update review">
                </form>
                <a class = "delete-link" href = "<c:url value = 'DeleteReviewServlet.do?idR=${restaurant.id}&idU=${user.id}'/>">Delete review</a>
                <a class = "delete-link" href = "<c:url value = 'LoadReviewsServlet.do?id=${restaurant.id}'/>">Go back</a>
            </div>
        </div>
    </body>
</html>