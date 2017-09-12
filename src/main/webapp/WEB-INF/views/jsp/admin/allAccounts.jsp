<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>DMS: Admin: All accounts</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/js/admin.js" var="adminJS"/>
    <script src="${adminJS}"></script>
    <spring:url value="/resources/css/table.css" var="tableCSS"/>
    <link href="${tableCSS}" rel="stylesheet" type="text/css">
</head>
<body onload="selectMenuItem('allItem'); formAccountsTable();">
<%@include file="../header.jsp" %>
<%@include file="menu.jsp" %>

<div class="content">
    <table id="accounts" class="table">
        <tr>
            <th>ID</th>
            <th>Nickname</th>
            <th>RoleID</th>
            <th></th>
        </tr>
    </table>
</div>

</body>
</html>
