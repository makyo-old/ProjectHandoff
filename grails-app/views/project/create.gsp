<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Project</title>
    <meta name="layout" content="main" />
    <g:set scope="request" var="subnav" value="project" />
  </head>
  <body>
    <g:form action="save" method="post">
      <table class="formTable">
        <tbody>
          <tr>
            <th class="ui-state-active ui-corner-left"><label for="name">Project name</label></th>
            <td><g:textField name="name" /></td>
          </tr>
          <tr>
            <th class="ui-state-active ui-corner-left"><label for="description">Project description</label></th>
            <td><g:textArea name="description" /></td>
          </tr>
          <tr>
            <th class="ui-state-active ui-corner-left"><label for="visibility">Visibility</label></th>
            <td><g:select name="visibility" from="${['all', 'loggedIn', 'desiredRoles', 'actors']}" /></td>
          </tr>
          <tr>
            <th class="ui-state-active ui-corner-left"><label for="joinMethod">Join restrictions</label></th>
            <td><g:select name="joinMethod" from="${['joinAll', 'joinDesired', 'applyAll', 'applyDesired', 'invite', 'closed']}" /></td>
          </tr>
          <tr>
            <th class="ui-state-active ui-corner-left"><label for="desiredRoles">Desired roles</label></th>
            <td><g:select name="desiredRoles" from="${us.jnsq.handoff.Role.list()}" optionKey="id" optionValue="name" multiple="true" /></td>
          </tr>
        </tbody>
      </table>
      <g:submitButton name="submit" value="Create project" class="ui-state-default ui-corner-all linkButton" />
    </g:form>
  </body>
</html>
