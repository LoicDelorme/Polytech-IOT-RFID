<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<tags:default-page>
    <jsp:attribute name="title">Polytech Lyon - Filter doors</jsp:attribute>
    
    <jsp:attribute name="page_title">Filter doors</jsp:attribute>
    
    <jsp:attribute name="header_content"></jsp:attribute>
    
    <jsp:attribute name="body_content">
    		<form name="doorForm" method="post" action="/rfid/DoorController/filter">
			<div class="form-group">
				<label for="label">Label</label>
				<input type="text" class="form-control" id="label" name="label" placeholder="Enter your label" maxlength="100">
		    </div>
		    <button type="submit" class="btn btn-primary">Search</button>
		</form>
    </jsp:attribute>
    
    <jsp:attribute name="additional_content"></jsp:attribute>
    
    <jsp:attribute name="footer_content"></jsp:attribute>
</tags:default-page>