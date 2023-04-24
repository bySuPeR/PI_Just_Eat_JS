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
                <h1 class = "login-title">Welcome</h1>
                <form class = "form" action = "LoginServlet.do" method = "post">
                    <div class = "input-group">
                        <label for = "email">Email</label>
                        <input type = "email" name = "email" id = "email" required>
                    </div>
                    <div class = "input-group">
                        <label for = "password">Password</label>
                        <input type = "password" name = "password" id = "password" required>
                    </div>
                    <input type = "submit" class = "login-button" value = "Log in">
                    <span class = "question-text">New here?<a class = "question-link" href = "<c:url value = 'RegisterServlet.do'/>">Register now!</a></span>
                </form>
                <p class = error>${requestScope.error}</p>
            </div>
        </div>
    </body>
</html>