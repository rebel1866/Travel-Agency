<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>BEST-TOURS.BY</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <jsp:include page="search-window-tour.jsp"/>

        <h2 class="home-headers">Горящие туры: </h2>
        <%@ include file="tours-list.jsp" %>

        <a class="inf-all" href="${pageContext.request.contextPath}/executor?command=tours&page=1">Просмотреть все
            туры</a>

        <c:if test="${fn:length(rec_tours)>0}">
            <h2 class="home-headers">Вас могут заинтересовать следующие туры: </h2>
            <%@ include file="rec-tours-list.jsp" %>
        </c:if>

        <c:if test="${sessionScope.authorization.button_access!=null}">
            <a id="add-tour" href="${pageContext.request.contextPath}/executor?command=add_tour&subcommand=3">Добавить
                тур</a>
        </c:if>

        <h2 class="home-headers">Отели: </h2>
        <%@ include file="hotels-list.jsp" %>

        <a class="inf-all" href="${pageContext.request.contextPath}/executor?command=hotels&page=1">Просмотреть все
            отели</a>

        <c:if test="${sessionScope.authorization.button_access!=null}">
            <a id="add-tour" href="${pageContext.request.contextPath}/executor?command=add_hotel&subcommand=3">Добавить
                отель</a>
        </c:if>

        <h2 class="home-headers">Отзывы: </h2>
        <%@ include file="feedbacks-list.jsp" %>

        <a class="inf-all" href="${pageContext.request.contextPath}/executor?command=feedbacks&page=1">
            Просмотреть все отзывы</a>
        <br><br>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
