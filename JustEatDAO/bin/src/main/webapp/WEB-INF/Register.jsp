<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang = "es">
    <head>
        <meta charset = "UTF-8">
        <title>Just Eat</title>
        <link rel = "stylesheet" href = "${pageContext.request.contextPath}/css/general.css">
        <link rel = "stylesheet" href = "${pageContext.request.contextPath}/css/login.css">
    </head>
    <body>
        <div class = "full-screen-container">
            <div class = "login-container">
                <h1 class = "login-title">Register</h1>
                <form class = "form" action = "RegisterServlet.do" method = "post">
                    <div class = "input-group">
                        <label for = "name">Name</label>
                        <input type = "text" name = "name" required>
                    </div>
                    <div class = "input-group">
                        <label for = "surname">Surname</label>
                        <input type = "text" name = "surname" required>
                    </div>
                    <div class = "input-group">
                        <label for = "email">Email</label>
                        <input type = "email" name = "email" id = "email" required>
                    </div>
                    <div class = "input-group">
                        <label for = "password">Password</label>
                        <input type = "password" name = "password" id = "password" required>
                    </div>
                    <button type = "submit" class = "login-button">Register now</button>
                     <h5 class="error">${requestScope.error}</h5>
                    <span class = "question-text">Already have an account?<a class = "question-link" href = "LoginServlet.do">Log in now!</a></span>
                </form>
            </div>
        </div>
    </body>
</html>