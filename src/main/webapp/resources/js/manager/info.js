function init(projectToOpen) {
    formProjectsComboBox(projectToOpen);
    if (projectToOpen === 0) {
        document.getElementById('currentProjectPanel').style.display = 'none';
        document.getElementById('nonePanel').style.display = 'block';
    }
    else {
        formCurrentProjectPanel(projectToOpen);
    }

}

function formCurrentProjectPanel(projectToOpen) {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/' + projectToOpen,
        dataType: 'json',
        async: true,
        success: function (result) {
            $('#projectId').append(result.id);
            $('#projectName').append(result.name);
            $('#projectComplete').append(result.complete.toString());
            $('#projectStart').append(new Date(result.start).toLocaleString());
            $('#projectEstEnd').append(new Date(result.estimatedEnd).toLocaleString());
            document.getElementById('nonePanel').style.display = 'none';
            document.getElementById('currentProjectPanel').style.display = 'block';
            if(result.complete) {
                document.getElementById('reopenButton').style.display = 'block';
            }
            else {
                document.getElementById('completeButton').style.display = 'block';
                document.getElementById('openDevsButton').style.display = 'block';
            }
            formChatPanel(projectToOpen);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });

}

function completeProject(projectId) {
    $.ajax({
        type: 'PUT',
        url: prefix + '/project/' + projectId + '/complete/',
        async: true,
        success: function () {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });
}

function reopenProject(projectId) {
    $.ajax({
        type: 'PUT',
        url: prefix + '/project/' + projectId + '/active/',
        async: true,
        success: function () {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });
}

function onSelectorChanged() {
    window.location.replace('/manager/info/' + $('#projectSelector').val());
}

function formProjectsComboBox(defaultId) {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/all_my/',
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