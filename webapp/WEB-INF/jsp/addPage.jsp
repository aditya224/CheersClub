<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<anyxmlelement xmlns:c="jakarta.tags.core" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Homepage</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        #header {
            background-color: #333;
            color: #fff;
            padding: 10px;
        }

        #sidebar-left {
            float: left;
            width: 20%;
            background-color: #f2f2f2;
            height: 100vh;
        }

        #main-content {
            float: left;
            width: 60%;
            padding: 10px;
        }

        #sidebar-right {
            float: left;
            width: 20%;
            background-color: #f2f2f2;
            height: 100vh;
        }

        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        li {
            margin: 10px 0;
        }

        a {
            color: #333;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
        /* this is header design */
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            background-color: #3b5998;
            color: #fff;
        }

        .logo img {
            height: 40px;
        }

        .search-box form {
        display: flex;
        align-items: center;
        }

        .search-box input[type="text"] {
            width: 200px;
            height: 30px;
            margin-right: 10px;
            padding: 5px;
            border: none;
            border-radius: 5px;
        }

        .search-box button {
            height: 30px;
            padding: 5px 10px;
            border: none;
            border-radius: 5px;
            background-color: #fff;
            color: #3b5998;
            cursor: pointer;
        }

        nav ul {
            display: flex;
        }

        nav li {
            margin-right: 20px;
        }

        nav a {
            color: #fff;
            text-decoration: none;
        }
        /* style for creation of page */
        form {
			margin: 50px auto;
			width: 50%;
			background-color: #f2f2f2;
			padding: 20px;
			border-radius: 5px;
		}

		label {
			display: block;
			margin-bottom: 10px;
			font-weight: bold;
		}

		input[type="text"],
		textarea {
			width: 100%;
			padding: 10px;
			margin-bottom: 20px;
			border: none;
			border-radius: 5px;
			box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.1);
		}

		input[type="submit"] {
			background-color: #4CAF50;
			color: #fff;
			border: none;
			padding: 10px 20px;
			border-radius: 5px;
			cursor: pointer;
		}

		input[type="submit"]:hover {
			background-color: #3e8e41;
		}


        
    </style>
    <script>
        
        

    </script>
</head>
<body>
    <div id="header">
        <!-- Header content goes here -->
    </div>
    <div class="header">
        <div class="logo">
          <a href="#"><img src="logo.png" alt="Logo"></a>
          <p>Welcome, <c:out value="${user.firstName}"/></p>
          <p style="display: none;" id="userID" name="userID"><c:out value="${user.userid}"/></p>
        </div>
        
        <nav>
          <ul>
            <li><a href="homepage">Home</a></li>
            <li><a href="#">User Profile</a></li>
            <li><a href="#">Notifications</a></li>
            <li><a href="logout">Logout</a></li>
          </ul>
        </nav>
    </div>

    

    <div id="main-content">
        
        
            <form:form modelAttribute="page" action="createPage" method="post">
            <!-- <form action="createPost" method="post"> -->
                <input type="hidden" name="myParagraph" value="<c:out value='${user.userid}'/>">
                <label for="pageTitle">Page Title:</label>
                <form:input path="pageTitle"/>
                <!-- <font color="red"><form:errors path="pageTitle"/></font><br/> -->
                
                <label for="pageDescription">Page Description:</label>
                <form:textarea path="pageDescription" id="pageDescription" rows="4" cols="50" />
                <!-- <font color="red"><form:errors path="pageDescription"/></font><br/> -->
                
                <input type="submit" value="Add Page"/>
            </form>
        </form:form>
        
    </div>

    
</body>
</html>
