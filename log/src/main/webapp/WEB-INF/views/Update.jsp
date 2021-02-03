<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Update</title>
</head>
<body>
	<form action="/log/Update.do"method="post">
		<p><h1>Modify your information. </h1></p>
		ID :<input type="text" value="${member.m_id}" style="border:none"  name="m_id"readonly > <br>
		PW : <input type="password" name="m_pw" ><br>
		NAME : <input type="text" name="m_name" ><br>
		ADD : <input type="text" name="m_add" ><br>
		<input type="submit" value="Update" >
	</form>
<a href = "/log/">Cancel</a>
</body>
</html>