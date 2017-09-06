<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 15.08.2017
  Time: 4:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/js/developer.js" var="developerJS"/>
    <script src="${developerJS}"></script>
    <style>
        #nullCurProjectPanel {
            display: none;
        }

        #notNullCurProjectPanel {
            display: none;
        }
    </style>
</head>
<body onload="init();">
<%@include file="header.jsp" %>
<h1>Developer page</h1>
<div>
    <div id="nullCurProjectPanel">
        <h2>Current project</h2>
        <p>There is no active project</p>
    </div>
    <div id="notNullCurProjectPanel">
        <h2>Current project</h2>
        <p id="projectId">ID: </p>
        <p id="projectName">Name: </p>
        <p id="projectComplete">Complete: </p>
    </div>
    <h2>All projects</h2>
    <table id="projects">
        <tr>
            <th width="20">ID</th>
            <th width="100">Nickname</th>
            <th width="100">Complete</th>
        </tr>
    </table>
</div>

</body>
</html>
