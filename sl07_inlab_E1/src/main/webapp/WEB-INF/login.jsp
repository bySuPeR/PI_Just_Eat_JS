<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"  href="${pageContext.request.contextPath}/css/stylesheet.css"   />
<title>Pizzeria Login</title>
</head>
<body>
<p>${messages}</p>
<h1>Pizzeria Login</h1>
<form method="POST" action="LoginServlet">
  <div id="user">
<label>User:<input type="text" name="username"></label> 
</div>
<div id="password">
<label>Password:<input type="password" name="password"></label>
</div>
  <input type="submit" value="Enter"> 
</form>
</body>
</html>