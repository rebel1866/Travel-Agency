<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Полная информация о заявке</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/full-order-info.css" type="text/css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="order-full-content">
            <h2>Общая информация: </h2>
            <p><c:out value="ID заявки: ${order.orderId}"/></p>
            <p><c:out value="Статус заявки: ${order.orderStatus}"/></p>
            <p><c:out value="Изначальная цена: ${order.tour.price}$"/></p>
            <p><c:out value="Скидка: ${order.discount.discountPercents}%"/></p>
            <p><c:out value="Цена со скидкой: ${order.finalPrice}$"/></p>
            <p><c:out value="Комментарий к заявке: ${order.comment}"/></p>
            <h2>Информация о туре:</h2>
            <p><c:out value="ID тура: ${order.tour.tourId}"/></p>
            <p><c:out value="Название тура: ${order.tour.tourName}"/></p>
            <p><c:out value="Отправление: ${order.tour.departure}"/></p>
            <p><c:out value="Возвращение: ${order.tour.comeback}"/></p>
            <h2>Информация пользователя:</h2>
            <p><c:out value="ID пользователя: ${order.user.userID}"/></p>
            <p><c:out value="Имя и фамилия: ${order.user.firstName} ${order.user.lastName}"/></p>
            <p><c:out value="Телефон: ${order.user.telephone}"/></p>
            <p><c:out value="Электронная почта: ${order.user.email}"/></p><br>
            <p>
                <c:if test="${!order.orderStatus.equals('Active')}">
                    <a class="links-orders"
                       href="${pageContext.request.contextPath}/executor?command=full_order_info&order_id=${order.orderId}&subcommand=1">
                        Одобрить</a>
                </c:if>
                <c:if test="${!order.orderStatus.equals('Denied')}">
                    <a class="links-orders"
                       href="${pageContext.request.contextPath}/executor?command=full_order_info&order_id=${order.orderId}&subcommand=2">
                        Отклонить</a>
                </c:if>
                <a class="links-orders"
                   href="${pageContext.request.contextPath}/executor?command=remove_order&order_id=${order.orderId}&subcommand=4">
                    Удалить заявку</a>
                <a class="links-orders" href="">Изменить заявку</a>
            </p>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
