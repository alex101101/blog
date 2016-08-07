<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <meta name="_csrf" content="${_csrf.token}"/>
 	 <meta name="_csrf_header" content="${_csrf.headerName}"/>
 	 
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
	
    <!-- Bootstrap -->
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapcss" htmlEscape="true"></spring:url>
	<spring:url value="/resources/css/styles.css" var="stylescss" htmlEscape="true"></spring:url>
    <link rel="stylesheet" href="${bootstrapcss}"></link>
    <link rel="stylesheet" href="${stylescss}"></link>
   
    
    	    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	    <spring:url value="/resources/js/jquery-2.2.3.js" var="jquery" htmlEscape="true"></spring:url>
	    <spring:url value="/resources/js/bootstrap.min.js" var="bootstrapjs" htmlEscape="true"></spring:url>
	    <spring:url value="/resources/js/mustache.js" var="mustachejs" htmlEscape="true"></spring:url>
	    <spring:url value="/resources/js/jquery-ui.js" var="jqueryui" htmlEscape="true"></spring:url>
	    <spring:url value="/resources/js/search.js" var="search" htmlEscape="true"></spring:url>
    <script src="${jquery}" ></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${bootstrapjs}"></script>
    <script src="${mustachejs}"></script>
    <script src="${jqueryui}"></script>
    <script src="${search}"></script>
    
        <tiles:insertAttribute name="header" />

</head>
<body>
    
	<div>
		<tiles:insertAttribute name="menu" />
	</div>
	<div>
		<tiles:insertAttribute name="body" />
	</div>
	<div>
		<tiles:insertAttribute name="footer" />
	</div>
	

</html>