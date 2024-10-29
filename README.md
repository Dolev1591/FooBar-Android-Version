# Welcome to FooBar Social Network (Android version)

This repository contains the source code for our Android Studio project,
an Android application developed using Android Studio, Java, and the Android SDK.
The project is centered around the FooBar Social Network. Given that this project is client-server based,
you can find our server repository here: https://github.com/DeanCo100/FooBar-Server.

NOTE: We assume your server runs locally at this address: http://10.0.2.2:8080/
If you wish to change the address, you can find it in strings.xml under BASE_URL.

How It Works:
1.Clone the project from GitHub and unzip it.
2.Open the project in Android Studio.
3.Android SDK: Ensure your Android Studio is configured with the necessary SDK versions:
4.Minimal API Level: 24 (minimal SDK)
5.Optimal API Level: 34 (optimal and compile SDK)
6.You might want to do File > Sync Project with Gradle Files to ensure all dependencies are included.
7.Make sure your server is up and running and the URL address is correct.
8.Build and run the application on an Android emulator or device.
9.Register, log in, and that's it! Enjoy our app.
KEY NOTES:

-Our project supports dark mode, meaning if your phone is on "Dark Mode,"
 then the app will be compatible and be in "Dark Mode" too, and vice versa.
- In order to view a person's profile, you need to press on their username.
  Some of the activities have 3 dots in the right corner; you can use it for various actions.
  
About Our Work:
#### Firstly:
- We checked the task's requirements and thought about how to implement the relevant changes to the UI.
- We conducted a few changes in the existing UI.

#### Secondly:
- After creating the relevant new APIs on the server (e.g., friends, delete user, edit user, etc.), we made the necessary adjustments and created the relevant functions to support that.
-  We implemented the MVVM model to communicate with the server and get data.

#### Thirdly:
- We modified the relevant UI, including adding more activities such as: home page, edit user profile, etc.

#### Lastly:
- We made final modifications in the code.
- We conducted a few 'User Reviews' of our program.
- We took screenshots (that we present below) to show you what it's like to use our social network.
- We ENJOYED using our program as users. And of course,
- we submitted the completed project.

# UI Experience Demonstaration:
1. Login page: (Enter username and password to proceed or create a new account)

![login page.png](screenshotsUI%2Flogin%20page.png)

2. Sign Up page: (Enter your details according to the instructions and choose a profile picture - After this step you can login to FooBar)
   
![sign-up page.png](screenshotsUI%2Fsign-up%20page.png)

![sign-up page 2.png](screenshotsUI%2Fsign-up%20page%202.png)

3. After you created an account and logged in, that's the Feed page you'll see:
   
![feed page.png](screenshotsUI%2Ffeed%20page.png)

![feed page2.png](screenshotsUI%2Ffeed%20page2.png)

4. Press on '+' on the top right part of the screen to add a new post:
   
![add post.png](screenshotsUI%2Fadd%20post.png)

![add post 2.png](screenshotsUI%2Fadd%20post%202.png)

5. Press on the pencil logo on the top right part of the screen to edit the user's profile:
   You can change his display name and/or profile picture (by taking a new photo or attaching a photo from the gallery)
   
![edit profile.png](screenshotsUI%2Fedit%20profile.png)

6. On the Edit Profile page, after you make changes, you can press on the dots logo at the top right part of the screen and press 'Set Changes' in order to save them.
   Also you have an option to Delete User - mind that it will delete the user and all of his posts!
   
![delete user option.png](screenshotsUI%2Fdelete%20user%20option.png)

7. Edit Post - A user can edit his own posts by pressing on the pencil logo that appears on the right side of his post.
   Notice: only the logged in user can update his own posts

![edit post.png](screenshotsUI%2Fedit%20post.png)

8. On the Edit Post page, the user can write a new message or change the post photo. 
   By pressing on the dots logo the user can set the changes he made, delete the photo or delete the post.

![edit post options.png](screenshotsUI%2Fedit%20post%20options.png)

9. How to make new friends? By clicking on the user that shared a post, you will be able to send him a friend request (if you aren't friends yet)

![friend request.png](screenshotsUI%2Ffriend%20request.png)

10. Friend requests and list of friends:
    By pressing on 'Friends' on the bottom of the screen you can see your friends list. 
    Pressing on 'Notifications' you will see your pending friend requests.

![friends list2.png](screenshotsUI%2Ffriends%20list2.png)

![friend requests.png](screenshotsUI%2Ffriend%20requests.png)

11. By clicking on a user that is one of your friends, you will be able to see all of their posts and of course give a like!
    You can delete a friend by pressing on the dots logo and then pressing on delete.

![seeing others post - only if a friend.png](screenshotsUI%2Fseeing%20others%20post%20-%20only%20if%20a%20friend.png)

12. By pressing on "Menu" at the bottom of the screen you will be able to see your posts - by pressing on "My Profile"
    or you can press on "Log Out" and then you will log out and will be taken to the Login Page.

![pressing on menu.png](screenshotsUI%2Fpressing%20on%20menu.png)

![user posts.png](screenshotsUI%2Fuser%20posts.png)

13. Our app also supports dark mode if you feel moody...

![dark mode1.png](screenshotsUI%2Fdark%20mode1.png)

![dark mode2.png](screenshotsUI%2Fdark%20mode2.png)

14. Screenshots of the database:

Users:

![users mongoDB.png](screenshotsUI%2Fusers%20mongoDB.png)

Posts:

![posts mongoDB.png](screenshotsUI%2Fposts%20mongoDB.png)
