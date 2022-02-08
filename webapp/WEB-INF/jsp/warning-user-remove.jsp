<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Удаление пользователя</title>
    <style>
        #content-user-remove{
            margin-left: 50px;
            padding-bottom: 400px;
        }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="content-user-remove">
        <h1> <c:out value="Удалить пользователя ID № ${user_id}?"/></h1>
        <form action="${pageContext.request.contextPath}/executor" method="post">
            <input type="hidden" name="command" value="remove_user">
            <input type="hidden" name="subcommand" value="2">
            <input type="submit" name="action" value="Подтвердить">
            <input type="submit" name="action" value="Отменить">
            <input type="hidden" name="user_id" value="${user_id}">
        </form>
    </div></div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
