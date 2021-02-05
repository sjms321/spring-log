<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Main</title>
</head>
<body>
<h1>
	JOIN 
</h1>
	<form action="/log/join.do" method="post">
		ID : <input type="text" name="m_id" ><br>
		PW : <input type="password" name="m_pw" ><br>
		NAME : <input type="text" name="m_name" ><br>
		ADD : <input type="text" name="m_add" ><br>
		Who is your best friend?<br>
		<input type="text" name= "m_hint"><br>
		<input type="submit" value="Join" >
	</form>
	<a href = "/log/"><button>Cancel</button></a>
</body>
</html>
