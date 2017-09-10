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
    <title>DMS: Manager: Project developers</title>

    <spring:url value="/resources/css/table.css" var="tableCSS"/>
    <link href="${tableCSS}" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/js/manager/devs.js" var="managerJS"/>
    <script src="${managerJS}"></script>
    <spring:url value="/resources/js/manager/checkCurrentProject.js" var="checkCurrentProjectJS"/>
    <script src="${checkCurrentProjectJS}"></script>

    <style>
        #noneTables {
            display: none;
        }

        #devsTables {
            display: none;
        }

        #devsInProject {
            float: left;
        }

        #availDevs {
            float: left;
            margin: 0px 8px;
        }
    </style>
</head>
<body onload="init()">
<%@include file="../header.jsp" %>
<%@include file="menu.jsp" %>

<div class="content">
    <div id="noneTables">
        There is no current project
    </div>
    <div id="devsTables">
        <div id="devsInProject">
            <table id="devsInProjectTable" class="table">
                <tr>
                    <th>ID</th>
                    <th>Nickname</th>
                    <th></th>
                </tr>
            </table>
        </div>
        <div id="availDevs">
            <table id="availDevsTable" class="table">
                <tr>
                    <th>ID</th>
                    <th>Nickname</th>
                    <th></th>
                </tr>
            </table>
        </div>
    </div>
</div>

</body>
</html>
