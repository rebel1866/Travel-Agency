<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Отель ${hotel.hotelName}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/rating.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/full-hotel-info.css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">

        <c:set var="isAdmin"
               value="${sessionScope.authorization.button_access!=null}"/>

        <div id="content-full-hotel">
            <h1><c:out value="${hotel.hotelName}"/>
                <div class="rating">
                    <c:forEach var="i" begin="1" end="${hotel.rating}">
                        <span class="active"></span>
                    </c:forEach>
                </div>
            </h1>

            <h2><a id="resort-name-hotel" href="dsf"><c:out value="${hotel.resort.resortName}"/></a></h2>

            <c:out value="Расположение: ${hotel.typeLocation}"/><br>
            <c:out value="Общее число номеров: ${hotel.amountRooms}"/><br>
            <c:out value="Описание:"/><br>

            <c:out value="${hotel.hotelDescription}"/><br>
            <h3><c:out value="Инфраструктура:"/></h3>
            <c:set var="infraStr" value="${hotel.infrastructure}"/>
            <c:set var="infraArray" value="${infraStr.split('; ')}"/>

            <ul>
                <c:forEach items="${infraArray}" var="element">
                    <li>
                        <c:out value="${element}"/>
                    </li>
                </c:forEach>
            </ul>



                <h3><c:out value="Фотографии:"/></h3>
                <c:forEach items="${hotel.hotelImages}" var="image">
                <img class="image-hotel" src="${pageContext.request.contextPath}/img/${image.imagePath}">
            </c:forEach>

            <c:if test="${fn:length(tours)!=0}">
                <h2 id="hotel-tourss"><c:out value="Туры в отель ${hotel.hotelName}:"/></h2>
                <%@ include file="tours-list.jsp" %>
            </c:if>

            <c:if test="${isAdmin}">
                <div id="nav-hotel">
                    <a id="remove-hotel"
                       href="${pageContext.request.contextPath}/executor?command=remove_hotel&hotel_id=${hotel.hotelId}&hotel_name=${hotel.hotelName}&subcommand=4">
                        <c:out value="Удалить отель"/>
                    </a>

                    <a id="change-hotel"
                       href="${pageContext.request.contextPath}/executor?command=change_hotel&hotel_id=${hotel.hotelId}&subcommand=3">
                        <c:out value="Изменить отель"/>
                    </a>
                </div>
            </c:if>
        </div>


    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
