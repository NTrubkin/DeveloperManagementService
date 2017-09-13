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
    <spring:url value="/resources/js/chat.js" var="chatJS"/>
    <script src="${chatJS}"></script>
    <spring:url value="/resources/css/chat.css" var="chatCSS"/>
    <link href="${chatCSS}" rel="stylesheet" type="text/css">

    <style>
        #nonePanel{
            display: none;
        }
        #currentProjectPanel {
            display: none;
        }
        #completeButton {
            display: none;
        }
        #reopenButton {
            display: none;
        }
        #openDevsButton {
            display: none;
        }
        #chatPanel {
            display: none;
        }
    </style>
</head>
<body onload="selectMenuItem('infoItem'); init(${projectId});">
<%@include file="../header.jsp" %>
<%@include file="menu.jsp" %>

<div class="content">
    <div>
        <table class="dialog">
            <tr>
                <td class="question">Select Project</td>
                <td class="answer">
                    <select id="projectSelector" onchange="onSelectorChanged()">
                        <option value="0">-</option>
                    </select>
                </td>
            </tr>
        </table>
    </div>

    <div id="nonePanel">
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
                <td class="answer"><p id="projectComplete"></p></td>
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
                    <button id="completeButton" onclick="completeProject(${projectId})">Complete</button>
                    <button id="reopenButton" onclick="reopenProject(${projectId})">Reopen</button>
                    <button id="openDevsButton" onclick="location.href = '${urlPrefix}${'/manager/devs/'}${projectId}'">Show Developer</button>
                </td>
            </tr>
        </table>

        <div id="chatPanel">
            <div id="newComment">
                <input id="newText">
                <button id="sendButton" onclick="sendCommentary()">send</button>
                <hr>
            </div>
            <div id="comments">
            </div>
        </div>

    </div>
</div>

</body>
</html>
