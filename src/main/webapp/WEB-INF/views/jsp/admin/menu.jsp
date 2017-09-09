<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <spring:url value="/resources/css/menu.css" var="menuCSS"/>
    <link href="${menuCSS}" rel="stylesheet" type="text/css">
</head>
<body>
<div id="menu">
    <button onclick="location.href = '${urlPrefix}${'/admin/create_acc'}'" class="menuItem">Create account</button>
    <button onclick="location.href = '${urlPrefix}${'/admin/all_accs'}'" class="menuItem">All accounts</button>
</div>
</body>
</html>
