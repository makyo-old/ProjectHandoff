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
    $.post(cbURL + 'role/ajax_editRole', {
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
    $.post(cbURL + 'role/ajax_deleteRole', {
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
        + roleFieldId + '-repeatability" value="' + $('#field-' + roleFieldId + '-rep-text').text() 
        + '" />');
}

function saveRoleField(roleFieldId) {
    // TODO
}

function deleteRoleField(roleFieldId) {
    // TODO
}

function cancelEditRoleField(roleFieldId) {
    // TODO
}