<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Добавление тура</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-tour.css">
</head>
<body>
<div id="container">
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <div id="content">
        <div id="add-tour">
            <h1><c:out value="Добавление нового тура: "/></h1>
            <form action="${pageContext.request.contextPath}/executor" method="post" accept-charset="UTF-8">
                <table>
                    <tr>
                        <td>
                            <input id="tour-name" required type="text" name="tour_name" placeholder="Название тура">
                            <input type="hidden" name="command" value="add_tour"/><br>
                            <label><c:out value="Транспорт: "/>
                                <input required type="text" name="transport">
                            </label><br>
                            <label><c:out value="Возвращение:"/>
                                <input required type="date" name="comeback">
                            </label><br>
                            <label><c:out value="Отправление: "/>
                                <input required type="date" name="departure">
                            </label><br>
                            <c:out value="Горящий статус:"/>
                            <input type="radio" required name="burning_status" value="true"> <c:out value="Да"/>
                            <input type="radio" required name="burning_status" value="false"> <c:out value="Нет"/>
                            <br>
                            <c:out value="Рейтинг горящего тура:"/>
                            <input type="number" required min="0" max="5" value="0" name="relevance"><br>
                            <label for="seats"><c:out value="Количество мест: "/></label>
                            <input required id="seats" type="number" name="amount_seats"><br>
                        </td>
                        <td>
                            <c:out value="Отель:"/>
                            <select name="hotel_id">
                                <c:forEach var="hotel" items="${hotels}">
                                    <option value="${hotel.hotelId}">${hotel.hotelName}</option>
                                </c:forEach>
                            </select><br>
                            <label><c:out value="Тип номера: "/>
                                <input required type="text" name="hotel_room_type">
                            </label><br>
                            <label><c:out value="Стоимость:"/>
                                <input required id="price-tour" type="number" min="0" name="price">
                            </label><br>
                            <label><c:out value="Тип питания: "/>
                                <input required type="text" name="feeding">
                            </label><br>
                            <label for="adults"><c:out value="Количество взрослых: "/></label>
                            <input required id="adults" type="number" name="amount_adults"><br>
                            <label for="children"><c:out value="Количество детей: "/></label>
                            <input required id="children" type="number" name="amount_children"><br>
                        </td>
                    </tr>
                </table>
               <p id="e-message-tour"> <c:out value="${requestScope.e_message}"/></p>
                <input type="hidden" name="tour_status" value="EXIST"/>
                <input type="hidden" name="subcommand" value="main">
                <input id="submit-tour" type="submit" name="submit" value="Подтвердить"/><br>
            </form>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
