<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${project.name}</title>
    <g:set scope="request" var="subnav" value="project" />
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'project.css')}" />
    <meta name="layout" content="main" />
  </head>
  <body>
    <div id="workspace">
      <div id="projectInfo">
        ${project.description}
        <hr />
        <h3>Desired Roles</h3>
        <ul>
          <g:each in="${project.desiredRoles}" status="i" var="role">
            <li><g:link controller="role" action="view" id="${role.id}" title="${role.description}" class="tipsy">${role.name}</g:link></li>
          </g:each>
        </ul>
      </div>
      <div id="projectFiles">filler</div>
      <div id="projectActors">filler</div>
      <br clear="all" />
    </div>
  </body>
</html>
