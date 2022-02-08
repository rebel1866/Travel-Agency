<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<c:set var="isAdmin"
       value="${sessionScope.authorization.button_access!=null}"/>
<div id="navbar">

    <c:if test="${isAdmin}">
        <p class="nav-text"><a class="nav-href" href="${pageContext.request.contextPath}/executor?command=orders&page=1">
            <fmt:message key="label.orders"/></a></p>
    </c:if>

    <c:if test="${isAdmin}">
        <p class="nav-text"><a class="nav-href" href="${pageContext.request.contextPath}/executor?command=users&page=1">
            <fmt:message key="label.users"/></a></p>
    </c:if>

    <p class="nav-text"><a class="nav-href"
                           href="${pageContext.request.contextPath}/executor?command=tours&burning_status=1&page=1">
        <fmt:message key="label.burning_tours"/></a></p>
    <p class="nav-text"><a class="nav-href" href="${pageContext.request.contextPath}/executor?command=tours&page=1">
        <fmt:message key="label.all_tours"/></a></p>
    <p class="nav-text"><a class="nav-href" href="${pageContext.request.contextPath}/executor?command=hotels&page=1">
        <fmt:message key="label.hotels"/></a></p>
    <p class="nav-text"><a class="nav-href"
                           href="${pageContext.request.contextPath}/executor?command=feedbacks&page=1&sorting=fbk_date_time&sortingOrder=desc">
        <fmt:message key="label.feedbacks"/></a></p>
    <p class="nav-text"><a class="nav-href" href="fake.jsp"><fmt:message key="label.resorts"/></a></p>
    <p class="nav-text"><a class="nav-href" href="fake.jsp"><fmt:message key="label.news"/></a></p>

    <p class="nav-text"><a class="nav-href" href="fake.jsp"><fmt:message key="label.about_us"/></a></p>
    <p class="nav-text"><a class="nav-href" href="fake.jsp"><fmt:message key="label.contacts"/></a></p>
    <c:forEach begin="1" end="6">
        </br>
    </c:forEach>
</div>
</body>
</html>
