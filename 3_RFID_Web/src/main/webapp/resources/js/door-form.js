function checkInputs() {
	var error = false;
	
    if (document.doorForm.label.value == "") {
	    	alert("Please enter your label");
	    	error = true;
    }
    
    return !error;
}