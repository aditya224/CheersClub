<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html>
<head>
	<meta charset="UTF-8">
	<title>Login/Register</title>
	<style>
		/* Styles for the forms */
		form {
			display: block;
			margin: 10px;
			padding: 10px;
			border: 1px solid #ccc;
			border-radius: 5px;
			background-color: #f5f5f5;
			width: 500px;
			box-sizing: border-box;
		}
		form.show {
			display: block;
		}
		form label {
			display: block;
			margin-bottom: 5px;
			font-weight: bold;
		}
		form:input {
			display: block;
			margin-bottom: 10px;
			padding: 5px;
			border-radius: 5px;
			border: 1px solid #ccc;
			width: 100%;
			box-sizing: border-box;
		}
		form button[type="submit"] {
			background-color: #4CAF50;
			color: #fff;
			padding: 10px;
			border-radius: 5px;
			border: none;
			width: 100%;
			cursor: pointer;
		}
	</style>
	<!-- <script>
		/* JavaScript for toggling between login and register forms */
		function toggleForm(formName) {
			var loginForm = document.getElementById("login-form");
			var registerForm = document.getElementById("register-form");
			if (formName === "login") {
				loginForm.classList.add("show");
				registerForm.classList.remove("show");
			} else if (formName === "register") {
				loginForm.classList.remove("show");
				registerForm.classList.add("show");
			}
		}
		function checkpassword(){
			if(Document.getElementById("password")!=Document.getElementById("confirmpassword")){
				var t = Document.getElementById("passwordmatcherror");
				document.getElementById("passwordmatcherror").style.display = "block";
				return false;
			}
		}
		

	</script> -->
</head>
<body>
	<header>
		<h1>Welcome to the Website</h1>
	</header>
	<main>
		
		
		<form:form modelAttribute="user" id="register-form" class="register-form" action="register" method="post">
		<!-- <form id="register-form" action="register" method="post"> -->
			<label for="UserType">UserType:</label>
			<select id="UserType" name="UserType">
			<option value="USER">USER</option>
			<option value="MODERATOR">MODERATOR</option>
			<option value="ADMIN">ADMIN</option>
			</select>
			<label for="firstName">First Name:</label>
			<form:input path="firstName"/>
			<!-- <form:input path="firstName" size="30"/> <font color="red"><form:errors path="firstName"/></font></td> -->
			<font color="red"><form:errors path="firstName"/></font><br/>
			<label for="lastName">Last Name:</label>
			<form:input path="lastName" id="lastName" />
			<font color="red"><form:errors path="lastName"/></font><br/>
            <label for="streetName">Street Name:</label>
			<form:input path="address.streetName" id="streetName" />
			<font color="red"><form:errors path="address.streetName"/></font><br/>
            <label for="streetNum">Street Number:</label>
			<form:input path="address.streetNum" id="streetNum"/>
			<font color="red"><form:errors path="address.streetNum"/></font><br/>
            <label for="city">City:</label>
			<form:input path="address.city" id="city"/>
			<font color="red"><form:errors path="address.city"/></font><br/>
            <label for="state">State:</label>
			<form:input path="address.state" id="state" />
			<font color="red"><form:errors path="address.state"/></font><br/>
            <label for="zip">Zip:</label>
			<form:input path="address.zip" id="zip" />
			<font color="red"><form:errors path="address.zip"/></font><br/>
			<label for="country">Country:</label>
			<form:input path="address.country" id="country" />
			<font color="red"><form:errors path="address.country"/></font><br/>
			<label for="email">Email:</label>
			<form:input path="email" id="email" />
			<font color="red"><form:errors path="email"/></font><br/>
			<label for="password">Password:</label>
			<form:input path="password"  id="password" />
			<font color="red"><form:errors path="password"/></font><br/>
			
			<button type="submit" onsubmit="checkpassword()">Register</button>
			<p id="passwordmatcherror" style="display: none;"></p>
		</form>
        
		</form:form>
	</main>
	<footer>
		<p>&copy; CheersClub Website</p>
	</footer>
</body>
</html>
