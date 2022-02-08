<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration.css" type="text/css">
    <script src="${pageContext.request.contextPath}/js/validation.js"></script>
</head>
<body>

<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="reg">
        <h1>Регистрация</h1>
        <div id="e-message">
            <c:out value="${e_message}"/>
        </div>
        <form id="form-container" action="${pageContext.request.contextPath}/executor" method="post">
            <input type="hidden" name="command" value="registration">
            <input type="hidden" name="subcommand" value="4">
            <input type="text" required name="login"  placeholder="Логин"/><br>
            <input id="pass" type="password" formnovalidate required name="password"
                   pattern="^(?=.*?[A-Z])(?=.*?[a-z])(?=(.*\d){2}).{6,}$" placeholder="Пароль"/><br>
            <span id="pass-mes"></span>
            <input id="r_pass" pattern="^(?=.*?[A-Z])(?=.*?[a-z])(?=(.*\d){2}).{6,}$" formnovalidate type="password"
                   required name="r_password" placeholder="Повторите пароль"/><br>
            <span id="not-match"></span>
            <input type="text" required name="first_name" placeholder="Имя"/><br>
            <input type="text" required name="last_name" placeholder="Фамилия"/><br>
            <input id="date" required type="date" name="birth_date"  value="1995-01-01"/><br>
            <input type="email" required name="email" placeholder="Электронная почта"/><br>
            <input id="tel" type="text" formnovalidate pattern="\+[0-9]{6,}" required name="telephone" placeholder="Номер телефона"/><br>
            <span id="tel-mes"></span>
            <c:out value="Пол:"/>
            <input type = "radio" name = "gender" value = "male"> Мужской
            <input type = "radio" name = "gender" value = "female"> Женский<br>
            <input id="submit" type="submit" name="submit" onclick="validation()" value="Зарегистрироваться">
        </form>
    </div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
