<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>find PW</title>
</head>
<body>
<form action="/log/findPW.do">
	what is your ID?<br>
	 <input type="text" name= "m_id"><br>
	Who is your best friend?<br>
	<input type="text" name= "m_hint"><br>
	<input type="submit" value="findPW">
</form>
<a href = "/log/"><button>Cancel</button></a>
</body>
</html>