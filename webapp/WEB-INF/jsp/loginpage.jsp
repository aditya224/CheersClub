<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<meta charset="UTF-8">
	<title>Login/Register</title>
	<style>
		/* Styles for the forms */
		/* form {
			display: none;
			margin: 10px;
			padding: 10px;
			border: 1px solid #ccc;
			border-radius: 5px;
			background-color: #f5f5f5;
			width: 300px;
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
		form input {
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
		} */
		/* second */
		/* Header styles */

header {
  background-color: #333;
  color: #fff;
  padding: 10px;
}

nav ul {
  display: flex;
  justify-content: space-between;
  list-style: none;
  margin: 0;
  padding: 0;
}

nav ul li {
  margin: 0;
}

nav ul li a {
  color: #fff;
  text-decoration: none;
  padding: 10px;
}

nav ul li a:hover {
  background-color: #666;
}

/* Login container styles */

.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  text-align: center;
}

h2 {
  font-size: 24px;
  margin-bottom: 20px;
}

form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

label {
  font-size: 18px;
  margin-bottom: 10px;
}




input[type="submit"] {
  width: 50%;
  padding: 10px;
  background-color: #ffcc00;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

input[type="submit"]:hover {
  background-color: #ffa500;
}

p {
  font-size: 16px;
  margin-top: 20px;
}

a {
  color: #ffcc00;
  text-decoration: none;
  font-weight: bold;
  transition: color 0.3s ease;
}

a:hover {
  color: #ffa500;
}
form button[type="submit"] {
			background-color: #4CAF50;
			color: #fff;
			padding: 10px;
			border-radius: 5px;
			border: none;
			width: 50%;
			cursor: pointer;
		}
		form input {
			display: block;
			margin-bottom: 10px;
			padding: 5px;
			border-radius: 5px;
			border: 1px solid #ccc;
			width: 50%;
			box-sizing: border-box;
		}

		

	</style>
	<script>
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
		

	</script>
</head>
<body>
	<header>
		<h1>Welcome to CheersClub</h1>
	</header>
	<main>
		<!-- <p id="Login Input incorrect"></p> -->
		<!-- <div>
			<button onclick="toggleForm('login')">Login</button>
			<button onclick="toggleForm('register')">Register</button>
		</div> -->
		<!-- <form:form modelAttribute="user" id="login-form" class="show" action="login" method="post"> -->
			
			
		<form id="login-form" class="show" action="login" method="post">
			<label for="email">Email:</label>
			<!-- <form:input path="email" size="30" id="email"/> <font color="red"><form:errors path="email"/></font></td> -->
			<input type="text" name="email" id="email" required>
			<label for="password">Password:</label>
			<!-- <form:input path="password" size="30" id="password" /> <font color="red"><form:errors path="password"/></font></td> -->
			<input type="password" name="password" id="password" required>
			<button type="submit">Login</button>
		</form>
		<!-- </form:form> -->
		<div class="form-group">
			<span>New user? <a href="registerpage">Register
					here</a></span>
	</div>
	</main>
	<footer>
		<p>&copy; CheersClub Website</p>
	</footer>
</body>
</html>
