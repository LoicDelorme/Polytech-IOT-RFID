<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<tags:default-page>
    <jsp:attribute name="title">Polytech Lyon - Filter users</jsp:attribute>
    
    <jsp:attribute name="page_title">Filter users</jsp:attribute>
    
    <jsp:attribute name="header_content"></jsp:attribute>
    
    <jsp:attribute name="body_content">
    		<form name="userForm" method="post" action="/rfid/UserController/filter">
			<div class="form-group">
				<label for="lastname">Last name</label>
				<input type="text" class="form-control" id="lastname" name="lastname" placeholder="Enter your last name" maxlength="100">
		    </div>
		    <div class="form-group">
				<label for="firstname">First name</label>
				<input type="text" class="form-control" id="firstname" name="firstname" placeholder="Enter your first name" maxlength="100">
		    </div>
		    <div class="form-group">
				<label for="rfidTag">RFID tag</label>
				<input type="text" class="form-control" id="rfidTag" name="rfidTag" placeholder="Enter your RFID tag" maxlength="100">
		    </div>
		    <button type="submit" class="btn btn-primary">Search</button>
		</form>
    </jsp:attribute>
    
    <jsp:attribute name="additional_content"></jsp:attribute>
    
    <jsp:attribute name="footer_content"></jsp:attribute>
</tags:default-page>