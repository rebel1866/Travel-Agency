<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Удаление отеля</title>
    <style>
        #hotel-remove{
            margin-left: 30px;
            padding-bottom: 250px;
        }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="hotel-remove">
            <h1>Внимание!</h1>
            <h3>Вся информация связанная с данным отелем будет удалена (в том числе все изображения).</h3>
            <h4>Удалить <c:out value="${hotel_name}"/> ?</h4>
            <a href="${pageContext.request.contextPath}/executor?command=remove_hotel&action=accept&subcommand=1&hotel_id=${hotel_id}">
                <c:out value="Подтвердить"/></a>
            <a href="${pageContext.request.contextPath}/executor?command=remove_hotel&action=deny&subcommand=1&hotel_id=${hotel_id}">
                <c:out value="Отменить"/></a>
        </div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
