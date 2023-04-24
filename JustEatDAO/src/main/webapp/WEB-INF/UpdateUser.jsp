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
                <form class = "form" action = "UpdateUserServlet.do" method = "post">
                    <div class = "input-group">
                        <label for = "name">Name</label>
                        <input type = "text" name = "name" value = "${user.name}" required>
                    </div>
                    <div class = "input-group">
                        <label for = "surname">Surname</label>
                        <input type = "text" name = "surname" value = "${user.surname}" required>
                    </div>
                    <div class = "input-group">
                        <label for = "email">Email</label>
                        <input type = "email" name = "email" id = "email" value = "${user.email}" required>
                    </div>
                    <div class = "input-group">
                        <label for = "password">Password</label>
                        <input type = "password" name = "password" id = "password" value = "${user.password}" required>
                    </div>
                    <button type = "submit" class = "login-button">Update account</button>
                    <a class = "delete-link" href = "<c:url value = 'LogOutServlet.do'/>">Log Out</a>
                    <a class = "delete-link" href = "<c:url value = 'DeleteUserServlet.do'/>">Delete account</a>
                    <a class = "delete-link" href = "<c:url value = "SearchServlet.do"/>">Go back to main page</a>
                </form>
                <p class = error>${requestScope.error}</p>
            </div>
        </div>
    </body>
</html>