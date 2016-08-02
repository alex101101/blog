<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Alexander Mallal</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li class="active"><a href="/blog">Home <span class="sr-only">(current)</span></a></li>
            <li><a href="/blog/post">New Post</a></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Contact</a></li>
            <li><a href="#">Resume</a></li>
            <li><a href="#">Music</a></li>
          </ul>

          <form class="navbar-form pull-md-right pull-lg-right pull-sm-left" role="search">
              <div class="form-group">
                <input type="text" class="form-control" placeholder="Search">
              </div>
            <button type="submit" class="btn btn-default">Submit</button>
          </form>

                  <c:choose>
					<c:when test = "${!empty loggedInUser}">
						<p id="loggedInUser" class="navbar-text pull-md-right pull-lg-right pull-sm-left">Hello ${loggedInUser}!</p>
					</c:when>
				</c:choose>

          <ul class="nav navbar-nav pull-md-right pull-lg-right pull-sm-left">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Options <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <c:choose>
					<c:when test = "${empty loggedInUser}">
		                <li><a href="login">Login</a></li>
		                <li><a href="register">Register</a></li>
		                <li class="disabled"><a href="accountdetails">Account Details</a></li>
					</c:when>
					<c:otherwise>
		                <li class="disabled"><a href="login">Login</a></li>
		                <li class="disabled"><a href="register">Register</a></li>
		                <li><a href="user">Account Details</a></li>
		                <div class="contatainer" id="Logout">
		                <c:url var="logoutUrl" value="/logout"/>
		                <form class="navbar-form pull-md-right pull-lg-right pull-sm-left" method="POST" action="${logoutUrl}" role="logout">
				           <button type="submit" class="btn btn-link">Log out</button>
				           <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				         </form>
				        </div>
					</c:otherwise>
				</c:choose>
              </ul>
            </li>
          </ul>



        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>
