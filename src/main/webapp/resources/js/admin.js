var prefix = '/developer-management-service-1.0-SNAPSHOT';

var createAccount = function () {
    var account = {
        'id': 0,
        'nickname': $("#nickname").val(),
        'roleId': document.getElementById("role").options[document.getElementById("role").selectedIndex].value,
        'passhash': $("#password").val()
    };
    $.ajax({
        type: 'POST',
        url: prefix + "/account/",
        contentType: 'application/json; charset=utf-8',
        async: true,
        data: JSON.stringify(account),
        success: function (result) {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
};

function formAccountsTable() {
    $.ajax({
        type: 'GET',
        url: prefix + '/account/all/',
        dataType: 'json',
        async: true,
        success: function (result) {
            formAccountsTableFromJson(result);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function formAccountsTableFromJson(json) {
    var tr;
    for (var i = 0; i < json.length; i++) {
        tr = $('<tr/>');
        tr.append("<td>" + json[i].id + "</td>");
        tr.append("<td>" + json[i].nickname + "</td>");
        tr.append("<td>" + roleCodeToType(json[i].roleId) + "</td>");
        tr.append("<td><a href='#' onclick='deleteAccount(" + json[i].id + ");return false;'>X</a></td>");
        $('#accounts').append(tr);
    }
}

function deleteAccount(id) {
    $.ajax({
        type: 'DELETE',
        url: prefix + '/account/' + id,
        async: true,
        success: function (result) {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function roleCodeToType(code) {
    switch (code) {
        case 1:
            return 'Administrator';
        case 2:
            return 'Project manager';
        default:
            return 'Developer';
    }
}