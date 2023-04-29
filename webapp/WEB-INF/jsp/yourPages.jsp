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
        /* style for post container */
        .container {
            display: inline-block;
            margin: auto;
            width: 50%;
            border: 3px solid #73AD21;
            padding: 10px;
        }

        label {
            display: inline;
            font-weight: bold;
            margin-top: 10px;
        }

        form:input[type=text], form:textarea {
            width: 50%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        input[type=submit] {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 50%;
        }

        input[type=submit]:hover {
            background-color: #45a049;
        }

        /* style for posts */
        /* CSS code */
        .post-container {
            border: 1px solid #ddd;
            padding: 10px;
            display: flex;
            flex-direction: column;
        }

        .post-header {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .post-header img {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-right: 10px;
        }

        .post-content {
                margin-bottom: 10px;
        }

        /* .post-content img {
            width: 100%;
            height: auto;
            margin-top: 10px;
        } */

        .post-actions {
            display: flex;
            justify-content: space-between;
            border-top: 1px solid #ddd;
            padding-top: 10px;
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
            <a href="homepage">
                <img src="${pageContext.request.contextPath}/images/logo.png" height="20px" width="70px" alt=...>
            </a>
        </div>
        
        <nav>
          <ul>
            <li><a href="homepage">Home</a></li>
            <li><a href="#">Your Profile, ${user.firstName}</a></li>
            <!-- <li><a href="#">Notifications</a></li> -->
            <li><a href="logout">Logout</a></li>
          </ul>
        </nav>
    </div>

    <div id="sidebar-left">
        <ul>
            <c:forEach var="page" items="${pageList}">
                <li>
                    <a href="yourPages/${page.id}">${page.pageTitle}</a>
                </li>
            </c:forEach>
            <c:forEach var="userpage" items="${userpageList}">
                <li>
                    <a href="yourPages/${userpage.page.id}">${userpage.page.pageTitle}</a>
                </li>
            </c:forEach>
            
            <c:if test="${sessionScope.user.userType == 'MODERATOR' and sessionScope.user.status == 'APPROVED'}">
                <li><a href="yourPages/approvePageRequest">Approve Page requests</a></li>
                <li><a href="yourPages/addPage">Add a Page</a></li>
            </c:if>
            <c:if test="${sessionScope.user.userType == 'USER'}">
                <li><a href="yourPages/searchForPages">searchForPages</a></li>
                
            </c:if>
        </ul>
    </div>

    <div id="main-content">
        
        Select A Page!
        
    </div>

    
</body>
</html>
