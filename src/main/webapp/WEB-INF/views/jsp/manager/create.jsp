<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 09.09.2017
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>DMS: Manager: Create project</title>

    <spring:url value="/resources/css/dialog.css" var="dialogCSS"/>
    <link href="${dialogCSS}" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/js/manager/create.js" var="managerJS"/>
    <script src="${managerJS}"></script>
</head>
<body>
<%@include file="../header.jsp"%>
<%@include file="menu.jsp"%>

<div class="content">
    <div>
        <table class="dialog">
            <tr>
                <td class="question">Name</td>
                <td class="answer"><input id="name"/></td>
            </tr>
            <tr>
                <td class="question">Estimated end date</td>
                <td class="answer"><input type="date" id="estEndDate"></td>
            </tr>
            <tr>
                <td class="question">Estimated end time</td>
                <td class="answer"><input type="time" id="estEndTime"></td>
            </tr>
            <tr>
                <td class="question"></td>
                <td class="answer">
                    <button onclick="createProject()">Create</button>
                </td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>
