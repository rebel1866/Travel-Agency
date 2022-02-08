
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hotel-list.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tables.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/rating.css">
</head>
<body>
<table>
    <tr>
        <c:forEach var="hotel" items="${hotels}" varStatus="i">
        <c:if test="${i.index != 0 && i.index % 3 == 0}">
    </tr>
    <tr>
        </c:if>
        <td>
            <p class="hotel-name"><c:out value="${hotel.hotelName}"/>

            <div class="rating">
                <c:forEach var="i" begin="1" end="${hotel.rating}">
                    <span class="active"></span>
                </c:forEach>
            </div>

            <p class="resort-name"><c:out value="${hotel.resort.resortName}"/></p>
            <c:set var="description" value="${hotel.hotelDescription}"/>
            <c:set var="desSubstring" value="${fn:substring(description, 0, 130)}"/>

            <p class="hotel-des"><c:out value="${desSubstring}..."/> </p>
            <a id="inf-hotel" href="${pageContext.request.contextPath}/executor?command=full_hotel_info&hotel_id=${hotel.hotelId}">
                Подробнее об отеле</a>
        </td>
        </c:forEach>
            <c:if test="${fn:length(hotels)==0}">
                <br><br><br>
                <h2 id="nothing-found"><c:out value="По вашему запросу ничего не найдено"/></h2>
            </c:if>
    </tr>
</table>
</body>
</html>
