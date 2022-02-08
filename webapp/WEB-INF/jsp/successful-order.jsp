<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>
<html>
<head>
    <title>Ваша заявка успешно добавлена</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
    <style>
        #success-order{
            padding-top: 80px;
            padding-left: 30px;
            background-color: ghostwhite;
            padding-bottom: 300px;
        }
    </style>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="success-order">
            <h1>Спасибо, ваша заявка успешно добавлена! Наши менеджеры свяжутся с вами в ближайшее время.</h1>
            <h2>ID вашей заявки:  <m:printSessionInfo arg="order_id"/></h2>
        </div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
