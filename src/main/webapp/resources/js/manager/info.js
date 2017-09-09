var prefix = '/developer-management-service-1.0-SNAPSHOT';

function init() {
    formCurrentProjectPanel();
}

function formCurrentProjectPanel() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/',
        dataType: 'json',
        async: false,
        success: function (result) {
            if (result.id === 0) {
                document.getElementById('currentProjectPanel').style.display = 'none';
                document.getElementById('nonePanel').style.display = 'block';
            }
            else {
                $('#projectId').append(result.id);
                $('#projectName').append(result.name);
                $('#projectStart').append(new Date(result.start).toLocaleString());
                $('#projectEstEnd').append(new Date(result.estimatedEnd).toLocaleString());
                document.getElementById('nonePanel').style.display = 'none';
                document.getElementById('currentProjectPanel').style.display = 'block';
            }

        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function completeCurrentProject() {
    $.ajax({
        type: 'PUT',
        url: prefix + '/project/current/complete',
        async: true,
        success: function () {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}