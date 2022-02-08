<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вы не имеете доступа к этой странице</title>
    <style>
        h1 {
            text-align: center;
            margin: 100px 50px 100px;
        }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <h1>Ваш аккаунт был заблокирован администратором, в связи с этим вы теперь не можете иметь доступ к этой
            странице</h1>
    </div>
</div>
<jsp:include page="navbar.jsp"/>
<jsp:include page="footer.jsp"/>
</div>
</body>
</html>
