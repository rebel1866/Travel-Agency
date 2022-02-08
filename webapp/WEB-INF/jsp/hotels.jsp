
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Отели</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <jsp:include page="search-window-hotels.jsp"/>

        <div id="sorting" style="margin-left: 580px;margin-top: 50px">
            <c:out value="Сортировать по"/>
            <c:out value="рейтингу:"/>&nbsp;
            <c:set var="queryString" value="${pageContext.request.getQueryString()}"/>

            <a class="link-a" href="${pageContext.request.contextPath}/executor?<m:cutsort queryStr="${queryString}"/>&sorting=rating&sortingOrder=asc">
                <c:out value="asc"/>
            </a>&nbsp;

            <a class="link-a" href="${pageContext.request.contextPath}/executor?<m:cutsort queryStr="${queryString}"/>&sorting=rating&sortingOrder=desc">
                <c:out value="desc"/>
            </a>
        </div>

        <%@ include file="hotels-list.jsp" %>

        <c:if test="${sessionScope.authorization.button_access!=null}">
            <a id="add-tour" href="${pageContext.request.contextPath}/executor?command=add_hotel&subcommand=3">Добавить
                отель</a>
        </c:if>

        <c:if test="${fn:length(hotels)!=0}"><%@ include file="pagination.jsp" %></c:if>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
