<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/search-windows.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/themes/sunny/jquery-ui.css">
   <script src="${pageContext.request.contextPath}/js/ajax.js"></script>
</head>
<body>
<div id="search-window">


    <form action="${pageContext.request.contextPath}/executor" method="get" accept-charset="UTF-8">
        <input type="hidden" name="command" value="tours"/>


        <input id="acInput" autocomplete="off" type="text" name="hotel_name" placeholder="Отель"/>


        <input id="location-input" autocomplete="off" type="text" name="location" placeholder="Страна"/>
        <c:out value="Питание: "/>
        <select name="feeding">
            <option selected value="">Не указано</option>
            <option value="Всё включено">Всё включено</option>
            <option value="Завтрак и ужин">Завтрак и ужин</option>
            <option value="Завтрак и обед">Завтрак и обед</option>
        </select><br>
        <input class="form" type="number" min="0" name="price_from" placeholder="Цена от"/>
        <input id="resort-input" autocomplete="off" type="text" name="resort_name" placeholder="Курорт"/>
        <c:out value="Количество звезд отеля:"/>
        <input type="number" name="rating" value="not selected" min="1" max="5" step="1"><br>
        <input class="form" type="number" min="0" name="price_to" placeholder="Цена до"/>
        <c:out value="Ночей от:"/>
        <input type="number" style="width: 50px" name="amount_nights_from" value="6">
        <c:out value="До:"/>
        <input type="number" style="width: 50px" name="amount_nights_to" value="25">
        <c:out value="Количество взрослых:"/>
        <input type="number" name="amount_adults" value="not selected" min="1" max="7" step="1"> <br>
        <input type="date" class="form" name="departure"/>
        <input type="date" class="form" name="comeback"/>
        <c:out value="Количество детей:"/>
        <input type="number" name="amount_children" value="not selected" min="0" max="5" step="1">
        &nbsp;&nbsp;
        <label for="burning">Горящий:</label>
        <input style="vertical-align: middle" id="burning" type="checkbox" name="burning_status"
               <c:if test="${param.burning_status.equals('1')}">checked</c:if> value="1">
        <input style="margin-left: 240px;margin-bottom: -20px" type="submit" name="submit" value="Подобрать тур"/>
        <input type="hidden" name="page" value="1">
    </form>
</div>
</body>
</html>
