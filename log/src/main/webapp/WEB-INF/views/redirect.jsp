<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>redirect</title>
</head>
<body>

<script type="text/javascript">
	alert('${msg}');
	document.location.href = '${url}';
</script>
</body>


</html>