<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="system_title"/></title>
	
	<spring:url value="/resources/images/favicon.ico" var="favicon_url"/>
	<spring:url value="/resources/j_spring_security_check" var="form_url"/>
	
	<link rel="shortcut icon" href="${favicon_url}"/>
	<link rel="bookmark" href="${favicon_url}"/>
	
	<script language=JavaScript>
	setTimeout("document.formlogin.submit()", 50);
	</script>
</head>
<body>
	等待系统加载    ...
	<form name="formlogin" action="${form_url}" method="POST">
		<input type="hidden" name="j_username" value="${username}"/>
		<input type="hidden" name="j_password" value="${password}"/>
	</form>
</body>
</html>
