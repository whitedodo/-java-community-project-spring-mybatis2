function passwdChecker(p1, p2) {
	var passwd1 = document.getElementById(p1).value;
	var passwd2 = document.getElementById(p2).value;
	
	if( passwd1 != passwd2 ) {
		// alert("비밀번호가 일치 하지 않습니다");
		return false;
		
	} else{
		// alert("비밀번호가 일치합니다");
		return true;
	}

}

function passwdLengthChecker(p1){
	
	var passwd = document.getElementById(p1).value;
	
	if ( passwd.length > 6 ){
		// alert("비밀번호 길이가 짧습니다.");
		return true;
	}
	
	return false;
	
}