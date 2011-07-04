function authAjax() {
    $('#auth a').addClass('ui-widget-disabled');
    
    $.post($('#loginForm').attr('action'), {
        j_username: $('#username').val(),
        j_password: $('#password').val(),
        _spring_security_remember_me: $('#remember_me').val()
    }, function(data, status, jqXHR) {
        if (data.success) {
            $('#auth').dialog('close');
            $('#userControl').html(
                '<h3>Welcome, ' + data.username + '</h3>\n'
                + '<a href="' + cbURL + 'logout">Logout</a>'
                );
        } else if (data.error) {
            $('#auth a').removeClass('ui-widget-disabled');
            $('#loginError .errorText').text(data.error);
            $('#loginError').show();
        }
    });
}

function showLogin() {
    $('#auth').dialog({
        modal: true,
        title: 'Log in',
        position: ['right', 'top'],
        close: function() {
            $('#ucInner').dialog('destroy');
        }
    });
}