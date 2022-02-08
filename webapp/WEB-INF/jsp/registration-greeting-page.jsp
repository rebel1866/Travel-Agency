<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Поздравляем с успешной регистрацией!</title>
    <style>
        h1{
            text-align: center;
            margin-top: 70px;
            padding-bottom: 300px;
        }
        a{
            text-decoration: none;
        }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <h1>Добро пожаловать <m:printSessionInfo arg="user_name"/> <m:printSessionInfo arg="user_last_name"/><br><br>
        <a href="${pageContext.request.contextPath}/executor?command=home">HOME</a>
        </h1>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
