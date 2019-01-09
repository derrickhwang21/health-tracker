# Codefellowship
Building an Android health tracker app. The features are built out over the next several labs.
## Feature Task

#### lab 26 - Feature Tasks

##### Finger Exercises
One key part of health is building finger strength and endurance.

On the main page, display a number and a button. The number should increase when the button is clicked.

##### Stopwatch

On the main page, add a stopwatch.

Have a button to `[Start/Pause]` and `Reset` the clock. The start/pause button should toggle between saying "start" and "pause". And you should only be able to reset when the stopwatch is paused.

Have the view udpate as fast as possible while still leaving the buttons responsive

Display the clock as `0:00:00.000` so it should be able to track up to 9 hours, 59 minutes, 59 seconds, and 999 miliseconds.

##### Insipring Image Carousel

Display an image carousel with caption. Allow users to cycle through images and read captions. Pressing next should go to the next image and its caption, pressing prev should go back

The app should display a `(1/N)`, `(2/N)` indicator so users know how many items are in the list.
#### lab 17
31
​
32
Allow users to log in to CodeFellowship and create posts
33
* Upon logging in, users should be taken to a `/myprofile` route that displays their information
34
​
35
Ensure homepage, login, and registration routes are accessible to non-logged in users. All other routes should be limited to logged-in users.
36
​
37
Ensure user registration also logs users into the app automatically
38
​
39
Add a `Post` entity
40
* A `Post` has a `body` and a `createdAt` timestamp
41
* A logged-in user should be able to create a `Post`, and a post should belong to the user that created it
42
​
43
A user's posts should be visible on their profile page
44
​
45
When a user is logged in, the app should display the user's username on every page (in the navbar)

## Setting Up
* clone repository
    * ensure the following dependencies are installed:

         `dependencies {
    	implementation('org.springframework.boot:spring-boot-starter-data-jpa')
        	implementation('org.springframework.boot:spring-boot-starter-security')
        	implementation('org.springframework.boot:spring-boot-starter-thymeleaf')
        	implementation('org.springframework.boot:spring-boot-starter-web')
        	runtimeOnly('org.springframework.boot:spring-boot-devtools')
        	runtimeOnly('org.postgresql:postgresql')
        	testImplementation('org.springframework.boot:spring-boot-starter-test')
        	testImplementation('org.springframework.security:spring-security-test')
        	testCompile("org.springframework.boot:spring-boot-starter-test")}`
* From your console, connect to PostgreSQL
    * Create `codefellowship_app` database
    * Connect to `codefellowship_app` database
* In the command line, use `gradle bootrun` to start application.
* Go to localhost: 8080 from a browser to view client side server

**Prior to building and running application be sure `spring.jpa.hibernate.ddl-auto=create` is uncommented within the application.properties to initialize table schema. After running locally once, be sure to comment this out or delete to avoid dropping existing table.**
