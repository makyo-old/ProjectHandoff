<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Role: ${role.name}</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <h3>Description</h3>
    <p>${role.description}</p>
    <h3>Fields</h3>
    <table class="formTable">
      <tbody>
        <g:each var="field" in="${role.fields}">
          <tr>
            <th class="ui-state-active ui-corner-left">${field.name}</th>
            <td>${field.description}</td>
          </tr>
        </g:each>
      </tbody>
    </table>
  </body>
</html>
