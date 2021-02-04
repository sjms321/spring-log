<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Main</title>
	 <style>
        label {
            display: block; /* 새라인에서 시작*/
        }
        
        label span {
            display: inline-block;
            text-align: right;
            padding: 10px;
        }
        
        input[type=text] {
            color: red;
        }
      
        
      
 
    </style>
</head>
<body>
<h1>
	Hello! :D  
</h1>
<form action="/log/" method="post">
		<label>
		<span>ID :</span>
		 <input type="text" name="m_id"  ><br>
		</label>
		
		<label>
		<span>PW :</span>
		 <input type="password" name="m_pw" ><br>
		 </label>
		 
	
		 <label>
		 
			 <span>
				<input type="submit" value="Login" >
				
			</span>
				
		</label>
		
 	
	</form>

 <a href= "/log/join" >	<button>
						join
					 </button></a>
</body>
</html>
