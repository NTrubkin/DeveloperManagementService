var prefix = '/developer-management-service-1.0-SNAPSHOT';

function formCurrentProjectPanel() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/',
        dataType: 'json',
        async: true,
        success: function (result) {
            if (result.id === 0) {
                document.getElementById('notNullCurProjectPanel').style.display = 'none';
                document.getElementById('nullCurProjectPanel').style.display = 'block';
            }
            else {
                $('#projectId').append(result.id);
                $('#projectName').append(result.name);
                var start = new Date(result.start);
                $('#projectStart').append(start.toLocaleString());
                var estEnd = new Date(result.estimatedEnd);
                $('#projectEstEnd').append(estEnd.toLocaleString());
                document.getElementById('nullCurProjectPanel').style.display = 'none';
                document.getElementById('notNullCurProjectPanel').style.display = 'block';
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function formAllProjectsTable() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/all_my/',
        dataType: 'json',
        async: true,
        success: function (result) {
            formProjectsTableFromJson(result);
        },
        error: function (jqXHR, textStatus, errorThrown) {
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
        tr.append("<td>" + new Date(json[i].start).toLocaleString() + "</td>");
        tr.append("<td>" + new Date(json[i].estimatedEnd).toLocaleString() + "</td>");
        if (json[i].end === null) {
            tr.append("<td> - </td>");
        }
        else {
            tr.append("<td>" + new Date(json[i].end).toLocaleString() + "</td>");
        }
        tr.append("<td>" + json[i].complete + "</td>");
        $('#projects').append(tr);
    }
}