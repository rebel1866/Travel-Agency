<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Not Allowed to do this action</title>
    <style>
        h1 {
            margin-left: 50px;
            margin-top: 150px;
            background-color: lightblue;
            margin-right: 50px;
            border-radius: 10px;
            padding: 20px;
        }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="not-allowed"><h1>У вас недостаточно прав для выполнения данного действия</h1></div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
