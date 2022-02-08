<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Страница не доступна</title>
    <style>
        #style-not-available{
            text-align: center;
            margin-top: 100px;
            padding-bottom: 300px;
        }
        #link{
            text-decoration: none;
        }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="style-not-available">
        <h1><c:out value="Страница больше недоступна"/></h1>
        <a id="link" href="${pageContext.request.contextPath}${link}"><c:out value="${message}"/></a>
    </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
