<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>DMS: Admin: Create account</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/js/admin.js" var="adminJS"/>
    <script src="${adminJS}"></script>
    <spring:url value="/resources/css/dialog.css" var="dialogCSS"/>
    <link href="${dialogCSS}" rel="stylesheet" type="text/css">
</head>
<body onload="selectMenuItem('createItem')">
<%@include file="../header.jsp" %>
<%@include file="menu.jsp" %>

<div class="content">
    <table id="accounts" class="dialog">
        <tr>
            <td class="question">Nickname</td>
            <td class="answer"><input id="nickname"/></td>
        </tr>
        <tr>
            <td class="question">Password</td>
            <td class="answer"><input type="password" id="password"/></td>
        </tr>
        <tr>
            <td class="question">Role</td>
            <td class="answer">
                <select id="role">
                    <option value="1">Administrator</option>
                    <option value="2">Project manager</option>
                    <option value="3">Developer</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="question"></td>
            <td class="answer">
                <button id="createAccount" onclick="createAccount()">Create</button>
            </td>
        </tr>
    </table>
</div>

</body>
</html>
