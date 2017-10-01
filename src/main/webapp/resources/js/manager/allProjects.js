function init() {
    formProjectsTable();
}

function formProjectsTable() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/all_my/',
        dataType: 'json',
        async: false,
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