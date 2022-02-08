<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>BEST-TOURS.BY</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tour-list.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tables.css">
</head>
<body>
<table>
    <tr>
        <c:forEach var="tour" items="${rec_tours}" varStatus="i">
        <c:if test="${i.index != 0 && i.index % 3 == 0}">
    </tr>
    <tr>
        </c:if>
        <td>
            <p class="tour-name">${tour.tourName}</p>
            <p id="tour-hotel">Отель ${tour.hotel.hotelName}
            <div class="rating">
                <c:forEach var="i" begin="1" end="${tour.hotel.rating}">
                    <span class="active"></span>
                </c:forEach>
            </div>
            </p>
            <fmt:parseDate value="${tour.departure}" pattern="yyyy-MM-dd" var="parsedDep" type="date" />
            <fmt:formatDate value="${parsedDep}" var="newTourDeparture" type="date" pattern="dd MMMM" />
            <fmt:parseDate value="${tour.comeback}" pattern="yyyy-MM-dd" var="parsedBack" type="date" />
            <fmt:formatDate value="${parsedBack}" var="newTourComeback" type="date" pattern="dd MMMM" />
            <p style="margin-top: -10px">    <c:out value="${tour.amountAdults}"/> <c:if test="${tour.amountAdults>1}">
                <c:out value="взрослых"/></c:if>
                <c:if test="${tour.amountAdults==1}"><c:out value="взрослый"/></c:if>
                <c:if test="${tour.amountChildren>0}"> и <c:out value="${tour.amountChildren}"/>
                    <c:if test="${tour.amountChildren>1}"><c:out value="детей"/></c:if>
                    <c:if test="${tour.amountChildren==1}"><c:out value="ребенок"/></c:if>
                </c:if>
            </p>
            <p class="date"> <c:out value="c"/> ${newTourDeparture} <c:out value="по"/> ${newTourComeback} </p>
            <span class="price"><m:printPrice priceUSD="${tour.price}"/></span>
            <a class="info-tour" href="${pageContext.request.contextPath}/executor?command=full_tour_info&id=${tour.tourId}">
                <c:out value="Подробнее о туре"/></a>
        </td>
        </c:forEach>
        <c:if test="${fn:length(tours)==0}">
            <h2 id="nothing-found"><c:out value="По вашему запросу ничего не найдено"/></h2>
        </c:if>
    </tr>
</table>
</body>
</html>
