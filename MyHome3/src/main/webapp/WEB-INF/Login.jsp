<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang = "en">
<head>
    <meta charset = "utf-8">
    <title>MyHome</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login.css">
    <link rel = "icon" href = "images/house.PNG">
</head>
<body>
    <div class = "form">
        <div class = "header">
            <div>My<span>Home</span></div>
        </div>
        <br>
        <div class = "login">
            <form action = "LoginServlet.do" method = "post">
            	<input type = "text" placeholder = "username" required name = "username"><br>
            	<input type = "password" placeholder = "password" required name = "password"><br>
            	<input id = "login" type = "submit" value = "Log In">
            </form>
            <form action = "RegisterServlet.do" method = "get">
            	<input type = "submit" value = "Register">
            </form>
        </div>
        <p class = error>${requestScope.error}</p>
    </div>
</body>
</html>