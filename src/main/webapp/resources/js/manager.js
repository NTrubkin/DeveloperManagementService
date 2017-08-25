var prefix = '/developer-management-service-1.0-SNAPSHOT';
var currentProjectId = 0;

var createProject = function () {
    var project = {
        'id': 0,
        'name': $("#name").val(),
        'complete': false,
        'managerId': 0
    };
    $.ajax({
        type: 'POST',
        url:  prefix + "/project/",
        contentType: 'application/json; charset=utf-8',
        async: true,
        data: JSON.stringify(project),
        success: function(project) {
            window.location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
};

function init() {
    formCurrentProjectPanel();
    formProjectsTable();
}

function formProjectsTable() {
    $.ajax({
        type: 'GET',
        url:  prefix + '/project/all_my/',
        dataType: 'json',
        async: true,
        success: function(result) {
            formProjectsTableFromJson(result);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function formProjectsTableFromJson(json) {
        var tr;
        for (var i = 0; i < json.length; i++) {
            tr = $('<tr/>');
            tr.append("<td>" + json[i].id + "</td>");
            tr.append("<td>" + json[i].name + "</td>");
            tr.append("<td>" + json[i].complete + "</td>");
            if(currentProjectId === 0) {
                tr.append("<td><a href='#' onclick='reopen(" + json[i].id +");return false;'>reopen</a></td>");
            }
            $('#projects').append(tr);
        }
}

function reopen(id) {
    $.ajax({
        type: 'PUT',
        url:  prefix + '/project/' + id + '/active',
        async: true,
        success: function() {
            window.location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function formCurrentProjectPanel() {
    $.ajax({
        type: 'GET',
        url:  prefix + '/project/',
        dataType: 'json',
        async: true,
        success: function(result) {
            if(result.id === 0) {
                document.getElementById('currentProjectPanel').style.display = 'none';
                document.getElementById('createPanel').style.display = 'block';
            }
            else {
                currentProjectId = result.id;
                $('#projectId').append(result.id);
                $('#projectName').append(result.name);
                $('#projectComplete').append(result.complete.toString());
                formProjectDevelopersTable();
                formAvailableDevelopersTable();
                document.getElementById('createPanel').style.display = 'none';
                document.getElementById('currentProjectPanel').style.display = 'block';
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function completeCurrentProject() {
    if(currentProjectId != 0) {
        $.ajax({
            type: 'PUT',
            url:  prefix + '/project/current/complete',
            async: true,
            success: function() {
                window.location.reload();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    }
}

function formProjectDevelopersTable() {
    $.ajax({
        type: 'GET',
        url:  prefix + '/project/' + currentProjectId + '/dev/all',
        dataType: 'json',
        async: true,
        success: function(result) {
            formProjectDevelopersTableFromJson(result, 'devs', 'removeFromCurrentProject', 'remove');
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function formAvailableDevelopersTable() {
    $.ajax({
        type: 'GET',
        url:  prefix + '/project/dev/avail',
        dataType: 'json',
        async: true,
        success: function(result) {
            formProjectDevelopersTableFromJson(result, 'avDevs', 'addToCurrentProject', 'add');
        },
        error: function(jqXHR, textStatus, errorThrown) {
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
            tr.append("<td><a href='#' onclick='" + action + "(" + json[i].id +");return false;'>" + label + "</a></td>");

            $('#' + tableId).append(tr);
        }
}

function addToCurrentProject(devId) {
    $.ajax({
        type: 'POST',
        url:  prefix + '/project/' + currentProjectId + '/dev/' + devId,
        async: true,
        success: function() {
            window.location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function removeFromCurrentProject(devId) {
    $.ajax({
        type: 'DELETE',
        url:  prefix + '/project/' + currentProjectId + '/dev/' + devId,
        async: true,
        success: function() {
            window.location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}