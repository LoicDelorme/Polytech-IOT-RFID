<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<tags:default-page>
    <jsp:attribute name="title">Polytech Lyon - List of all registered doors</jsp:attribute>
    
    <jsp:attribute name="page_title">Registered doors</jsp:attribute>
    
    <jsp:attribute name="header_content"></jsp:attribute>
    
    <jsp:attribute name="body_content">
    		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>Label</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<core:forEach items="${doors}" var="door">
					<tr>
						<td><a href="/rfid/DoorController/overview?id=${door.id}">${door.id}</a></td>
						<td>${door.label}</td>
						<td>
							<a class="btn btn-warning" href="/rfid/DoorController/update-form?id=${door.id}" role="button"><i class="glyphicon glyphicon-pencil"></i></a>
							<a class="btn btn-danger" href="/rfid/DoorController/delete?id=${door.id}" role="button"><i class="glyphicon glyphicon-remove"></i></a>
						</td>
					</tr>
				</core:forEach>
			</tbody>
		</table>
    </jsp:attribute>
    
    <jsp:attribute name="additional_content"></jsp:attribute>
    
    <jsp:attribute name="footer_content"></jsp:attribute>
</tags:default-page>