<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang = es>
    <head>
        <meta charset = "UTF-8">
        <title>Just Eat</title>
        <link rel = "stylesheet" href = "general.css">
        <link rel = "stylesheet" href = "edit_plate.css">
    </head>
    <body>
        <div class = "full-screen-container">
            <div class = "main-page-container">
                <img src = "canteria.jpg" class = "restaurant-photo">
                <form class = "form-edit-plate">
                    <div class = "input-group">
                        <label for = "name">Nombre</label><br>
                        <input type = "text" name = "name" class = "text-input" required>
                    </div>
                    <div class = "input-group">
                        <label for = "description">DescripciÃ³n</label><br>
                        <input type = "textarea" name = "description" class = "text-input" required>
                    </div>
                    <div class = "input-group">
                        <label for = "price">Precio</label><br>
                        <input type = "number" name = "price" required>
                    </div>
                </form>
                <button type = "submit" id = "confirm" onclick = "window.location.href='edit_restaurant.html';">Aceptar</button>
            </div>
        </div>
    </body>
</html>