var prefix = '/developer-management-service-1.0-SNAPSHOT';
var currentProjectId = 0;

function formChatPanel() {
    $.ajax({
        type: 'GET',
        url: prefix + '/project/',
        dataType: 'json',
        async: true,
        success: function (result) {
            if (result.id === 0) {
                document.getElementById('chatPanel').style.display = 'none';
                document.getElementById('nonePanel').style.display = 'block';
            }
            else {
                currentProjectId = result.id;
                showCommentaries();
                document.getElementById('nonePanel').style.display = 'none';
                document.getElementById('chatPanel').style.display = 'block';
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}

function showCommentaries() {
    if (currentProjectId === 0) {
        alert('invalid current project');
    }
    else {
        $.ajax({
            type: 'GET',
            url: prefix + '/project/' + currentProjectId + '/comment/all/',
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
    if (currentProjectId === 0) {
        alert('invalid current project');
    }
    else {
        var comment = {
            'text': $("#newText").val()
        };
        $.ajax({
            type: 'POST',
            url: prefix + '/project/' + currentProjectId + '/comment/',
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
}

function formatTime(time) {
    return time.toLocaleDateString() + "<br>" + time.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
}