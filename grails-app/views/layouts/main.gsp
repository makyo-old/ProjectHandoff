<!DOCTYPE html>
<html>
  <head>
    <title><g:layoutTitle default="Home" /> | Project Handoff</title>
    <script type="text/javascript" src="http://lib.jnsq.us/jquery/1.6.2"></script>
    <script type="text/javascript" src="http://lib.jnsq.us/jquery-ui/1.8.14"></script>
    <sec:ifNotLoggedIn><g:javascript library="auth" /></sec:ifNotLoggedIn>
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Amaranth:700|Lato:300&subset=latin&v2" />
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'jquery-ui-1.8.14.green/css/custom-theme', file: 'jquery-ui-1.8.14.custom.css')}" />
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'main.css')}" />
    <script type="text/javascript">
      var cbURL = '${createLink(uri: '/')}';
    </script>
    <g:layoutHead />
  </head>
  <body>
    <div id="spinner" class="spinner" style="display:none;">
      <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
    </div>
    <div id="header" class="ui-widget-content">
      <h1>Project Handoff</h1>
      <g:link controller="project" class="ui-state-default ui-corner-all linkButton">Projects</g:link>
      <g:link controller="role" class="ui-state-default ui-corner-all linkButton">Roles</g:link>
      <g:link controller="account" class="ui-state-default ui-corner-all linkButton">Users</g:link>
      <div id="userControl">
        <sec:ifLoggedIn>
          <h3>Welcome, <sec:username />!</h3>
          <g:link controller="logout">Log out</g:link>
        </sec:ifLoggedIn>
        <sec:ifNotLoggedIn>
          <h3>Welcome!</h3>
          <a href="javascript:void(0)" onclick="showLogin();" class="ui-state-default ui-corner-all linkButton">Log in</a>
          <g:link controller="register" class="ui-state-default ui-corner-all linkButton">Register</g:link>
          <div id="auth">
            <form action="${request.contextPath}/j_spring_security_check" id="loginForm" method="POST">
              <p>
                <label for="username">Username</label>
                <input type="text" name="j_username" id="username" />
              </p>
              <p>
                <label for="password">Password</label>
                <input type="password" name="j_password" id="password" />
              </p>
              <p>
                <label for="remember_me">Remember me</label>
                <input type="checkbox" id="remember_me"
                       name="_spring_security_remember_me"/>
              </p>
              <div id="loginError"class="ui-state-error ui-corner-all">
                <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                <span class="errorText"></span>
              </div>
              <p>
                <a href="javascript:void(0)" onclick="authAjax(); return false;" class="ui-state-default ui-corner-all linkButton">Login</a>
              </p>
            </form>
          </div>
        </sec:ifNotLoggedIn>
      </div>
    </div>
    <div id="subNavigation">
      <g:render template="/${subnav ?: 'defaultSubNav'}" />
    </div>
    <div id="contentWrapper">
      <div id="messages">
        <g:if test="${flash.message}">${flash.message}</g:if>
      </div>
      <h2>${pageProperty(name: 'title').replaceAll(' \\| Project Handoff', '')}</h2>
      <div id="content">
        <g:layoutBody />
      </div>
    </div>
    <div id="footer">
      <img src="http://lib.jnsq.us/jnsq-logo.png">
      a JNSQ.US project, &copy; 2011 under the <a href="http://lib.jnsq.us/license/gplv3">GPLv3 license</a>
    </div>
  </body>
</html>