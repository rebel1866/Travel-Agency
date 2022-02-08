<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/feedbacks-list.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tables.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/rating.css">
</head>
<body>
<table>
    <tr>
        <c:forEach var="feedback" items="${feedbacks}" varStatus="i">
        <c:if test="${i.index != 0 && i.index % 2 == 0}">
    </tr>
    <tr>
        </c:if>
        <td id="fbk-container">
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

            <div class="tour_name_fbk">
                <a class="tour_name_link"
                   href="${pageContext.request.contextPath}/executor?command=full_hotel_info&hotel_id=${feedback.tour.hotel.hotelId}">
                    <c:out value="${feedback.tour.hotel.hotelName}"/></a>
                <div class="rating">
                    <c:forEach var="i" begin="1" end="${feedback.fbkRating}">
                        <span class="active"></span>
                    </c:forEach>
                </div>
            </div>

            <span class="fbk-body">
                <br><br>
                <c:set var="fbk_length" value="410"/>
                <c:if test="${fn:length(feedback.feedbackBody)>fbk_length}">
                    <c:set var="fbkSubstring" value="${fn:substring(feedback.feedbackBody, 0, fbk_length)}"/>
                    <c:out value=" ${fbkSubstring}....."/>
                </c:if>

                 <c:if test="${fn:length(feedback.feedbackBody)<fbk_length}">
                     <c:out value="${feedback.feedbackBody}"/>
                 </c:if>
            </span>

            <c:set var="isAdmin" value="${sessionScope.authorization.button_access!=null}"/>
            <c:if test="${isAdmin}"><br><br>
                <c:set value="${pageContext.request.queryString}" var="qStr"/>
                <c:set var="quStr" value="${fn:replace(qStr,'&','::')}"/>
                <a style="text-decoration: none"
                   href="${pageContext.request.contextPath}/executor?command=remove_feedback&fbk_id=${feedback.feedbackId}&subcommand=main&qStr=${quStr}">
                    <c:out value="Удалить отзыв"/></a>
            </c:if>
        </td>
        </c:forEach>
    </tr>
</table>
</body>
</html>
