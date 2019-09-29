# World wide countries listing

#### This sample was created to showcase my skills and the latest learning in the Android Framework.

##### To be able to compile this project, you need to add the following in your global gradle
properties file(For the here maps static map image):

##### MAP_APP_ID
##### MAP_APP_CODE

For this client app I used https://restcountries.eu to fetch the countries list

Used Glide for normal image loading

Tweaked Glide to be able to load SVG images over the web

Used Navigation component to provide the navigation in the app

Kotlin-coroutines were used for blocking operations(fetching countries list from the server,
generating dominant colors to tint the action bar + status bar)

Added some nice animation for the search view in the actionbar

Used LiveData and ViewModels as well

Koin was used to glue the different components together in the application

Included some few number of unit tests as well.


### Architecture:

I am using the MVVM architecture and some state machine concept on top of it.
Every screen has a view, a model, and a ViewModel. The ViewModel contains a state that represents
the properties of the View. This state will be emitted using LiveData to the observer(view).

The ViewModel state is represented using a simple kotlin data class with different fields.

I also use sealed classes to model some repetitive behaviors. Like, when fetching data in an
asynchronous fashion, the usual states are Loading, Failed(with the failure), or Success(with the
actual data).

Repository is the single source of truth that is used to fetch data(either from the network or
from the local storage)

### Future Enhancements:

Maybe, in addition to the countries list screen. Another way of viewing the countries would be by
creating a google maps screen with markers of all the countries on the map(Marker clustering may be
useful here). User would be able to switch between listview or map view at his convenience.
Clicking on the marker will show an info dialog with the country flag. clicking on the info dialog
will navigate the user to the same country details screen.


Here is a gif demonstrating this super mini app:


Search behavior and animation:

![Search animation Gif](/readmeassets/country_picker_search_animation.gif)


Countries listing and details

![App Gif demo](/readmeassets/country_picker_list_details.gif)