<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration.css" type="text/css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="reg">
            <h2>Добавить пользователя:</h2>
        <div id="e-message">
            <c:out value="${e_message}"/>
        </div>
        <form id="form-container" action="${pageContext.request.contextPath}/executor" method="post">
            <input type="hidden" name="command" value="add_user">
            <input type="hidden" name="subcommand" value="main">
            <input required type="text" name="login" placeholder="Логин"/><br>
            <input required type="password" name="password" placeholder="Пароль"/><br>
            <input required type="password" name="r_password" placeholder="Повторите пароль"/><br>
            <input required type="text" name="first_name" placeholder="Имя"/><br>
            <input required type="text" name="last_name" placeholder="Фамилия"/><br>
            <input required id="date" type="date" name="birth_date"  value="1995-01-01"/><br>
            <input required type="email" name="email" placeholder="Электронная почта"/><br>
            <input required type="text" name="telephone" placeholder="Номер телефона"/><br>
            <c:out value="Пол:"/>
            <input required type = "radio" name = "gender" value = "male"> Мужской
            <input required type = "radio" name = "gender" value = "female"> Женский<br>
            <input id="submit" type="submit" name="submit" value="Добавить пользователя">
        </form>
        </div></div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
