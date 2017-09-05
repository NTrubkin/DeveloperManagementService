<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 15.08.2017
  Time: 4:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/js/manager.js" var="managerJS" />
    <script src="${managerJS}"></script>
    <style>
        #body {

        }

        #leftPanel {
            float: left;
            width: 50%;
        }

        #rightPanel {
            float: right;
            width: 50%;
        }

        #currentProjectPanel {
            display: none;
        }

        #createPanel {
            display: none;
        }
    </style>
</head>
<body onload="init();">
<%@include file="header.jsp" %>
<h1>Project manager page</h1>

<div style="width: 100%;">
    <div id="leftPanel">
        <h2>My project list</h2>
        <table id="projects">
            <tr>
                <th width="20">ID</th>
                <th width="100">Name</th>
                <th width="100">Complete</th>
            </tr>
        </table>
    </div>

    <div id="rightPanel">
        <div id="createPanel">
            <h2>Create new project</h2>
            <div>
                <label>Name:</label>
                <input type="text" id="name"/>
                <button onclick="createProject()">Create</button>
            </div>
        </div>
        <div id="currentProjectPanel">
            <h2>Current project</h2>
            <p id="projectId">ID: </p>
            <p id="projectName">Name: </p>
            <p id="projectComplete">Complete: </p>
            <button onclick="completeCurrentProject()">Complete project</button>
            <h3>Developers in the project</h3>
            <table id="devs">
                <tr>
                    <th width="20">ID</th>
                    <th width="100">Nickname</th>
                </tr>
            </table>
            <h3>Available developers</h3>
            <table id="avDevs">
                <tr>
                    <th width="20">ID</th>
                    <th width="100">Nickname</th>
                </tr>
            </table>
        </div>
    </div>
</div>

</body>
</html>
