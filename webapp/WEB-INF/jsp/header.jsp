<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common-styles.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" type="text/css">
</head>
<body>
<div id="header">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="messages"/>

    <c:if test="${sessionScope.authorization.user_role.equals('Guest')}">
        <a href="${pageContext.request.contextPath}/executor?command=sign_in&subcommand=1"
                                                         class="sign-in-button">
        <fmt:message key="label.sign_in"/> </a> </c:if>

    <c:if test="${!sessionScope.authorization.user_role.equals('Guest')}">
        <div class="sign-in-button">
            <div class="dropdown">
                <button class="dropbtn">${sessionScope.authorization.login}</button>
                <div class="dropdown-content">
                    <a href="${pageContext.request.contextPath}/executor?command=personal_data">
                        <fmt:message key="label.personal.data"/></a>
                    </a>
                    <a href="${pageContext.request.contextPath}/executor?command=sign_out&subcommand=main"><fmt:message
                            key="label.sign_out"/></a>
                </div>
            </div>
        </div>
    </c:if>

    <div id="lang">
        <div class="dropdown">
            <button class="dropbtn"><fmt:message key="label.lang"/></button>
            <div class="dropdown-content">
                <c:if test="${pageContext.request.queryString==null}">
                    <c:set var="temp" value="${pageContext.request.getServletPath()}"/>
                    <c:set var="queryString" value="${temp.substring(1)}"/>
                </c:if>

                <c:if test="${pageContext.request.queryString!=null}">
                    <c:set var="queryString" value="${pageContext.request.getQueryString()}"/>
                </c:if>

                <a href="${pageContext.request.contextPath}/executor?switch_lang=ru&query_string=${queryString}">Русский</a>
                <a href="${pageContext.request.contextPath}/executor?switch_lang=en&query_string=${queryString}">English</a>
            </div>
        </div>
    </div>

    <span><a id="best-tours" style="text-decoration: none;"
             href="${pageContext.request.contextPath}/executor?command=home">BEST-TOURS.BY</a></span>
</div>
</body>
</html>
