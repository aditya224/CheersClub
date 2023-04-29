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
            background-color:  #3b5998;
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
            padding: 5px;
            background-color:  #3b5998;
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
            width: 400px;
            height: 40px;
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
        document.getElementById("useridinput").innerHTML = document.getElementById("userID").textContent;
        function toggleForm() {
			var postcontainer = document.getElementById("container");
			
			if (postcontainer.style.display === "none") {
                postcontainer.style.display = "block";
            } else {
                postcontainer.style.display = "none";
            }
		}
        function deletePost(postid) {
            $.ajax({
                type: "POST",
                url: "deletePost",
                data: { 
                        postid: postid,
                         },
                success: function(response) {
                // update the UI to reflect the new number of upvotes
                    location.reload();
                    //$("#"+postid).text(response.updateVotes);
                },
                error: function() {
                alert("Error updating upvotes");
                }
            });
        }

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

    

    <div id="main-content">
        <!-- <button onclick="toggleForm()">Post</button> -->
        
        
        <ul>
            
            <!-- Add more posts here -->
            <c:forEach var="p" items="${posts}">
                <li>
                <div class="p">
                    
                    
                </div>
                <!-- JSP code -->
                <div class="post-container">
                    <div class="post-header">
                    <!-- <img src="profile-picture.jpg" alt="Profile Picture"> -->
                    <div class="post-header-text">
                        <h3>${p.postTitle}</h3>
                    </div>
                    </div>
                    <div class="post-content">
                        <p>${p.postContent}</p>
                    
                    <img src="data:image/jpeg;base64,${post.base64Image}" alt="Post Image">
                    </div>
                    
                    <span id="${p.postid}">${p.likeCount}</span>
                    <div class="post-actions">
                        <!-- <button onclick="deletePost('${p.postid}')">Upvote</button> -->
                    </div>
                    
                </div>
  
                </li>
            </c:forEach>
        </ul>

        
    </div>

    
</body>
</html>
