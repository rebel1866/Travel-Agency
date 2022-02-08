<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Удаление заявки</title>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
    <h1> <c:out value="Удалить заявку № ${order_id}?"/></h1>
        <form action="${pageContext.request.contextPath}/executor" method="post">
            <input type="hidden" name="command" value="remove_order">
            <input type="hidden" name="subcommand" value="2">
            <input type="submit" name="action" value="Подтвердить">
            <input type="submit" name="action" value="Отменить">
            <input type="hidden" name="order_id" value="${order_id}">
        </form>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
