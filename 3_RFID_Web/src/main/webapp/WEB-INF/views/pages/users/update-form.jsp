<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<tags:default-page>
    <jsp:attribute name="title">Polytech Lyon - Update a user</jsp:attribute>
    
    <jsp:attribute name="page_title">Update a user</jsp:attribute>
    
    <jsp:attribute name="header_content"></jsp:attribute>
    
    <jsp:attribute name="body_content">
	    <core:url value="/resources/js/user-form.js" var="js_user_form" />
	    <script src="${js_user_form}"></script>
    
    		<form name="userForm" method="post" action="/rfid/UserController/update" onsubmit="return checkInputs()">
    			<div class="form-group">
				<label for="id">ID</label>
				<input type="number" class="form-control" id="id" name="id" value="${user.id}" readonly>
		    </div>
			<div class="form-group">
				<label for="lastname">Last name</label>
				<input type="text" class="form-control" id="lastname" name="lastname" placeholder="Enter your last name" value="${user.lastname}" maxlength="100">
		    </div>
		    <div class="form-group">
				<label for="firstname">First name</label>
				<input type="text" class="form-control" id="firstname" name="firstname" placeholder="Enter your first name" value="${user.firstname}" maxlength="100">
		    </div>
		    <div class="form-group">
				<label for="rfidTag">RFID tag</label>
				<input type="text" class="form-control" id="rfidTag" name="rfidTag" placeholder="Enter your RFID tag" value="${user.rfidTag}" maxlength="100">
		    </div>
		    <div class="form-group">
				<label for="isValid">Is valid?</label>
				<core:choose>
					<core:when test="${user.isValid}">
						<input type="checkbox" class="form-control" id="isValid" name="isValid" checked="checked">
					</core:when>
					<core:otherwise>
						<input type="checkbox" class="form-control" id="isValid" name="isValid">
					</core:otherwise>
				</core:choose>
		    </div>
		    <button type="submit" class="btn btn-primary">Submit</button>
		</form>
    </jsp:attribute>
    
    <jsp:attribute name="additional_content"></jsp:attribute>
    
    <jsp:attribute name="footer_content"></jsp:attribute>
</tags:default-page>