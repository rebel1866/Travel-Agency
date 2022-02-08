<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>
<html>
<head>
    <style>
        .pagination {
            margin-left: 280px;
            text-decoration: none;
        }
        a{
            text-decoration: none;
        }
    </style>
</head>
<body>

<c:set var="space" value="&nbsp&nbsp&nbsp"/>
<c:set var="step" value="5"/>

<p class="pagination">
    <c:set var="queryString" value="${pageContext.request.getQueryString()}"/>

    <c:if test="${pagination.currentPage>=step+1}">
        <a href="${pageContext.request.contextPath}/executor?<m:cutpage queryStr="${queryString}"/>&page=1">
            <c:out value="первая"/></a>
    </c:if>
    ${space}

    <c:if test="${pagination.currentPage!=1}">
        <a href="${pageContext.request.contextPath}/executor?<m:cutpage queryStr="${queryString}"/>&page=${pagination.currentPage -1}">
            <c:out value="предыдущая"/></a>
    </c:if>
    ${space}

    <c:forEach var="i" begin="${pagination.startPoint}" end="${pagination.endPoint}">
        <a href="${pageContext.request.contextPath}/executor?<m:cutpage queryStr="${queryString}"/>&page=${i}">
            <c:if test="${pagination.currentPage==i}">
            <strong style="color: black">
                </c:if>
                <c:out value="${i}"/>
            </strong>
        </a>
    </c:forEach>
    ${space}

    <c:if test="${pagination.currentPage!=pagination.totalPages}">
        <a href="${pageContext.request.contextPath}/executor?<m:cutpage queryStr="${queryString}"/>&page=${pagination.currentPage +1}">
            <c:out value="следующая"/></a>
    </c:if>
    ${space}

    <c:if test="${pagination.currentPage <= pagination.totalPages - step}">
        <a href="${pageContext.request.contextPath}/executor?<m:cutpage queryStr="${queryString}"/>&page=${pagination.totalPages}">
            <c:out value="последняя"/></a>
    </c:if>
</p>
</body>
</html>
