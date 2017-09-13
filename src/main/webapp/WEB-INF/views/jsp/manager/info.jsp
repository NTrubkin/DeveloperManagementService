<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 06.09.2017
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>DMS: Manager: Project info</title>

    <spring:url value="/resources/css/dialog.css" var="dialogCSS"/>
    <link href="${dialogCSS}" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/js/manager/info.js" var="managerJS"/>
    <script src="${managerJS}"></script>

    <style>
        #nonePanel{
            display: none;
        }
        #currentProjectPanel {
            display: none;
        }
    </style>
</head>
<body onload="selectMenuItem('infoItem'); init();">
<%@include file="../header.jsp" %>
<%@include file="menu.jsp" %>

<div class="content">
    <div id="nonePanel">
        There is no current project
    </div>

    <div id="currentProjectPanel">
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
            <tr>
                <td class="question"></td>
                <td class="answer">
                    <button onclick="completeCurrentProject()">Complete</button>
                </td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>
