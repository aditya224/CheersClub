<!DOCTYPE html>
<html>
  <head>
    <title>CheersClub</title>
    <style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        }

    header {
        background-color: #333;
        color: #fff;
        padding: 20px;
        }

    nav ul {
        list-style: none;
        margin: 0;
        padding: 0;
        }

    nav ul li {
        display: inline-block;
        }

    nav ul li a {
        color: #fff;
        text-decoration: none;
        padding: 10px;
        }

    section {
        padding: 20px;
        }

    h1 {
        font-size: 36px;
        margin-bottom: 20px;
        }

    p {
        font-size: 18px;
        line-height: 1.5;
        margin-bottom: 20px;
        }

    footer {
        background-color: #333;
        color: #fff;
        padding: 20px;
        text-align: center;
        }
    /* for author design */
    .author-details {
        display: flex;
        align-items: center;
        gap: 1rem;
        font-family: Arial, sans-serif;
        font-size: 1.2rem;
        color: #333;
        background-color: #f7f7f7;
        padding: 1rem;
        border: 1px solid #ccc;
        border-radius: 5px;
        }

    .author-details img {
        width: 100px;
        height: 100px;
        border-radius: 50%;
        }

    .author-details h2 {
        margin: 0;
        font-size: 1.5rem;
        font-weight: bold;
        }

    .author-details p {
        margin: 0;
        font-size: 1.2rem;
        font-style: italic;
        }


    </style>
  </head>
  <body>
    <header>
      <nav>
        <ul style="display: flex;">
          <li><a href="/CheersClub">Home</a></li>
          
        </ul>
      </nav>
    </header>
    <section>
      <h1>Welcome to My Website</h1>
      <p>In this application, there will be 3 main actors:<br/>
        1.	User/Authors -  to post, comment and send votes.<br/>
        2.	Maintainers/Moderators - to maintain Users and Pages<br/>
        3.	Admin - to maintain moderators<br/>
        <br/>
        Introduction and Objective:<br/>
        The goal of this proposed project is to develop a social networking site like to Twitter or StackOverflow,<br/>
        but with a focus on improving the user experience. Users will be able to connect with one another through the platform, <br/>
        exchange ideas, and take part in conversations that are relevant to their interests. <br/>
        The site will also include a robust search and tagging system that will allow users to quickly find and follow conversations on subjects that are important to them.<br/>
        The platform will enact stringent moderation to detect and delete hazardous content to guarantee a secure and welcoming environment for all users. <br/>
        Our platform, in our opinion, has the potential to be a useful tool for people and groups looking to connect and collaborate in meaningful ways.<br/>
        Users can create profiles, follow other users, and share short messages or "tweets" related to specific topics or themes.<br/>
        
        Moderators/Maintainers role is to check whether the content is meaningful or not, and check if users are authenticated.<br/> 
        The role of Admins is to add/delete moderators and maintain posts.<br/>
        
        Finally, if time permits, Additionally, the platform will offer a chat application that allows users to communicate in real-time with other users, <br/>
        either one-on-one or in groups, to exchange ideas, collaborate on projects, or simply network with like-minded individuals.<br/>
        <br/>
        Use cases:<br/>
        <br/>
        1.	User:<br/>
        -   Can post<br/>
        -	Can update profile<br/>
        -	Can comment or can upvote/downvote<br/>
        -   Can request for a page,can be added to a specific Page<br/>
        -   Can initiate a topic <br/>
        -   can comment on a given topic<br/>
        2.	Moderator/Maintainer:<br/>
        -	Can create Pages<br/>
        -	Can Add/restrict user to their page<br/>
        -	Can create page for specific content<br/>
        3.	Admin:<br/>
        -	Can add/remove Moderator<br/>
        For this application I'll be implementing it in SpringBoot, using MVC pattern with service and security. <br/>
        For database connectivity, I'll be using Hibernate and MySQL having DAO pattern. <br/>
        Finally, for front-end I'll use Javascript and JQuery functionalities.</p><br/>
        <br/>
        <br/>
    </section>
    <div class="author-details">
        
        <h2>Aditya Raj</h2>
        <h3>NUID: 002774709</h3>
        <p>Web Development and Tools - Final Project [Spring 2023]</p>
    </div>
      
    <footer>
      <p>&copy; 2023 CheersClub</p>
    </footer>
  </body>
</html>
