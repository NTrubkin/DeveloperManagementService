<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <spring:url value="/resources/css/menu.css" var="menuCSS"/>
    <link href="${menuCSS}" rel="stylesheet" type="text/css">
</head>
<body>
<div id="menu">
    <button onclick="location.href = '${urlPrefix}${'/manager/create'}'" class="menuItem">Create project</button>
    <button onclick="location.href = '${urlPrefix}${'/manager/info'}'" class="menuItem">Project info</button>
    <button onclick="location.href = '${urlPrefix}${'/manager/devs'}'" class="menuItem">Project developers</button>
    <button onclick="location.href = '${urlPrefix}${'/manager/chat'}'" class="menuItem">Project chat</button>
    <button onclick="location.href = '${urlPrefix}${'/manager/all_projects'}'" class="menuItem">All projects</button>
</div>
</body>
</html>
