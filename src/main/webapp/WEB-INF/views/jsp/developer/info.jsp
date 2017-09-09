<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 07.09.2017
  Time: 0:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>DMS: Developer: Project info</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/js/developer.js" var="developerJS"/>
    <script src="${developerJS}"></script>
    <spring:url value="/resources/css/dialog.css" var="dialogCSS"/>
    <link href="${dialogCSS}" rel="stylesheet" type="text/css">
    <style>
        #nullCurProjectPanel {
            display: none;
        }

        #notNullCurProjectPanel {
            display: none;
        }
    </style>
</head>
<body onload="formCurrentProjectPanel();">
<%@include file="../header.jsp"%>
<%@include file="menu.jsp"%>

<div class="content">
    <div id="nullCurProjectPanel">
        There is no current project
    </div>
    <div id="notNullCurProjectPanel">
        <table class="dialog">
            <tr>
                <td class="question">Name</td>
                <td class="answer"><p id="projectName"></p></td>
            </tr>
            <tr>
                <td class="question">ID</td>
                <td class="answer"><p id="projectId"></p></td>
            </tr>
            <tr>
                <td class="question">Complete</td>
                <td class="answer"><p>false</p></td>
            </tr>
            <tr>
                <td class="question">Start</td>
                <td class="answer"><p id="projectStart"></p></td>
            </tr>
            <tr>
                <td class="question">Estimated end</td>
                <td class="answer"><p id="projectEstEnd"></p></td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>
