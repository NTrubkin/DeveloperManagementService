var createProject = function () {
    // id, managerId, start, end и другие параметры будут определены на сервере автоматически
    var timeStr = $("#estEndTime").val();
    var dateStr = $("#estEndDate").val();

    var date = new Date(createDate(dateStr, timeStr));

    if (isNaN(date.getTime())) {
        alert('Wrong estimated end');
        return;
    }
    var project = {
        'name': $("#name").val(),
        'complete': false,
        'estimatedEnd': date.getTime()
    };
    $.ajax({
        type: 'POST',
        url: prefix + "/project/",
        contentType: 'application/json; charset=utf-8',
        async: true,
        data: JSON.stringify(project),
        success: function (project) {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
        }
    });
};

function createDate(dateStr, timeStr) {
    return dateStr + 'T' + timeStr;
}