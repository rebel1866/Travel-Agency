<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Сбросить пароль</title>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div style="text-align: center;padding-bottom: 300px">
            <h2 style="margin-left: 50px;margin-right: 50px"><c:out
                    value="Ваш пароль будет сброшен. Новый пароль будет выслан на почту, указанную при регистрации."/></h2>
            <form action="${pageContext.request.contextPath}/executor" method="post">
                <input type="text" style="margin-bottom: 10px" name="login" placeholder="Введите логин"><br>
                <input type="text" style="margin-bottom: 10px" name="email" placeholder="Введите почту"><br>
                <input type="hidden" name="command" value="reset_password">
                <input type="hidden" name="subcommand" value="1">
                <c:out value="${e_message}"/>
                <input type="submit" name="action" value="Подтвердить">
                <input type="submit" name="action" value="Отменить">
            </form>
        </div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
