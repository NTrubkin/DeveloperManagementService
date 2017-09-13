var prefix = '/developer-management-service-1.0-SNAPSHOT';
var projectId = 0;
function init(projectToOpen) {
    projectId = projectToOpen;
    formProjectsComboBox(projectId);
    if (projectId === 0) {
        document.getElementById('devsTables').style.display = 'none';
        document.getElementById('noneTables').style.display = 'block';
    }
    else {
        formProjectDevelopersTable();
        formAvailableDevelopersTable();
        document.getElementById('noneTables').style.display = 'none';
        document.getElementById('devsTables').style.display = 'block';
    }
}

function formProjectDevelopersTable() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/' + projectId + '/dev/all',
        dataType: 'json',
        async: false,
        success: function (result) {
            formProjectDevelopersTableFromJson(result, 'devsInProjectTable', 'removeFromCurrentProject', 'remove');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
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
            alert(jqXHR.responseText);
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
        url: prefix + '/project/' + projectId + '/dev/' + devId,
        async: true,
        success: function () {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });
}

function removeFromCurrentProject(devId) {
    $.ajax({
        type: 'DELETE',
        url: prefix + '/project/' + projectId + '/dev/' + devId,
        async: true,
        success: function () {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });
}

function formProjectsComboBox(defaultId) {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/',
        dataType: 'json',
        async: false,
        success: function (result) {
            formProjectsComboBoxFromJson(result, defaultId);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });
}

function formProjectsComboBoxFromJson(result, defaultId) {
    for(var i = 0; i < result.length; i++) {
        $('#projectSelector')
            .append($("<option></option>")
                .attr("value",result[i].id)
                .text("#" + result[i].id + " : " + result[i].name));
        if(result[i].id === defaultId) {
            $('#projectSelector').val(defaultId);
        }
    }
}

function onSelectorChanged() {
    window.location.replace(prefix + '/manager/devs/' + $('#projectSelector').val());
}