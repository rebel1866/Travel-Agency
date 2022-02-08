
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In Demand</title>
    <style>
        h1 {
            margin-left: 50px;
            margin-top: 150px;
            background-color: lightblue;
            margin-right: 50px;
            border-radius: 10px;
            padding: 60px;
        }
        h2{
            text-align: center;
        }
        a{
            text-decoration: none;
        }

        #sign-in-demand {
            padding-bottom: 250px;
            background-color: ghostwhite;
            padding-top: 10px;
        }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="sign-in-demand"><h1>Чтобы посетить эту страницу, войдите в аккаунт или зарегистрируйтесь</h1>
        <h2><a href="${pageContext.request.contextPath}/executor?command=sign_in&subcommand=1">ВОЙТИ В АККАУНТ</a> </h2></div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
