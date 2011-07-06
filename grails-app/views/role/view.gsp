<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Role</title>
    <g:set scope="request" var="subnav" value="role" />
    <g:javascript library="role" />
    <script type="text/javascript">
      $(function () { enableSortFields(); });
    </script>
    <meta name="layout" content="main" />
  </head>
  <body>
    <input type="hidden" id="id" value="${role.id}" />
    <h3>Name</h3>
    <p id="name">${role.name}</p>
    <h3>Description</h3>
    <p id="description">${role.description}</p>
    <p style="text-align: right;" id="editButtons">
      <a href="javascript:void(0)" onclick="editRole()" id="edit" class="ui-state-default ui-corner-all linkButton">Edit</a>
      <a href="javascript:void(0)" onclick="saveRole()" class="ui-state-default ui-corner-all linkButton" style="display: none">Save</a>
      <a href="javascript:void(0)" onclick="deleteRole()" class="ui-state-default ui-corner-all linkButton" style="display: none">Delete</a>
      <a href="javascript:void(0)" onclick="cancelEditRole()" class="ui-state-default ui-corner-all linkButton" style="display: none">Cancel</a>
    </p>
    <hr />
    <h3>Fields</h3>
    <div id="sortableFields">
      <g:each var="field" in="${role.fields}">
        <div id="field-${field.id}" class="row">
          <div class="ui-state-active ui-corner-left header">
            <span id="field-${field.id}-name">${field.name}</span>
            <p>
              <a href="javascript:void(0)" onclick="editRoleField(${field.id})" id="edit-${field.id}" class="ui-state-default ui-corner-all linkButton">Edit</a>
              <a href="javascript:void(0)" onclick="saveRoleField(${field.id})" class="ui-state-default ui-corner-all linkButton" style="display: none">Save</a>
              <a href="javascript:void(0)" onclick="deleteRoleField(${field.id})" class="ui-state-default ui-corner-all linkButton" style="display: none">Delete</a>
              <a href="javascript:void(0)" onclick="cancelEditRoleField(${field.id})" class="ui-state-default ui-corner-all linkButton" style="display: none">Cancel</a>
            </p>
          </div>
          <div class="definition">
            <p id="field-${field.id}-description">${field.description}</p>
            <input type="hidden" id="field-${field.id}-rep-text" value="${field.repeatability}" />
            <p class="light" id="field-${field.id}-repeatability">
              <g:if test="${field.repeatability =~ /^\d+$/}">Repeat ${field.repeatability} time${field.repeatability.toInteger() == 1 ? '' : 's'}</g:if>
              <g:else>${['+': 'One or more times', '?': 'Zero or one times', '*': 'Zero or more times'][field.repeatability]}</g:else>
            </p>
          </div>
        </div>
      </g:each>
      <div id="createField" class="row" style="display: none;">
        <g:form controller="role" action="createRoleField" method="post">
          <div class="ui-state-active ui-corner-left header">
            <input type="text" id="edit-field-new-name" name="name" />
            <p>
              <g:submitButton name="Submit" value="Save" class="ui-state-default ui-corner-all linkButton" />
              <a href="javascript:void(0)" onclick="cancelCreateRoleFieldInRole()" class="ui-state-default ui-corner-all linkButton">Cancel</a>
            </p>
            <input type="hidden" name="roleid" value="${role.id}" />
            <input type="hidden" name="weight" value="${role.fields.size() + 1}" />
          </div>
          <div class="definition">
            <p><textarea id="edit-field-new-description" name="description"></textarea></p>
            <p>
              <input type="text" id="edit-field-new-repeatability" value="1" name="repeatability" /><br />
              <a href="javascript:void(0)" onclick="$('#edit-field-new-repeatability').val('?')">Zero or one times</a>
              <a href="javascript:void(0)" onclick="$('#edit-field-new-repeatability').val('+')">One or more times</a>
              <a href="javascript:void(0)" onclick="$('#edit-field-new-repeatability').val('*')">Zero or more times</a>
            </p>
          </div>
        </g:form>
      </div>
      <div class="row" style="text-align: right">
        <a href="javascript:void(0)" onclick="$('#createField').show(); $(this).hide();" id="addFieldButton" class="ui-state-default ui-corner-all linkButton">Add field...</a>
      </div>
    </div>
  </body>
</html>
