<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>DMS: Developer: Project chat</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/js/chat.js" var="chatJS"/>
    <script src="${chatJS}"></script>
    <spring:url value="/resources/css/chat.css" var="chatCSS"/>
    <link href="${chatCSS}" rel="stylesheet" type="text/css">

    <style>
        #chatPanel {
            display: none;
        }

        #nonePanel {
            display: none;
        }
    </style>
</head>
<body onload="selectMenuItem('chatItem'); formChatPanel();">
<%@include file="../header.jsp" %>
<%@include file="menu.jsp" %>

<div class="content">
    <div id="nonePanel">
        There is no current project
    </div>

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

</body>
</html>
