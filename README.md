# StuConect

Description:

The Project aims to provide constant communication between student group and authority group. Functionalities includes information sharing, real time location sharing and message sharing. Google, Facebook and Firebase API's were used to support the project.

Technical details:

----backend----
Firebase API acts as a backend server for this project.

----UI----
Bottom Navigation is used to switch between news feed, list friends, map friend, chat activities (Fragments). Google Maps for locating the user. List Views and custom adapters were used for news feed and chat activities.

----Login----
To perform login implementations, Google and Facebook login API's and email method from firebase were integrated into the projects. To identify each user we are generatingg token id and storing it in Firebase database.

----Authentication----
Firebase authentication is used to 

----news feed----
For information sharing module, list view with custom adapter were used for better implementation. It can display the post of the user along with the timestamp and the name of the user. Floating action button acts as starting point for the user to initiate his posting process.

----Maps----
Google Maps API were to point the location of the particular user. Markers were used to show info about the user i.e name and timestamp.
Service class was used to get the location details of the user in background at the time interval of 60 seconds.

----chat----
Listview, by which the messages of the were displayed. FirebaseListAdapter gets the work done with ease. Messages are stored in the Firebase's realtime databases.

----profile----
Just to know about the registered user with their account type, email and name.




