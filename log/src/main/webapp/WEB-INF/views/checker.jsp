<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>check</title>
</head>
<body>
<script type="text/javascript">
	var pw = prompt('Please enter your password again.',"");
	
	if(pw == '${member.m_pw}'){
		document.location.href = 'http://localhost:8090/log/removeOK';
	} 
	else if (pw == null){
		document.location.href = 'http://localhost:8090/log/loginOk';
	}
	else{
		alert('please enter PW again.');
		document.location.href = 'http://localhost:8090/log/removeCHECK';
	}
</script>
</body>
</html>