<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Заявки</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/orders.css">
</head>
<body>

<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="order-content">
            <form action="${pageContext.request.contextPath}/executor" method="get">
                <input type="hidden" name="command" value="orders">
                <input type="text" name="order_id" placeholder="ID заявки">
                <input type="text" name="user_id" placeholder="ID пользователя">
                <input type="text" name="tour_id" placeholder="ID тура"><br>
                <input type="text" name="first_name" placeholder="Имя пользователя">
                <input type="text" name="last_name" placeholder="Фамилия пользователя">
                <label><c:out value="Статус:"/>
                    <select name="order_status">
                        <option value="">Не указано</option>
                        <option value="Active">Active</option>
                        <option value="Awaiting">Awaiting</option>
                        <option value="Non-Active">Non-Active</option>
                        <option value="Non-Active">Denied</option>
                    </select>
                </label><br>
                <input id="search" type="submit" name="submit" value="Поиск">
                <input type="hidden" name="page" value="1">
            </form>
            <table>
                <tr class="color-blue">
                    <th><c:out value="ID заявки"/></th>
                    <th><c:out value="ID тура"/></th>
                    <th><c:out value="Название тура"/></th>
                    <th><c:out value="Конечная стоимость"/></th>
                    <th id="user-id"><c:out value="ID пользователя"/></th>
                    <th><c:out value="Имя и фамилия"/></th>
                    <th><c:out value="Телефон"/></th>
                    <th><c:out value="Статус заявки"/></th>
                    <th><c:out value="Полная информация"/></th>
                </tr>
                <c:forEach var="order" items="${orders}" varStatus="i">
                    <tr <c:if test="${i.count%2==0}">class="color-blue" </c:if>>
                        <td><c:out value="${order.orderId}"/></td>
                        <td><c:out value="${order.tour.tourId}"/></td>
                        <td><c:out value="${order.tour.tourName}"/></td>
                        <td><c:out value="${order.finalPrice}"/></td>
                        <td><c:out value="${order.user.userID}"/></td>
                        <td><c:out value="${order.user.firstName} ${order.user.lastName}"/></td>
                        <td><c:out value="${order.user.telephone}"/></td>
                        <td>
                    <span
                            <c:if test="${order.orderStatus.equals('Active')}">class="color-green" </c:if>
                            <c:if test="${order.orderStatus.equals('Non-active')}">class="color-red" </c:if>
                            <c:if test="${order.orderStatus.equals('Awaiting')}">class="color-yellow" </c:if>>
                        <c:out value="${order.orderStatus}"/>
                      </span></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/executor?command=full_order_info&order_id=${order.orderId}&subcommand=main">
                                <c:out value="Подробнее"/></a></td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${fn:length(feedbacks)!=0}">
                <%@ include file="pagination.jsp" %>
            </c:if>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
