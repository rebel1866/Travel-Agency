<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Новая заявка на тур</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-order.css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="content-addorder">
            <h1><c:out value="Проверьте информацию вашего заказа:"/></h1>
            <h2><c:out value="Данные тура:"/></h2>
            <p><c:out value="${order.tour.tourName}"/></p>
            <p><c:out value="Отель ${order.tour.hotel.hotelName} ${order.tour.hotel.rating} звезд"/></p>
            <p><c:out value="Отправление: ${order.tour.departure}"/></p>
            <p><c:out value="Возвращение: ${order.tour.comeback}"/></p>
           <h5><p><c:out value="Стоимость тура: ${order.tour.price}$"/></p></h5>
           <h4> <p><c:out value="Скидка: ${order.discount.discountPercents}%"/></p></h4>
            <h3><p id="full-price"><c:out value="Полная стоимость с учетом скидки: ${order.finalPrice}$"/></p></h3>
            <h2><c:out value="Проверьте вашу личную информацию:"/></h2>
            <p><c:out value="Имя и фамилия: ${order.user.firstName} ${order.user.lastName}"/></p>
            <p><c:out value="Контактный телефон: ${order.user.telephone}"/></p>
            <p><c:out value="Электронная почта: ${order.user.email}"/></p>
            <h2><c:out value="Оставьте комментарий к заявке:"/></h2>
            <form action="${pageContext.request.contextPath}/executor" method="post">
                <input type="hidden" name="command" value="add_order">
                <input type="hidden" name="subcommand" value="main">
                <input type="hidden" name="tour_id" value="${order.tour.tourId}">
                <textarea name="comment" rows="10" cols="50"></textarea><br><br>
                <input type="submit" name="send" value="Подтвердить">
            </form>
        </div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
