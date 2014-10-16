FredFlintstone
==============
The Sport Event Organizer App is a user-friendly Android application that allows people to quickly organize mass sport events. 
With couple of clicks the user is able to create a sport event and to announce it the app, where the other users can check a wide list of events, pick the one they find most attractive for them and call the creator to confirm their presence.
For the purpose of the app we are using custom grid view and fragments for listing the events and showing the detail information of the event.
Camera and geolocation are used for taking photo of the event ground and receiving a map of the event location.
For visualizing the event details we are using contacts, in order to recognize if the organizer is one of your friends in your phonebook.
Connection is used together with call intend to prevent the call in 2G network state and brake the app connection. 
In addition we are using third-party provider for visualizing the location via Google Maps.
The SQLite data storage is used for saving the favorite event indexes in order to save space on the phone. The complete data is stored in remote server database (Telerik backend services) and taken asynchronously through cursor. All the services that may influence the UI thread are performed at the backend too.
The Interface is user friendly with large visible buttons and clues where needed, multiple device screen support and fragment usage. Notification are used when data is submitted and when user needs to be informed for important events in the app.
The gestures used in the app are touch, long press, swipe and fling.
