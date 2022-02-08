<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/orders.css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div style=" padding: 30px">
            <h1>Личный кабинет</h1>
            <h3>Информация пользователя:</h3>
            <p><c:out value="Логин: ${user.login}"/></p>
            <p><c:out value="Имя: ${user.firstName}"/></p>
            <p><c:out value="Фамилия: ${user.lastName}"/></p>
            <p><c:out value="Дата рождения: ${user.birthDate}"/></p>
            <p><c:out value="Электронная почта: ${user.email}"/></p>
            <p><c:out value="Пол: ${user.gender}"/></p>
            <p><c:out value="Номер телефона: ${user.telephone}"/></p>
            <p>
                <a href="${pageContext.request.contextPath}/executor?command=change_personal_data&subcommand=3">
                    <c:out value="Редактировать личные данные"/> </a>
            </p>
            <c:if test="${sessionScope.authorization.button_access!=null}">
                <p>
                    <a href="${pageContext.request.contextPath}/executor?command=add_tour&subcommand=3">
                        <c:out value="Добавить тур"/> </a>
                </p>

                <p>
                    <a href="${pageContext.request.contextPath}/executor?command=add_hotel&subcommand=3">
                        <c:out value="Добавить отель"/> </a>
                </p>

                <p>
                    <a href="${pageContext.request.contextPath}/executor?command=add_user&subcommand=3">
                        <c:out value="Добавить пользователя"/> </a>
                </p>

                <p>
                    <a href="${pageContext.request.contextPath}/executor?command=add_new_role&subcommand=3">
                        <c:out value="Добавить новую роль пользователя"/> </a>
                </p>

            </c:if>
            <h3>Мои регистрации:</h3></div>
        <div style="padding-bottom: 30px">
            <table>
                <tr class="color-blue">
                    <th><c:out value="ID заявки"/></th>
                    <th><c:out value="Название тура"/></th>
                    <th><c:out value="Конечная стоимость"/></th>
                    <th><c:out value="Статус заявки"/></th>
                </tr>
                <c:forEach var="order" items="${orders}" varStatus="i">
                    <tr <c:if test="${i.count%2==0}">class="color-blue" </c:if>>
                        <td><c:out value="${order.orderId}"/></td>
                        <td><c:out value="${order.tour.tourName}"/></td>
                        <td><c:out value="${order.finalPrice}"/></td>
                        <td>
                    <span
                            <c:if test="${order.orderStatus.equals('Active')}">class="color-green" </c:if>
                            <c:if test="${order.orderStatus.equals('Non-active')}">class="color-red" </c:if>
                            <c:if test="${order.orderStatus.equals('Awaiting')}">class="color-yellow" </c:if>>
                        <c:out value="${order.orderStatus}"/>
                      </span></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
