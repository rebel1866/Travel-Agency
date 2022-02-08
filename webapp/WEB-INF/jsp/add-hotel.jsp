<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить тур</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-hotel.css">
</head>
<body>
<div id="container">
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <div id="content">
        <div id="add-hotel-form">
            <h1><c:out value="Добавление нового отеля:"/></h1>
            <form action="${pageContext.request.contextPath}/executor" method="post" accept-charset="UTF-8"
                  enctype="multipart/form-data">
                <input type="hidden" name="command" value="add_hotel"/>
                <input type="hidden" name="hotel_status" value="EXIST"/>
                <input required class="forms-add-hotel" type="text" name="hotel_name" placeholder="Название отеля"><br>
                <c:out value="Курорт:"/>
                <select name="resort_id">
                    <c:forEach var="resort" items="${resorts}">
                        <option value="${resort.resortId}">${resort.resortName}</option>
                    </c:forEach>
                </select><br>
                <c:out value="Рейтинг отеля:"/>
                <input required type="number" min="1" max="5" value="4" name="rating"><br>
                <textarea required name="hotel_description" cols="45" rows="10" placeholder="Описание отеля"></textarea><br>
                <c:out value="Общее число номеров:"/>
                <input required id="amount-rooms" type="number" name="amount_rooms"><br>
                <c:out value="Расположение отеля:"/>
                <select name="type_location">
                    <option value="Пляжный, 1-я линия от моря (200 метров)">
                        <c:out value="Пляжный, 1-я линия от моря"/></option>
                    <option value="Пляжный - 2-я линия от моря (500 метров)">
                        <c:out value="Пляжный - 2-я линия от моря"/></option>
                    <option value="Пляжный - 3-я линия от моря (700 метров)">
                        <c:out value="Пляжный - 3-я линия от моря"/></option>
                    <option value="Городской - 2 км от моря">
                        <c:out value="Городской"/></option>
                </select><br>
                <textarea required name="infrastructure" cols="60" rows="10" placeholder="Инфраструктура"></textarea><br>
                <p><c:out value="Загрузите фотографии отеля:"/></p>
                <input type="file" name="image1"/> <br/>
                <input type="file" name="image2"/> <br/>
                <input type="file" name="image3"/> <br/>
                <c:out value="${requestScope.e_message}"/><br><br>
                <input id="submit-add-hotel" type="submit" name="submit" value="Подтвердить"/><br>
            </form>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
