<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <spring:url value="/resources/css/menu.css" var="menuCSS"/>
    <link href="${menuCSS}" rel="stylesheet" type="text/css">
    <spring:url value="/resources/js/menu.js" var="menuJS"/>
    <script src="${menuJS}"></script>
</head>
<body>
<div id="menu">
    <button onclick="location.href = '${urlPrefix}${'/manager/create'}'" class="menuItem" id="createItem">Create project</button>
    <button onclick="location.href = '${urlPrefix}${'/manager/info'}'" class="menuItem" id="infoItem">Project info</button>
    <button onclick="location.href = '${urlPrefix}${'/manager/devs'}'" class="menuItem" id="devsItem">Project developers</button>
    <button onclick="location.href = '${urlPrefix}${'/manager/chat'}'" class="menuItem" id="chatItem">Project chat</button>
    <button onclick="location.href = '${urlPrefix}${'/manager/all_projects'}'" class="menuItem" id="allItem">All projects</button>
</div>
</body>
</html>
