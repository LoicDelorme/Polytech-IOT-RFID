<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<tags:default-page>
    <jsp:attribute name="title">Polytech Lyon - Features</jsp:attribute>
    
    <jsp:attribute name="page_title">Features</jsp:attribute>
    
    <jsp:attribute name="header_content"></jsp:attribute>
    
    <jsp:attribute name="body_content">
		<core:if test="${message != null}">
   			<div class="alert alert-success alert-dismissible" role="alert">
	  			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	  			<strong>${message}</strong>
			</div>
		</core:if>
    	
		<dl>
			<dt>Users</dt>
			<dd><a href="/rfid/UserController/list">List all users</a></dd>
            	<dd><a href="/rfid/UserController/filter-form">Filter users</a></dd>
            	<dd><a href="/rfid/UserController/add-form">Add a new user</a></dd>
		</dl>
		
		<dl>
			<dt>Doors</dt>
			<dd><a href="/rfid/DoorController/list">List all doors</a></dd>
           	<dd><a href="/rfid/DoorController/filter-form">Filter doors</a></dd>
           	<dd><a href="/rfid/DoorController/add-form">Add a new door</a></dd>
		</dl>
    </jsp:attribute>
    
    <jsp:attribute name="additional_content"></jsp:attribute>
    
    <jsp:attribute name="footer_content"></jsp:attribute>
</tags:default-page>