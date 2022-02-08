<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Удаление тура</title>
    <style>
       #tour-remove{
           margin-left: 30px;
           padding-bottom: 250px;
       }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="tour-remove">
        <h1>Внимание!</h1>
        <h3>При удалении тура будут также удалены вся связанные с ним отзывы и регистрации.</h3>
        <h4>Удалить <c:out value="${tour_name}"/> ?</h4>
        <a href="${pageContext.request.contextPath}/executor?command=remove_tour&action=accept&subcommand=1&tour_id=${tour_id}">
            <c:out value="Подтвердить"/></a>
        <a href="${pageContext.request.contextPath}/executor?command=remove_tour&action=deny&subcommand=1&tour_id=${tour_id}">
            <c:out value="Отменить"/></a>
        </div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
