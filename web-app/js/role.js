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