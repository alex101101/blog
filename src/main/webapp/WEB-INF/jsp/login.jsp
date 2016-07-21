<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


  <div class="container" id="SiteBodyLogin">
  <div class="container" id="SiteBodyLogin">
    	<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
    <div id=HLogin>
    <h5 class="text-muted">To write a post, Please log in:</h5>
    </div>
    <form name='loginForm' action="<c:url value='/login' />" method='POST'>
      <div class="form-group">
        <label class="sr-only" for="exampleInputEmail3">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Username">
      </div>
      <div class="form-group">
        <label class="sr-only" for="exampleInputPassword3">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password">
      </div>
      <div class="checkbox">
        <label>
          <input type="checkbox"> Remember me
        </label>
      </div>
      <button type="submit" name="submit" value="submit" class="btn btn-default" data-toggle="tooltip" data-placement="right" title="Secure Login with Spring Security...sort of">Log in</button>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>


  </div><!--SiteBodyLogin-->
      <hr>
  </div><!--SiteBody-->