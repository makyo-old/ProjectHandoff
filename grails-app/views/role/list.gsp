<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Roles</title>
    <g:set scope="request" var="subnav" value="role" />
    <meta name="layout" content="main" />
  </head>
  <body>
    <g:each var="role" status="i" in="${roles}">
      <div class="listRole ${i % 2 == 0 ? 'even' : 'odd'}">
        <h3><g:link controller="role" action="view" id="${role.id}">${role.name}</g:link></h3>
        <p>${role.description}</p>
        <p class="light">${role.actors.count()} actor${role.actors.count() == 1 ? '' : 's'}</p>
      </div>
    </g:each>
  </body>
</html>
