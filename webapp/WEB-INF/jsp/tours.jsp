<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>
<html>
<head>
    <title>Туры</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <jsp:include page="search-window-tour.jsp"/>

        <c:set var="queryString" value="${pageContext.request.getQueryString()}"/>
        <div id="sorting">
            <c:out value="Сортировать по"/>
            <c:out value="цене:"/>&nbsp;
            <a class="link-a"
               href="${pageContext.request.contextPath}/executor?<m:cutsort queryStr="${queryString}"/>&sorting=price&sortingOrder=asc">
                <c:out value="asc"/>
            </a>&nbsp;

            <a class="link-a"
               href="${pageContext.request.contextPath}/executor?<m:cutsort queryStr="${queryString}"/>&sorting=price&sortingOrder=desc">
                <c:out value="desc"/>
            </a>&nbsp;
            <c:out value="рейтингу:"/>&nbsp;

            <a class="link-a"
               href="${pageContext.request.contextPath}/executor?<m:cutsort queryStr="${queryString}"/>&sorting=rating&sortingOrder=asc">
                <c:out value="asc"/>
            </a>&nbsp;

            <a class="link-a"
               href="${pageContext.request.contextPath}/executor?<m:cutsort queryStr="${queryString}"/>&sorting=rating&sortingOrder=desc">
                <c:out value="desc"/>
            </a>
        </div>

        <%@ include file="tours-list.jsp" %>

        <c:if test="${sessionScope.authorization.button_access!=null}">
            <a id="add-tour" href="${pageContext.request.contextPath}/executor?command=add_tour&subcommand=3">Добавить
                тур</a>
        </c:if>

        <c:if test="${fn:length(tours)!=0}">
            <%@ include file="pagination.jsp" %>
        </c:if>

    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
