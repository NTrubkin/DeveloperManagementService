var prefix = '/developer-management-service-1.0-SNAPSHOT';
document.addEventListener('DOMContentLoaded', initHeader(), false);

function initHeader() {
    $.ajax({
        type: 'GET',
        url: prefix + '/account/',
        dataType: 'json',
        async: true,
        success: function (result) {
            document.getElementById("user").innerHTML = result.nickname;
            switch(result.roleId) {
                case 1:
                    document.getElementById("adminButton").style.display = 'block';
                    return;
                case 2:
                    document.getElementById("managerButton").style.display = 'block';
                    return;
                case 3:
                    document.getElementById("devButton").style.display = 'block';
                    return;
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // если запрос неудачный (например 403 ошибка), никакие кнопки в шапке активировать не нужно
        }
    });
}