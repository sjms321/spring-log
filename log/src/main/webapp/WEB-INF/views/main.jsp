<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Main</title>
</head>
<body>
<h1>
	Hello! :D  
</h1>
<form action="/log/" method="post">
		ID : <input type="text" name="m_id" ><br>
		PW : <input type="password" name="m_pw" ><br>
		<input type="submit" value="Login" >
	</form>
<P>  <a href= "/log/join" >join</a></P>
</body>
</html>
