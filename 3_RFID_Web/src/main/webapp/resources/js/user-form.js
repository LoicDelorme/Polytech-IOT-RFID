function checkInputs() {
	var error = false;
	
    if (document.userForm.lastname.value == "") {
	    	alert("Please enter your last name");
	    	error = true;
    }
    
    if (document.userForm.firstname.value == "") {
	    	alert("Please enter your first name");
	    	error = true;
    }
    
    return !error;
}