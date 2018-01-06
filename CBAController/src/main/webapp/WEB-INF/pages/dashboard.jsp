<html>
<body>
<h1 align="center">
DASHBOARD
</h1>
<h2>welcome ,${userName}</h2>

<form id="logout" action="logout" method="post" >
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
<a href="javascript:document.getElementById('logout').submit()">Logout</a>
</form>

<%@include file="bankOperations.jsp" %>
</body>
</html>