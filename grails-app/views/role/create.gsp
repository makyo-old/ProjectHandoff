<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Role</title>
    <g:javascript library="role" />
    <g:set scope="request" var="subnav" value="role" />
    <meta name="layout" content="main" />
  </head>
  <body>
    <g:form action="create" method="post">
      <table class="formTable">
        <tbody>
          <tr>
            <th class="ui-state-active ui-corner-left"><label for="rolename">Role name</label>:</th>
            <td><g:textField name="name" id="rolename" /></td>
          </tr>
          <tr>
            <th class="ui-state-active ui-corner-left"><label for="roledescription">Role description</label>:</th>
            <td><g:textArea name="description" id="roledescription" /></td>
          </tr>
          <tr>
            <th class="ui-state-active ui-corner-left"><label for="hasMask">Permissions mask</label>:</th>
            <td><g:checkBox name="hasMask" onchange="$(this).prop('checked') ? $('#mask').show() : $('#mask').hide()" /> Role has a permissions mask
              <div id="mask" style="display: none">
                <g:checkBox name="maskRead" value="on" /> Actors can read files<br />
                <g:checkBox name="maskWrite" value="on" /> Actors can update files<br />
                <g:checkBox name="maskInteract" value="on" /> Actors can interact with files<br />
                <g:checkBox name="maskAdministrate" value="on" /> Actors can administrate files<br />
              </div>
            </td>
          <tr>
            <th class="ui-state-active ui-corner-left">Role Fields:<br /><span class="light">A "notes" field is included automatically</span></th>
            <td>
              <div id="fields"><em>No fields</em></div><br />
              <input type="hidden" name="numFields" id="numFields" value="0" />
              <a href="javascript:void(0)" onclick="addField()" class="ui-state-default ui-corner-all linkButton">Add role field</a>
            </td>
          </tr>
        </tbody>
      </table>
      <g:submitButton name="submit" value="Create role" class="ui-state-default ui-corner-all linkButton" />
    </g:form>
  </body>
</html>
<%-- ${true} --%>
