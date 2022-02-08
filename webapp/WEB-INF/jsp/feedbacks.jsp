<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/feedbacks.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/rating.css">
<html>
<head>
    <title>Отзывы о нашей компании</title>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="fbk-content">
            <c:forEach items="${feedbacks}" var="feedback">
                <div id="head-fbk">

                    <div class="first-last-name">

                        <c:if test="${sessionScope.authorization.button_access!=null}">
                        <a href="${pageContext.request.contextPath}/executor?command=full_user_info&user_id=${feedback.user.userID}">
                            </c:if>

                            <c:out value="${feedback.user.firstName} ${feedback.user.lastName} "/>
                        </a>

                    </div>

                    <span class="date-time">
                <fmt:parseDate value="${ feedback.fbkDateTime }" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime"
                               type="both"/>
            <fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${ parsedDateTime }"/>
            </span><br/>

                    <div class="hotel_name_fbk">
                        <a href="${pageContext.request.contextPath}/executor?command=full_hotel_info&hotel_id=${feedback.tour.hotel.hotelId}">
                            <c:out value="${feedback.tour.hotel.hotelName}"/></a>
                        <div class="rating">
                            <c:forEach var="i" begin="1" end="${feedback.fbkRating}">
                                <span class="active"></span>
                            </c:forEach>
                        </div>

                        <c:set value="${pageContext.request.queryString}" var="qStr"/>
                        <c:set var="quStr" value="${fn:replace(qStr,'&','::')}"/>


                        <c:set var="isAdmin"
                               value="${sessionScope.authorization.button_access!=null}"/>
                        <c:if test="${isAdmin}"><br><br>
                            <a href="${pageContext.request.contextPath}/executor?command=remove_feedback&fbk_id=${feedback.feedbackId}&subcommand=main&qStr=${quStr}">
                                <c:out value="Удалить отзыв"/></a>
                        </c:if>
                    </div>
                </div>

                <div class="fbk-body">
                    <c:out value=" ${feedback.feedbackBody}"/>
                </div>
                </td>
            </c:forEach>
            <%@include file="pagination.jsp" %>
        </div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
