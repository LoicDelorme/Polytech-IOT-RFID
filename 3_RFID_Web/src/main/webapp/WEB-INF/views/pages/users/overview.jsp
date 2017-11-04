<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<tags:default-page>
    <jsp:attribute name="title">Polytech Lyon - User overview</jsp:attribute>
    
    <jsp:attribute name="page_title">User overview</jsp:attribute>
    
    <jsp:attribute name="header_content"></jsp:attribute>
    
    <jsp:attribute name="body_content">
    		<form>
			<div class="form-group">
				<label for="id">ID</label>
				<input type="number" class="form-control" id="id" name="id" value="${user.id}" readonly>
		    </div>
			<div class="form-group">
				<label for="lastname">Last name</label>
				<input type="text" class="form-control" id="lastname" name="lastname" value="${user.lastname}" readonly>
		    </div>
		    <div class="form-group">
				<label for="firstname">First name</label>
				<input type="text" class="form-control" id="firstname" name="firstname" value="${user.firstname}" readonly>
		    </div>
		    <div class="form-group">
				<label for="rfidTag">RFID tag</label>
				<input type="text" class="form-control" id="rfidTag" name="rfidTag" value="${user.rfidTag}" readonly>
		    </div>
		    <div class="form-group">
				<label for="isValid">Is valid?</label>
				<core:choose>
					<core:when test="${user.isValid}">
						<input type="checkbox" class="form-control" id="isValid" name="isValid" disabled="disabled" checked="checked" readonly>
					</core:when>
					<core:otherwise>
						<input type="checkbox" class="form-control" id="isValid" name="isValid" disabled="disabled">
					</core:otherwise>
				</core:choose>
		    </div>
		</form>
    </jsp:attribute>
    
    <jsp:attribute name="additional_content">
    		<div class="row">
            <div class="col-lg-12">
            		<h1 class="page-header">Logs</h1>
            </div>
	    </div>
    		
    		<div class="row">
            <div class="col-lg-12">
            		<table class="table table-striped">
					<thead>
						<tr>
							<th>Door</th>
							<th>Date time</th>
						</tr>
					</thead>
					<tbody>
						<core:forEach items="${user.logs}" var="log">
							<tr>
								<td>${log.door.label}</td>
								<td><fmt:formatDate value="${log.dateTime}" pattern="dd-MM-yyyy HH:mm:ss"/></td>
							</tr>
						</core:forEach>
					</tbody>
				</table>
            </div>
	    </div>
    </jsp:attribute>
    
    <jsp:attribute name="footer_content"></jsp:attribute>
</tags:default-page>