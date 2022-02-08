<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Произошла ошибка</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/error-page.css">
</head>
<body>

<div id="container">
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <div id="content">
        <c:set var="isAdmin"
               value="${sessionScope.authorization.button_access!=null}"/>

        <div id="error-content">
            <h1 class="<c:if test="${isAdmin==true}">error-admin</c:if><c:if test="${isAdmin==false}">error-common</c:if>">
                <c:out value=" Произошла ошибка..."/><br>
                <p><a href="${pageContext.request.contextPath}/executor?command=home">HOME</a></p>
            </h1>

            <c:if test="${isAdmin==true}">
                <h5><c:out value="Ошибка: ${pageContext.exception}"/><br><br></h5>
                <table>
                    <c:forEach var="i" begin="0" end="${fn:length(pageContext.exception.stackTrace)}">
                        <tr class="<c:if test="${i%2==0}">tr-even</c:if>">
                            <td><c:out value="${pageContext.exception.stackTrace[i]}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
