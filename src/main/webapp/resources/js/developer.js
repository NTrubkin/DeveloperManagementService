function formCurrentProjectPanel() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/',
        dataType: 'json',
        async: true,
        success: function (result) {
            if (result.length === 0) {
                document.getElementById('notNullCurProjectPanel').style.display = 'none';
                document.getElementById('nullCurProjectPanel').style.display = 'block';
            }
            else {
                $('#projectId').append(result[0].id);
                $('#projectName').append(result[0].name);
                var start = new Date(result[0].start);
                $('#projectStart').append(start.toLocaleString());
                var estEnd = new Date(result[0].estimatedEnd);
                $('#projectEstEnd').append(estEnd.toLocaleString());
                document.getElementById('nullCurProjectPanel').style.display = 'none';
                document.getElementById('notNullCurProjectPanel').style.display = 'block';
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
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
            alert(jqXHR.responseText);
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

function formChat() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/',
        dataType: 'json',
        async: false,
        success: function (result) {
            if(result.length === 0) {
                document.getElementById('nonePanel').style.display = 'block';
            }
            else {
                formChatPanel(result[0].id);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });
}