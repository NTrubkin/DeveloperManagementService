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
    <button onclick="location.href = '${urlPrefix}${'/admin/create_acc'}'" class="menuItem" id="createItem">Create account</button>
    <button onclick="location.href = '${urlPrefix}${'/admin/all_accs'}'" class="menuItem" id="allItem">All accounts</button>
</div>
</body>
</html>
