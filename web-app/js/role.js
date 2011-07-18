function setFieldRepeatability(fieldNumber, value) {
    $('#rolefield-' + fieldNumber + '-repeatability').val(value);
}

function addField() {
    var numFields = parseInt($('#numFields').val()) + 1;
    if (numFields == 1) {
        $('#fields').html('');
    }
    $('#fields').append("\
                <table class=\"formTable\">\
                  <thead>\
                    <tr>\
                      <th colspan=\"2\" class=\"ui-state-active ui-corner-all\">Field " + numFields + "</th>\
                    </tr>\
                  </thead>\
                  <tbody>\
                    <tr>\
                      <th class=\"ui-state-active ui-corner-left\"><label for\"rolefield-" + numFields + "-name\">Field Name</label>:</th>\
                      <td><input type=\"text\" name=\"rolefield-" + numFields + "-name\" id=\"rolefield-" + numFields + "-name\"/></td>\
                    </tr>\
                    <tr>\
                      <th class=\"ui-state-active ui-corner-left\"><label for\"rolefield-" + numFields + "-description\">Description</label>:</th>\
                      <td><textarea name=\"rolefield-" + numFields + "-description\" id=\"rolefield-" + numFields + "-description\"></textarea></td>\
                    </tr>\
                    <tr>\
                      <th class=\"ui-state-active ui-corner-left\"><label for=\"rolefield-" + numFields + "-repeatability\">Repeatability</label>:</th>\
                      <td>\
                        <input type=\"text\" name=\"rolefield-" + numFields + "-repeatability\" id=\"rolefield-" + numFields + "-repeatability\" value=\"1\" /><br />\
                        <a href=\"javascript:void(0)\" onclick=\"setFieldRepeatability(" + numFields + ", '?')\">Zero or one times</a>\
                        <a href=\"javascript:void(0)\" onclick=\"setFieldRepeatability(" + numFields + ", '+')\">One or more times</a>\
                        <a href=\"javascript:void(0)\" onclick=\"setFieldRepeatability(" + numFields + ", '*')\">Zero or more times</a>\
                      </td>\
                    </tr>\
                  </tbody>\
                </table>");
    $('#numFields').val(numFields);
}

function editRole() {
    $('#editButtons .linkButton').toggle();
    $('#name').html('<input type="text" id="rolename" value="' + $('#name').text() + '" />');
    $('#description').html('<textarea id="roledescription">' + $('#description').text() + "</textarea>");
}

function saveRole() {
    $('#editButtons').append('<span class="removeLater">Saving role...</span>');
    $('#editButtons .linkButton').hide();
    $.post(cbURL + 'rolAjax/editRole', {
        id: $('#id').val(),
        name: $('#rolename').val(),
        description: $('#roledescription').val()
    }, function(data, status, jqXHR) {
        $('#editButtons .removeLater').remove();
        if (data.success) {
            $('#edit').show();
            $('#name').html($('#rolename').val());
            $('#description').html($('#roledescription').val());
        } else {
            console.log(data);
            alert("Error saving");
        }
    });
}

function deleteRole() {
    $('#editButtons').append('<span class="removeLater">Deleting role...</span>');
    $('#editButtons .linkButton').hide();
    $.post(cbURL + 'roleAjax/deleteRole', {
        id: $('#id').val()
    }, function(data, status, jqXHR) {
        if (data.success) {
            window.location = cbURL + 'role/list'
        } else {
            $('#editButtons .removeLater').remove();
            alert("Error deleting");
        }
    });
}

function cancelEditRole() {
    $('#editButtons .linkButton').toggle();
    $('#name').html($('#rolename').val());
    $('#description').html($('#roledescription').val());
}

function editRoleField(roleFieldId) {
    $('#field-' + roleFieldId + ' .linkButton').toggle();
    $('#field-' + roleFieldId + '-name').html('<input type="text" id="edit-field-' 
        + roleFieldId + '-name" value="' + $('#field-' + roleFieldId + '-name').text() 
        + '" />');
    $('#field-' + roleFieldId + '-description').html('<textarea id="edit-field-' 
        + roleFieldId + '-description">' + $('#field-' + roleFieldId + '-description').text() 
        + '</textarea>');
    $('#field-' + roleFieldId + '-repeatability').html('<input type="text" id="edit-field-' 
        + roleFieldId + '-repeatability" value="' + $('#field-' + roleFieldId + '-rep-text').val() 
        + '" />');
}

function saveRoleField(roleFieldId) {
    $('#field-' + roleFieldId).append('<span class="removeLater">Saving field...</span>');
    $('#field-' + roleFieldId + ' .linkButton').hide();
    $.post(cbURL + 'roleAjax/editRoleField', {
        id: roleFieldId,
        name: $('#edit-field-' + roleFieldId + '-name').val(),
        description: $('#edit-field-' + roleFieldId + '-description').val(),
        repeatability: $('#edit-field-' + roleFieldId + '-repeatability').val()
    }, function(data, status, jqXHR) {
        $('#field-' + roleFieldId + ' .removeLater').remove();
        if (data.success) {
            $('#edit-' + roleFieldId).show();
            $('#field-' + roleFieldId + '-name').html($('#edit-field-' + roleFieldId + '-name').val());
            $('#field-' + roleFieldId + '-description').html($('#edit-field-' + roleFieldId + '-description').val());
            $('#field-' + roleFieldId + '-repeatability').html(
                (/^[\d]+$/.test($('#edit-field-' + roleFieldId + '-repeatability').val()) 
                    ? 'Repeat ' + $('#edit-field-' + roleFieldId + '-repeatability').val() + ' time' + (parseInt($('#edit-field-' + roleFieldId + '-repeatability').val()) == 1 ? '' : 's')
                    : {'?': 'Zero or one times', '+': 'One or more times', '*': 'Zero or more times'}[$('#edit-field-' + roleFieldId + '-repeatability').val()])
            );
        } else {
            console.log(data);
            alert("Error saving");
        }
    });
    
}

