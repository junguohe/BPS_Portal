<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="system_title"  /></title>


<spring:url value="/" var="root_url" />
<spring:url value="/resources/manual_images" var="images_url" />

<spring:url value="/resources/styles/common.css" var="common_css_url" />
<link rel="stylesheet" type="text/css" href="${common_css_url}" />

<spring:url value="/resources/images/favicon.ico" var="favicon" />
<link rel="shortcut icon" href="${favicon}"/>
<link rel="bookmark" href="${favicon}"/>

<spring:url value="/resources/extjs5.1/packages/ext-theme-neptune/build/resources/ext-theme-neptune-all.css" var="ext_css_url" />
<link rel="stylesheet" type="text/css" href="${ext_css_url}" />

<spring:url value="/resources/styles/style.css" var="banner_css_url" />
<link rel="stylesheet" href="${banner_css_url}" type="text/css">

<spring:url value="/resources/extjs5.1/build/ext-all-debug.js" var="ext_js_url" />
<script src="${ext_js_url}" type="text/javascript"></script>

<spring:url value="/resources/js/banner.js" var="banner_js_url" />
<script src="${banner_js_url}" type="text/javascript"></script>


<spring:url value="/resources/extjs5.1/packages/ext-locale/build/ext-locale-zh_CN.js" var="ext_local_url" />
<script src="${ext_local_url}" type="text/javascript"></script> 

<spring:url value="/api-debug.js" var="api_js" />
<script src="${api_js}"  type="text/javascript"></script>

<script>
var currentUserId = "${pageContext['request'].userPrincipal.principal.id}";
var currentUserName = "${pageContext['request'].userPrincipal.principal.username}";
var rootUrl = "${root_url}";

Ext.Ajax.on('requestexception', function(conn, response, options) {
	if (response.status == 9999) {
		//Ext.MessageBox.alert('提示信息', '登录验证过期，请重新登录。', function(but) {
			window.location.href = rootUrl;
		//});
	}
});

Ext.direct.Manager.on('exception', function(e) {
	//statusCode = e.statusCode;
	//Ext.Msg.alert(e.statusTitle, e.message);
});


function imagePath(imageName){
	return "${images_url}/"+imageName;
}

function rootPath(varPath){
	return "${root_url}"+varPath;
}


Ext.Loader.setConfig({
	enabled: true
});




</script>


</head>

<body>

 <c:set var="userName" value="${pageContext['request'].userPrincipal.principal.username}"/>
<!-- 
<table width="100%" >
	<tr align="right" >
		<td align="right" >
			welcome:<sec:authentication  property="name"></sec:authentication>
		</td>
	</tr>
</table>
 -->
<div>
<tiles:insertAttribute name="body"/>
</div>
</table>
</body>
</html>