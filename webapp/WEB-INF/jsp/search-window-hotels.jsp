<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search-windows.css" type="text/css"/>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/sunny/jquery-ui.css">
    <script src="${pageContext.request.contextPath}/js/ajax.js"></script>
</head>
<body>
<form id="hotel-form" action="${pageContext.request.contextPath}/executor" method="get">
    <input type="hidden" name="command" value="hotels"/>
    <input id="acInput" type="text" name="hotel_name" placeholder="Отель"/>
    <input id="location-input" type="text" name="location" placeholder="Страна"/>
    <input id="resort-input" type="text" name="resort_name" placeholder="Курорт"/><br>
    <c:out value="Количество звезд:"/>
    <input type="number" max="5" min="0" name="rating"/>
    <c:out value="Расположение:"/>
    <select name="type_location">
        <option selected value="">Не указано</option>
        <option value="Пляжный, 1-я линия от моря (200 метров)"><c:out value="Пляжный, 1-я линия от моря"/></option>
        <option value="Пляжный - 2-я линия от моря (500 метров)"><c:out value="Пляжный - 2-я линия от моря"/></option>
        <option value="Пляжный - 3-я линия от моря (700 метров)"><c:out value="Пляжный - 3-я линия от моря"/></option>
        <option value="Городской - 2 км от моря"><c:out value="Городской"/></option>
    </select><br>
    <input id="search-hotel-btn" type="submit" name="submit" value="Найти отель"/>
    <input type="hidden" name="page" value="1">
</form>
</body>
</html>
