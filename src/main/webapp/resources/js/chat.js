var prefix = '/developer-management-service-1.0-SNAPSHOT';
var projectId = 0;

function formChatPanel(projId) {
    console.log(projId);
    projectId = projId;
    document.getElementById('chatPanel').style.display = 'block';
    showCommentaries();
}

function showCommentaries() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/' + projectId + '/comment/all/',
        dataType: 'json',
        async: false,
        success: function (result) {
            formCommentsFromJson(result);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function formCommentsFromJson(json) {
    var comments = $('#comments');
    for (var i = 0; i < json.length; i++) {
        var div = "<div class='comment'>" +
            "<div class='sender'>" + json[i].accountNickname + "</div>" +
            "<div class='text'>" + json[i].text + "</div>" +
            "<div class='time'>" + formatTime(new Date(json[i].time)) + "</div>" +
            "</div>";
        comments.append(div);
    }
}

function sendCommentary() {
    var comment = {
        'text': $("#newText").val()
    };
    $.ajax({
        type: 'POST',
        url: prefix + '/project/' + projectId + '/comment/',
        contentType: 'application/json; charset=utf-8',
        async: true,
        data: JSON.stringify(comment),
        success: function (result) {
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function formatTime(time) {
    return time.toLocaleDateString() + "<br>" + time.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});
}