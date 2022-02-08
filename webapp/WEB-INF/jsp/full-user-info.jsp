<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Полная информация о пользователе</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/full-user.css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <h1>Информация о пользователе:</h1>
        <p class="list" ><c:out value="ID пользователя: ${user.userID}"/></p>
        <p class="list" ><c:out value="Логин: ${user.login}"/></p>
        <p class="list" ><c:out value="Имя: ${user.firstName}"/></p>
        <p class="list" ><c:out value="Фамилия: ${user.lastName}"/></p>
        <p class="list" ><c:out value="Дата рождения: ${user.birthDate}"/></p>
        <p class="list" ><c:out value="Пол: ${user.gender}"/></p>
        <p class="list" ><c:out value="Электронная почта: ${user.email}"/></p>
        <p class="list" ><c:out value="Телефон: ${user.telephone}"/></p>
        <p class="list" ><c:out value="Статус пользователя: ${user.userStatus}"/></p>
        <p class="list" ><c:out value="Роль пользователя: ${user.userRole}"/></p><br>
        <p><a id="add-user" href="${pageContext.request.contextPath}/executor?command=remove_user&user_id=${user.userID}&subcommand=4">
            <c:out value="Удалить пользователя"/></a>
            <a id="update-user"
               href="${pageContext.request.contextPath}/executor?command=update_user&user_id=${user.userID}&subcommand=3">
                <c:out value="Изменить пользователя"/></a>
            <a id="new-role"
               href="${pageContext.request.contextPath}/executor?command=give_admin_rights&user_id=${user.userID}&subcommand=3">
                <c:out value="Сделать админом"/></a>
        <a id="block"
           href="${pageContext.request.contextPath}/executor?command=block_user&user_id=${user.userID}&subcommand=main">
            <c:out value="Заблокировать"/></a></p><br><br>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
