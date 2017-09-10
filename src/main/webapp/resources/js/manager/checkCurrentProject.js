var prefix = '/developer-management-service-1.0-SNAPSHOT';
var isCurrentProjectExists = false;
var currentProjectId = 0;

function chechCurrentProject() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/',
        dataType: 'json',
        async: false,
        success: function (result) {
            currentProjectId = result.id;
            if (result.id === 0) {
                isCurrentProjectExists = false;
            }
            else {
                isCurrentProjectExists = true;
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}