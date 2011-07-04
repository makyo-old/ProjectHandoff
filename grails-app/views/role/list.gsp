<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Roles</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <g:each var="role" in="${roles}">
      <div class="listRole">
        <h3>${role.name}</h3>
        <p>${role.description}</p>
        <p class="light">${role.actors.count()} actor${role.actors.count() == 1 ? '' : 's'}</p>
      </div>
    </g:each>
  </body>
</html>
