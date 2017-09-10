var prefix = '/developer-management-service-1.0-SNAPSHOT';

function init() {
    chechCurrentProject();
    if (isCurrentProjectExists) {
        formProjectDevelopersTable();
        formAvailableDevelopersTable();
        document.getElementById('noneTables').style.display = 'none';
        document.getElementById('devsTables').style.display = 'block';
    }
    else {
        document.getElementById('devsTables').style.display = 'none';
        document.getElementById('noneTables').style.display = 'block';
    }
}

function formProjectDevelopersTable() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/' + currentProjectId + '/dev/all',
        dataType: 'json',
        async: false,
        success: function (result) {
            formProjectDevelopersTableFromJson(result, 'devsInProjectTable', 'removeFromCurrentProject', 'remove');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function formAvailableDevelopersTable() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/dev/avail',
        dataType: 'json',
        async: false,
        success: function (result) {
            formProjectDevelopersTableFromJson(result, 'availDevsTable', 'addToCurrentProject', 'add');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function formProjectDevelopersTableFromJson(json, tableId, action, label) {
    var tr;
    for (var i = 0; i < json.length; i++) {
        tr = $('<tr/>');
        tr.append("<td>" + json[i].id + "</td>");
        tr.append("<td>" + json[i].nickname + "</td>");
        tr.append("<td><a href='#' onclick='" + action + "(" + json[i].id + ");return false;'>" + label + "</a></td>");

        $('#' + tableId).append(tr);
    }
}

function addToCurrentProject(devId) {
    $.ajax({
        type: 'POST',
        url: prefix + '/project/' + currentProjectId + '/dev/' + devId,
        async: true,
        success: function () {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function removeFromCurrentProject(devId) {
    $.ajax({
        type: 'DELETE',
        url: prefix + '/project/' + currentProjectId + '/dev/' + devId,
        async: true,
        success: function () {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}