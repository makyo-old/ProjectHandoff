<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Projects</title>
    <g:set scope="request" var="subnav" value="project" />
    <meta name="layout" content="main" />
  </head>
  <body>
    <g:each in="${projects}" status="i" var="project">
      <div class="listProject ${i % 2 == 0 ? 'odd' : 'even'}">
        <h3>
          <g:if test="${project.completed}"><s></g:if>
          ${project.name}
          <g:if test="${project.completed}"></s></g:if>
        </h3>
        <p>${project.description}</p>
        <p class="light">Actors: ${project.actors.size()} | Visibility: ${project.visibility} | Join method: ${project.joinMethod}</p>
      </div>
    </g:each>
  </body>
</html>
