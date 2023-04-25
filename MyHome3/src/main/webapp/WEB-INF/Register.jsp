<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang = "en">
<head>
    <meta charset = "utf-8">
    <title>MyHome</title>
    <link rel = "stylesheet" href = "${pageContext.request.contextPath}/css/Register.css">
    <link rel = "icon" href = "images/house.PNG">
</head>
<body>
    <div class = "square">
        <div class = "text">My<span>Home</span></div>
        <div class = "login">
            <form action = "RegisterServlet.do" method = "post">
            	<input type = "text" placeholder = "Name" required name = "name"><br>
            	<input type = "email" placeholder = "name@something.com" required name = "email"><br>
            	<input type = "password" placeholder = "Introduce password" required name = "password"><br>
                <input type = "submit" value = "Register">
                <p class = error>${requestScope.error}</p>
            </form>
        </div>
    </div>
</body>
</html>