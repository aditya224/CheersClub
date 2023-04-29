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

        .post-description {
                margin-bottom: 10px;
        }

        /* .topic-description img {
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
        /* styling for comments */
        .comment {
            background-color: #f2f2f2;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-size: 14px;
            line-height: 1.5;
        }

        .comment p {
            margin: 0;
        }

        .comment .author {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .comment .date {
            font-style: italic;
            color: #999;
        }
        /* button styling */
        .link {
            background-color: #4CAF50; /* Green background color */
            color: white; /* Text color */
            padding: 10px 20px; /* Padding */
            border: none; /* Remove border */
            border-radius: 4px; /* Rounded corners */
            cursor: pointer; /* Change cursor on hover */
            transition: background-color 0.3s ease; /* Add transition effect */
            }

            /* Change background color on hover */
        .link:hover {
            background-color: #3e8e41;
        }
        
        .postSearch {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
            text-align: center;
            font-family: Arial, sans-serif;
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        a {
            color: #333;
            text-decoration: none;
        }

        a:hover {
            color: #000;
            text-decoration: underline;
        }
        .my-button {
            background-color: #4CAF50; /* Green background color */
            color: white; /* Text color */
            padding: 10px 20px; /* Padding */
            border: none; /* Remove border */
            border-radius: 4px; /* Rounded corners */
            cursor: pointer; /* Change cursor on hover */
            transition: background-color 0.3s ease; /* Add transition effect */
            }

            /* Change background color on hover */
        .my-button:hover {
            background-color: #3e8e41;
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
        function updateUpvotes(button,postid,userid) {
            $.ajax({
                type: "POST",
                url: "updateVote",
                data: { userid: userid,
                        postid: postid,
                        button: button },
                success: function(response) {
                // update the UI to reflect the new number of upvotes
                
                    $("#likesCount").text(response.updateVotes);
                },
                error: function() {
                alert("Error updating upvotes");
                }
            });
        }
        
        function requestPage(pageid,userid) {
            var myVar = pageid;
            $('#'+myVar).disabled = true;
            $.ajax({
                type: "POST",
                url: "requestPageApproval",
                data: { pageid: pageid,
                        userid: userid },
                success: function(response) {
                // update the UI to reflect the new number of upvotes
                
                //$("#"+id).find('.status').text(response.status);
                //var row = $('#' + id).closest('tr');
                //row.find('.status').text(response.status);
                location.reload();
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
        <div class="search-box">
          <form>
            <input type="text" placeholder="Search...">
            <button type="submit">Search</button>
          </form>
        </div>
        <nav>
          <ul>
            <li><a href="homepage">Home</a></li>
            <li><a href="#">Your Profile, ${user.firstName}</a></li>
            <li><a href="#">Notifications</a></li>
            <li><a href="logout">Logout</a></li>
          </ul>
        </nav>
    </div>

    <div id="sidebar-left">
        <ul>
            <c:forEach var="page" items="${pageList}">
                <li>
                    <a href="${page.pageTitle}">${page.pageTitle}</a>
                </li>
            </c:forEach>
            <c:forEach var="userpage" items="${userpageList}">
                <li>
                    <a href="yourPages/${userpage.page.id}">${userpage.page.pageTitle}</a>
                </li>
            </c:forEach>
            
            <c:if test="${sessionScope.user.userType == 'MODERATOR'}">
                <li><a href="yourPages/approvePageRequest">Approve Page requests</a></li>
                <li><a href="yourPages/addPage">Add a Page</a></li>
            </c:if>
            <c:if test="${sessionScope.user.userType == 'USER'}">
                <li><a href="yourPages/searchForPages">searchForPages</a></li>
                
            </c:if>
        </ul>
    </div>
    
    <div id="main-content">
        
        <!-- <button onclick="toggleForm()">Post</button> -->
        <ul>
            <div class="postSearch">
                <h3>Posts</h3>
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
                            <img src="${pageContext.request.contextPath}/images/upvote.jpg" height="15px" width="15px" alt=...>
                            <span id="likesCount">${p.likeCount}</span>
                            <img src="${pageContext.request.contextPath}/images/downvote.jpg" height="15px" width="15px" alt=...>
                            <div class="post-actions">
                                <button onclick="updateUpvotes('upvote','${p.postid}','${user.userid}')">Upvote</button>
                                <button onclick="updateUpvotes('downvote','${p.postid}','${user.userid}')">Downvote</button>
                                
                            </div>
                        </div>
        
                        </li>
                </c:forEach>
            </div>

            <div>
                <h3>Pages</h3>
                    <table>
                        <tr>
                            <th>Page Title</th>
                            <th>Page Description</th>
                            <th>Moderator</th>
                            <th>Request For Access</th>
                        </tr>

                        <c:forEach items="${searchPagesList}" var="spl">
                            <c:set var="pageid" value="${spl.page.id}"/>
                            <div id="${pageid}">
                                <tr>
                                    <td>${spl.page.pageTitle}</td>
                                    <td>${spl.page.pageDescription}</td>
                                    <td>${spl.page.moderator.firstName} ${spl.page.moderator.lastName}</td>
                                    <!-- <td><button class="my-button" id="moderatorDetailsButton" onclick="displayModeratorDetails('${moderator}')">View Details</button></td> -->
                                    <c:if test="${spl.status == 'PENDING'}">
                                        <td><p>Access Requested</p></td>
                                    </c:if>
                                    <c:if test="${spl.status == 'APPROVED'}">
                                        <td><a href="yourPages/${spl.page.id}" class="link">Go To Page</a></td>
                                    </c:if>
                                </tr>
                            </div>
                        </c:forEach>
                    </table>
            </div>

            
        </ul>
                
                    
            

        
    </div>

    
</body>
</html>
