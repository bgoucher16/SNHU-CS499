**Code Review Link**
<a href="https://www.youtube.com/watch?v=VrqJ5HYfxyo"> Click Here </a>

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Professional Self-Assessment**

I starting my journey a little over 4 years ago starting at Pierce Community College in Puyallup Washington. I started my major as a Health Information and Technology Major (HIT). The main languages that we focused on there were C#, Python, and HTML. Taking a look back at some of the code that I had written was astonishingly bad. Before getting my AA from there I transfered to SNHU and worked extremely hard in this program to be able to get to the end. Completely classes like Data Structures and Algorithms and Mobile Architecture and Programming which are typically seen as harder classes.

At this current moment in time I am yet to program something along side other people. All of my work has been done by me and myself only. That being said though, I have worked at Target for a little over 5 years and with that I have had the opportunity to work along side some incredible people. Collaborating with them to achieve the companies goal of ensuring our guests not only have a good time while shopping, but are also able to find every item that they are looking for.

In the near future I hope to get a job as a software enginner or even possibly a data engineer. I am leaning more towards the data engineer because of I love to take data and transform it into a more user friendly readable display. Using my knowledge of databases and programming languages like Python I am confident in my abilities to achieve this goal of mine.

**Data Structures and Algorithms**

Coming into this class I had heard from multiple friends that this was going to be one of the classes that I was going to need to study and know super well. Having completed the class they were not wrong. I learned all about Linked Lists, HashMaps, Binary Search Trees and Vectors. Coming out of the class I have learned even more about time complexities and how each of them are supposed to be used. For example in my final I use a Binary Search Tree because it is one of the fastest ways of searching for specific items in a given list. 

**Mobile Architecture and Programming**

Certainly one of the classes I was super exicted about taking. For this class I was tasked with creating an inventory mobile application that had some rules.
<ul>
  <li>
    Any user must be able to sign or register into a database.
  </li>
  <li>
    There must be a way for users to add items to there database.
  </li>
  <li>
    Each user should only be able to see there items only and no body elses.
  </li>
  <li>
    There needed to be SMS Notifications enabled for when an item reached low quantity.
  </li>
  <li>
    A way for users to see all of there items in list format.
  </li>
  
</ul>

**Databases and Security**

Combining these for me makes sense as all of the security that we have done as been working with databases. A lot of security is through authenication as well as encrypting password into SHA256 encryption. In both of the final projects that I created they both have some form of user authenication when signing in, but they use completely different Databases. The Mobile App uses SQL while the Flask Web Page uses MongoDB. Although they are both databases the use of them is completely different. SQL is typically used when you will have very structured data and in the Mobile app everything was very structured either being and string and an integer and very little changing between integers and strings. Whereas MongoDB makes everything into a JSON file which is significantly more flexible. This is what it typically looks like when inputting anything into a database created using Mongo.

![image](https://github.com/user-attachments/assets/a2c14d2f-12c9-41e2-9617-155a4ee419ef)


---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Course Outcomes**

  - I Employ strategies for building collaborative environments that enable diverse audiences to support organizational decision making in the field of computer science
  - I Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts
  - I Design and evaluate computing solutions that solve a given problem using algorithmic principles and computer science practices and standards appropriate to its solution, while managing the trade-offs involved in design choices
  - I Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals
  - I Develop a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Artifact 1**

**Software Engineering and Design**

This project started as my final project from Software Reverse Engineering where we took a binary file, converted it into assembly, then created a C++ application based on that assembly code. The main function of this file was for a user to sign in with very little authentication and then be able to type in a number like 1 where they would then be able to view all clients and then 2 to change there service from either Retirement or Brokerage. 

So I wanted to showcase my skills of taking that text application and make it into a full webpage with a database included into it. Knowing that I love to use Python the most and having found the Flask library I knew exactly what I was going to do.

I created a full webpage using the flask library, Python, HTML and CSS. This application does a little more than just show clients, after implementing the database, I was about to make it so that each user could have there own client list as well as add and remove clients as needed. This took a lot of time reading documentation and figuring out how to take each aspect from the database and be able to display it on the HTML pages, but with some time I got it working and created this:

<div align="center">
  <img src="https://github.com/user-attachments/assets/39b5b552-db59-4297-9cf7-183f2ef85f16" width="600px" height="350px">
</div>

All clients are able to be removed, added and even there service type changed between the two original options "Retirement" or "Brokerage"

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Artifact 2**

**Data Structures and Algoritms**

This project was apart of the Mobile Arcitecture and Programming class. In the beginning it had all of the requirements as mentioned above, but to improve this project it was going to need some functionality for future use. So, I wanted to add a search bar, but just adding a search bar with a query wasn't going to be good enough. So, my plan was to make it so that when a user enters in an item number that they are looking for it will input all of the items from the database that relate to them into a Binary Search Tree and then iterate through that tree to find the number that was previously searched for. If the number is not found then it will return False and tell the user that the number was not found, but if it is found they will be taken to a new screen that shows them all the information that could want about that specific item.

This Binary Search Tree has two possible time complexities depending on how that specific user creates there item numbers. If they do them in order then it will be O(n) because it essential just turns into a linked list, but if ordered randomly they it will be O(log n) since the tree will be able to go through significantly less numbers to find the number that the user is looking for. At this point in time there isn't going to be any difference in time, but if the app where to have a user with hundred upon hundreds of items I am sure the time would be semi noticable.

Takes all of the items from the database and creates the tree testing if they are either greater than or less than the current item.
<div align="center">
  <img src="https://github.com/user-attachments/assets/a14b80dc-4b64-49bf-a0eb-d7a037529b12" width="600px" height="350px">
</div>

Then the item is searched for and it does a similar process but its a check to see which direction in the tree the next number will be. If its less than it will go left and if its greater than it will go right until the correct number is found.
<div align="center">
  <img src="https://github.com/user-attachments/assets/4d95b9c9-cc92-44ce-b0b5-e99015215b70" width="600px" height="350px">
</div>

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Artifact 3**

**Databases**

For the database, I wanted to use the same file from the Software Reverse Engineering class but instead of a little hashmap that was about 6 lines long and held 5 different clients I knew I could make something so much better. For the database I used MongoDB as it was what I was more comfortable with using it Python, also because of being able to use PyMongo. Then came the structuring of how each part of it was going to be connected.

There were two different collections needed for this project. The "Users" and "Clients."

For the Users collection it was going to have to look something like this:

<ul>
  <li>_id: auto-generated (MongoDB does this automatically)</li>
  <li>username: "input from user"</li>
  <li>password: "input from user"</li>
</ul>

To be able to connect the two databases I was going to have to make one the primary key. It only made sense to use the _id that was auto generated since it will never be the same as another user.
Then for the Clients part is was going to look like this:

<ul>
  <li>_id: auto-generated (MongoDB does this automatically)</li>
  <li>client_id: generated with a +1 increment</li>
  <li>name: "Input from user"</li>
  <li>service: Choice either 1 or 2</li>
  <li>user_id: copied from the current users session</li>
</ul>

So, when a new client was created the code takes in the current users _id and sets it as the user_id. This was made so that each user could see there own clients. 

<div align="center">
  <img src="https://github.com/user-attachments/assets/39b5b552-db59-4297-9cf7-183f2ef85f16" width="600px" height="350px">
</div>

Just like in this screenshot, I am signed in as myself and I have two clients, Jane Doe and John Smith. But in the database there are multiple other clients registered to other accounts to test this logic.



