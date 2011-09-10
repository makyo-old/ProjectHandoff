<p>
  <g:form controller="project" action="apply" method="post">
    <input type="hidden" name="id" value="${projectID}" />
    Using role:
    <g:select name="role.id"
              from="${roles}" 
              optionKey="id"
              optionValue="name" />
    <g:submitButton name="submit" value="Apply" class="ui-state-default ui-corner-all linkButton" />
  </g:form>
</p>