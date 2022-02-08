<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Выберите роль</title>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <form action="${pageContext.request.contextPath}/executor" method="post">
            <input type="hidden" name="command" value="give_admin_rights">
            <input type="hidden" name="subcommand" value="main">
            <select name="user_role_id">
                <c:forEach var="role" items="${roles}">
                    <option value="${role.userRoleId}"><c:out value="${role.userRoleName}"/></option>
                </c:forEach>
            </select><br>
            <input type="submit" name="submit" value="Подтвердить">
        </form>

    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
