<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<tags:default-page>
    <jsp:attribute name="title">Polytech Lyon - Update a door</jsp:attribute>
    
    <jsp:attribute name="page_title">Update a door</jsp:attribute>
    
    <jsp:attribute name="header_content"></jsp:attribute>
    
    <jsp:attribute name="body_content">
	    <core:url value="/resources/js/door-form.js" var="js_door_form" />
	    <script src="${js_door_form}"></script>
    
    		<form name="doorForm" method="post" action="/rfid/DoorController/update" onsubmit="return checkInputs()">
    			<div class="form-group">
				<label for="id">ID</label>
				<input type="number" class="form-control" id="id" name="id" value="${door.id}" readonly>
		    </div>
			<div class="form-group">
				<label for="label">Label</label>
				<input type="text" class="form-control" id="label" name="label" placeholder="Enter your label" value="${door.label}" maxlength="100">
		    </div>
		    <button type="submit" class="btn btn-primary">Submit</button>
		</form>
    </jsp:attribute>
    
    <jsp:attribute name="additional_content"></jsp:attribute>
    
    <jsp:attribute name="footer_content"></jsp:attribute>
</tags:default-page>