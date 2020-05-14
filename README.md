# FYE
The Official Application for The First Year Experience Program at The American University in Cairo (2015)

🏫📓 The application is implemented in **iOS** using **Swift & Objective-C** and in **Android** using **Java** with **MySQL** databases as a backend, interfaced to using **PHP scripts**. It provides freshmen with information regarding AUC (places that offer student discount, on-campus services and food outlets, etc.) and the FYE program (schedules, maps, and inforgraphics) as well as useful services such as group chat rooms, and an in-board video player for FYE's Youtube channel.
It used to be available on the App Store and Play Store and has been downloaded and used by 3,000+ people, but is now discontinued.

### I had planned to further expand the application in the following ways:
* To create my own map implementation for both Android and iOS.
* To integrate the food outlet menus and allow students to order from the application.
* To implement a wallet system where students can store change that any outlet has not given them back and spend it later.
* To find a way to make a profit out of the app, either by charging food outlets, or looking for monthly sponsors.

## Third Party APIs Used:
### Android:
* Picasso - for handling images
* PhotoView - for zooming in on images
* Youtube API - for getting videos in a channel and displaying them
* Firebase - for authentication, notifications, and messaging
### iOS:
* Firebase - same as android
* Youtube API - same as android
* ImageScrollView - for zooming in on images

## Features in more details:
* Users can sign up for the application, sign into it if they have an existing account, change their passwords, and log out.
* There is a help page displayed upon opening the application, describing all the features in details to the user.
* The Academic Guide button lists all the majors, minors, and core curriculum, displays info about them upon clicking on one, and can open a link to the AUC catalog entry for the relevant selection.
* The Clubs button lists all the clubs in the university, and displays their mission and contact information upon clicking on one.
* The Maps button links to the application developed by Mostafa El Leithy & Windrose Co. called AUC Maps on android, and currently does nothing on iOS.
* The Message Boards button subscribes the user to the relevant house and group message board which they select during signing up if they are FYE students, if not they are only subscribed to the announcements message board. Upon selecting a message board, they can view and send messages, except for the announcements message boards, only admins can send messages on it.
* The FAQ button displays common questions freshmen have and the answer upon selecting one.
* The Important Links button displays useful links such as the AUC mail, and the bus schedule, and can open them in the browser.
* The Discounts button lists the places that give discounts to AUCians, and where they are located, their numbers, and the discounted amount upon selecting one.
* The Food Outlets button lists all the food outlets on campus, their locations, working hours, and number upon selecting one.
* The Facilities and Services button lists all the facilities in the universities, and then lists the services each one offers.
* The 3 Day Schedule, FYE Guide, Majors Fair, Engagement Fair, FYE Stations, and Treasure Hunt buttons open zoom-able pictures provided by FYE.
* The Competition button opens a list of houses, and the ranked group winners for each house for the FYE competition.
* The Youtube Channel button lists all the videos on FYE's Youtube channel and can play them upon selection.
* Notifications can be sent manually, or whenever there is a new message in a message board, and will be pushed to the user whether the app is in the foreground, background, or terminated.

Screenshots and videos can be found here: https://malaksadek.wordpress.com/2019/08/22/fye-the-first-year-experience/

# Contact

* email: mfzs1@st-andrews.ac.uk
* LinkedIn: www.linkedin.com/in/malak-sadek-17aa65164/
* website: https://malaksadek.wordpress.com/
