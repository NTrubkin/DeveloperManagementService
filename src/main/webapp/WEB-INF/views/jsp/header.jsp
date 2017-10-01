<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}"/>
<c:set var="urlPrefix" value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}"/>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/css/header.css" var="headerCSS"/>
    <link href="${headerCSS}" rel="stylesheet" type="text/css">

    <script>
        var prefix = '${urlPrefix}';
    </script>
    <spring:url value="/resources/js/header.js" var="headerJS"/>
    <script src="${headerJS}"></script>

</head>
<body id="body">
<div id="header">
    <div id="leftHeader">
        <button onclick="location.href= '${urlPrefix}${'/'}'" class="button"><strong>DEVELOPER MANAGEMENT
            SYSTEM</strong></button>
    </div>
    <div id="centerHeader">
        <button onclick="location.href = '${urlPrefix}${'/admin'}'" class="button navButton" id="adminButton">ADMIN
        </button>
        <button onclick="location.href = '${urlPrefix}${'/manager'}'" class="button navButton" id="managerButton">
            MANAGER
        </button>
        <button onclick="location.href = '${urlPrefix}${'/developer'}'" class="button navButton" id="devButton">
            DEVELOPER
        </button>
    </div>
    <div id="rightHeader">
        <p id="user"></p>
        <button onclick="location.href = '${urlPrefix}${'/login'}'" class="button" id="login"><img
                src="${urlPrefix}${'/resources/icons/login.png'}"/></button>
    </div>
</div>
</body>
</html>