function deleteRoleField(roleFieldId) {
    $('#field-' + roleFieldId).append('<span class="removeLater">Saving field...</span>');
    $('#field-' + roleFieldId + ' .linkButton').hide();
    $.post(cbURL + 'roleAjax/deleteRoleField', {
        id: roleFieldId
    }, function(data, status, jqXHR) {
        if (data.success) {
            $('#field-' + roleFieldId).children().slideUp();
            $('#field-' + roleFieldId).remove();
        } else {
            $('#field-' + roleFieldId + ' .removeLater').remove();
            alert("Error deleting");
        }
    });
}

function cancelEditRoleField(roleFieldId) {
    $('#field-' + roleFieldId + ' .linkButton').toggle();
    $('#field-' + roleFieldId + '-name').html($('#edit-field-' + roleFieldId + '-name').val());
    $('#field-' + roleFieldId + '-description').html($('#edit-field-' + roleFieldId + '-description').val());
    $('#field-' + roleFieldId + '-repeatability').html(
        (/^[\d]+$/.test($('#edit-field-' + roleFieldId + '-repeatability').val()) 
            ? 'Repeat ' + $('#edit-field-' + roleFieldId + '-repeatability').val() + ' time' + (parseInt($('#edit-field-' + roleFieldId + '-repeatability').val()) == 1 ? '' : 's')
            : {'?': 'Zero or one times', '+': 'One or more times', '*': 'Zero or more times'}[$('#edit-field-' + roleFieldId + '-repeatability').val()])
    );
}

function createRoleFieldInRole() {
    $('#createField').append('<span class="removeLater">Adding field...</span>');
    $('#createField .linkButton').hide();
    console.log("creating role");
    $.post(cbURL + 'roleAjax/createRoleField', {
        'role.id': $('#edit-field-new-roleid').val(),
        name: $('#edit-field-new-name').val(),
        description: $('#edit-field-new-description').val(),
        repeatability: $('#edit-field-new-repeatability').val(),
        weight: $('#edit-field-new-weight').val()
    }, function(data, status, jqXHR) {
        $('#createField .removeLater').remove();
        if (data.success) {
            $('#sortableFields').append('<div id="field-' + data.id + '" class="row">'
                + '<div class="ui-state-active ui-corner-left header">'
                + '<span id="field-' + data.id + '-name">' + $('#edit-field-new-name').val() + '</span>'
                + '<p>'
                + '<a href="javascript:void(0)" onclick="editRoleField(' + data.id + ')" id="edit-' + data.id + '" class="ui-state-default ui-corner-all linkButton">Edit</a>'
                + '<a href="javascript:void(0)" onclick="saveRoleField(' + data.id + ')" class="ui-state-default ui-corner-all linkButton" style="display: none">Save</a>'
                + '<a href="javascript:void(0)" onclick="deleteRoleField(' + data.id + ')" class="ui-state-default ui-corner-all linkButton" style="display: none">Delete</a>'
                + '<a href="javascript:void(0)" onclick="cancelEditRoleField(' + data.id + ')" class="ui-state-default ui-corner-all linkButton" style="display: none">Cancel</a>'
                + '</p>'
                + '</div>'
                + '<div class="definition">'
                + '<p id="field-' + data.id + '-description">' + $('#edit-field-new-description').val() + '</p>'
                + '<input type="hidden" id="field-' + data.id + '-rep-text" value="' + $('#edit-field-new-repeatability').val + '" />'
                + '<p class="light" id="field-' + data.id + '-repeatability">'
                + (isNaN(parseInt($('#edit-field-new-repeatability').val())) ? {'+': 'One or more times', '?': 'Zero or one times', '*': 'Zero or more times'}[$('#edit-field-new-repeatability').val()] : 'Repeat ' + $('#edit-field-new-repeatability').val() + ' time' + (parseInt($('#edit-field-new-repeatability').val()) == 1 ? '' : 's'))
                + '</p>'
                + '</div>'
            );
            $('#edit-field-new-weight').val(parseInt($('#edit-field-new-weight').val()) + 1);
            cancelCreateRoleFieldInRole();
        } else {
            alert("Error creating field");
        }
    });
}

function cancelCreateRoleFieldInRole() {
    $('#createField').hide();
    $('#edit-field-new-name').val('');
    $('#edit-field-new-description').val('');
    $('#edit-field-new-repeatability').val('1');
    $('#addFieldButton').show();
}

function enableSortFields() {
    $('#sortableFields').sortable({
        handle: '.header',
        axis: 'y',
        stop: function(event, ui) {
            $.get(cbURL + 'roleAjax/saveRoleFieldSort?role=' + $('#id').val() + '&' + $('#sortableFields').sortable('serialize'),
            function(data, status, jqXHR) {
                if (data.success) {
                    console.log("Sorted");
                } else {
                    console.log(data);
                    alert("Error sorting...");
                }
            });
        }
    });
}