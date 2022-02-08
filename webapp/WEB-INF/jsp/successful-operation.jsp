<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Successful operation</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/successful-op.css">
    <style>
        #link{
            text-decoration: none;
        }
        #back-link{
            text-align: center;
        }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
        <div id="content">
            <div id="success">
            <h1>Операция прошла успешно!</h1>
                <p id="back-link"><a id="link" href="${pageContext.request.contextPath}${link}">
                    <c:out value="${message}"/></a></p>
        </div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
