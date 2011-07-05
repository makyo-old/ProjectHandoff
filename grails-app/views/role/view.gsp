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
    <h3>Fields</h3>
    <table class="formTable">
      <tbody>
        <g:each var="field" in="${role.fields}">
          <tr id="field-${field.id}">
            <th class="ui-state-active ui-corner-left">
              <span id="field-${field.id}-name">${field.name}</span><br />
              <a href="javascript:void(0)" onclick="editRoleField(${field.id})" id="edit" class="ui-state-default ui-corner-all linkButton">Edit</a>
              <a href="javascript:void(0)" onclick="saveRoleField(${field.id})" class="ui-state-default ui-corner-all linkButton" style="display: none">Save</a>
              <a href="javascript:void(0)" onclick="deleteRoleField(${field.id})" class="ui-state-default ui-corner-all linkButton" style="display: none">Delete</a>
              <a href="javascript:void(0)" onclick="cancelEditRoleField(${field.id})" class="ui-state-default ui-corner-all linkButton" style="display: none">Cancel</a>
            </th>
            <td>
              <p id="field-${field.id}-description">${field.description}</p>
              <input type="hidden" id="field-${field.id}-rep-text" value="${field.repeatability}" />
              <p class="light" id="field-${field.id}-repeatability">
                <g:if test="${field.repeatability =~ /^\d+$/}">Repeat ${field.repeatability} time${field.repeatability.toInteger == 1 ? '' : 's'}</g:if>
                <g:else>${['+': 'One or more times', '?': 'Zero or one times', '*': 'Zero or more times'][field.repeatability]}</g:else>
              </p>
            </td>
          </tr>
        </g:each>
      </tbody>
    </table>
  </body>
</html>
