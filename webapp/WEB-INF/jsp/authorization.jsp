<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Вход</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/authorizationn.css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">

        <div class="authoriz">
            <h1>Вход в аккаунт</h1>

            <form action="${pageContext.request.contextPath}/executor" method="post">
                <input type="hidden" name="command" value="sign_in">
                <input type="hidden" name="subcommand" value="main">
                <input class="auth-element" required type="text" name="login" placeholder="Логин"/><br>
                <input class="auth-element" required type="password" name="password" placeholder="Пароль"/><br>

                <div id="sign_in_mes"><c:if test="${requestScope.sign_in_mes!=null}">
                    <c:out value="${requestScope.sign_in_mes}"/>
                </c:if>
                </div>
                <a id="forgot-pass" href="${pageContext.request.contextPath}/executor?command=reset_password&subcommand=2">
                    <c:out value="Не помню пароль"/></a><br>
                <a id="registration"
                   href="${pageContext.request.contextPath}/executor?command=registration&subcommand=1">
                    <c:out value="Нет аккаунта? Зарегистрируйтесь"/></a><br>
                <input id="submit" type="submit" name="submit" value="Войти">

            </form>
        </div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
