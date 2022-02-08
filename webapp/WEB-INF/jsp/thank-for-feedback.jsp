<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Спасибо за ваш отзыв</title>
    <style>
        h1 {
            margin: 0;
            text-align: center;
            padding-top: 110px;
        }

        p {
            text-align: center;
        }
        #tc{
            padding-bottom: 300px;
            background-color: ghostwhite;
        }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="tc">
            <h1>Спасибо за ваш отзыв!</h1>
            <p><a href="${pageContext.request.contextPath}${link}"><c:out value="Назад"/></a></p>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
