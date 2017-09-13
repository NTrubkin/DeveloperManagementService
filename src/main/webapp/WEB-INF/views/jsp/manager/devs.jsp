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
    <spring:url value="/resources/css/dialog.css" var="dialogCSS"/>
    <link href="${dialogCSS}" rel="stylesheet" type="text/css">

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
<body onload="selectMenuItem('devsItem'); init(${projectId})">
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

    <div id="noneTables">
    </div>
    <div id="devsTables">
        <div id="devsInProject">
            <p>In project</p>
            <table id="devsInProjectTable" class="table">
                <tr>
                    <th>ID</th>
                    <th>Nickname</th>
                    <th></th>
                </tr>
            </table>
        </div>
        <div id="availDevs">
            <p>Available</p>
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
