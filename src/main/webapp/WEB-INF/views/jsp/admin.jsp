<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 15.08.2017
  Time: 4:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<html>
<head>
    <title>Title</title>
    <spring:url value="/resources/js/admin.js" var="adminJS" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="${adminJS}"></script>
</head>
<body onload="formAccountsTable();">
<%@include file="header.jsp"%>
<h1>Administrator page</h1>
<h2>Create account</h2>
<div class="newAccount">
    <label>Nickname:</label>
    <input type="text" id="nickname"/>
    <label>Passhash:</label>
    <input type="password" id="passhash"/>
    <label>Role:</label>
    <select id="role">
        <option value="1">Administrator</option>
        <option value="2">Project manager</option>
        <option value="3">Developer</option>
    </select>
    <button id="addAccount" onclick="addAccount()">Add</button>
</div>
<h2>All accounts</h2>
<table id="accounts">
    <tr>
        <th width="20">ID</th>
        <th width="100">Nickname</th>
        <th width="100">RoleID</th>
    </tr>
</table>
</body>
</html>