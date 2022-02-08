<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменение личной информации</title>
    <link href="${pageContext.request.contextPath}/css/change-user.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <h2>Изменение личной информации:</h2>
        <form action="${pageContext.request.contextPath}/executor" method="post">
            <input type="hidden" name="command" value="${command}">
            <input type="hidden" name="subcommand" value="main">
            <input type="hidden" name="user_id" value="${user.userID}">
            <input type="hidden" name="old_login" value="${user.login}">
            <label><c:out value="Логин:"/>
                <input type="text" name="login" value="${user.login}">
            </label><br>
            <label><c:out value="Старый пароль:"/>
                <input type="password" name="old-password">
            </label><br>
            <label><c:out value="Новый пароль:"/>
                <input type="password" name="new-password1">
            </label><br>
            <label><c:out value="Повторите пароль:"/>
                <input type="password" name="new-password2">
            </label><br>
            <label><c:out value="Имя:"/>
                <input type="text" name="first_name" value="${user.firstName}">
            </label><br>
            <label><c:out value="Фамилия:"/>
                <input type="text" name="last_name" value="${user.lastName}">
            </label><br>
            <label><c:out value="Дата рождения:"/>
                <input type="date" name="birth_date" value="${user.birthDate}">
            </label><br>
            <label><c:out value="Почта:"/>
                <input type="text" name="email" value="${user.email}">
            </label><br>
            <label><c:out value="Пол:"/>
                <input type = "radio"  <c:if test="${user.gender.equals('MALE')}">checked</c:if>
                       name = "gender" value = "male">
            </label> Мужской
            <input type = "radio"  <c:if test="${user.gender.equals('FEMALE')}">checked</c:if>
                   name = "gender" value = "female"> Женский<br>
            <label><c:out value="Номер телофона:"/>
                <input type="text" name="telephone" value="${user.telephone}">
            </label>
            <c:out value="${e_message}"/>
            <input id="button-change-personal" name="submit" value="Изменить" type="submit">
        </form>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
