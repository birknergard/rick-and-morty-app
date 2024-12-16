# android-exam
Exam for android development program.
    
    ## Completed so far:
    A lot of backend stuff
    Make sure location fetching API actually works as intended.
    Make database functional. There are typeconverters necessary since i need a 
    location table as well as the main one with the created characters.
    UI for screen 01
    initial UI for screen 02
    UI for screen 03
    Basic backend for screen04
    NavBar
    Implement UI for screen 04
    Implement interactivity for screen 04
    Polish UI for screen 02 and 01
    General refactor and cleaning up the codebase. Optimize.
    Make UI as pretty as possible.


    
    ### Final 
    Polish polish polish

    ## Screen 4 functionality
    On screen 4 you will be able to see every episode of Rick and morty. They will be
    divided into seasons. If you click on the episode you can see all of the episodes
    appearing characters (image and name). This info fetched by an API call that only
    calls if you click the episode in particular. Reason for this is setting a database
    framework for preloading all characters is too much work for now, and would require
    the creation of a whole new database system. It would also sort of make screen01 obsolete.

# Rapport
1. 

    Navigation bar:
    On any of the screens the user can navigate to every screen like on for example instagram. When navigating around, the state of each screen is stored so that when
    the user goes back to a screen, it will appear in the same state as it was when last used.

    Screen 01 : CharacterList:
    The user can view a list of characters and their attributes from the TV-show rick and morty. When reaching the bottom of
    the list the user can click the arrow to load more characters to increase the number of characters, until there are no more characters to load.

    Screen 02 : Your created characters:
    On this screen the user can view their created characters (stored in local database) with all their attributes (defined in screen 03). 
    If the user has not created any characters the page will guide the user to the create screen via. text. Otherwise there will be a vertically scrolling list
    with all of the characters. Each list item is clickable, and clicking the item will show the characters description. Clicking the description will go back to
    the initial item.

    Screen 03: Create characters:
    The user can use this screen to create characters. More specifically, the user can set the following fields:
    - Name: free text, limit 50 characters
    - Gender: Can select between four options, defined as buttons
    - Origin: Can either submit free text, or use the textfield to search for an already existing location (as fetched from the RickAndMortyApi/Location endpoint, and stored in local database),
    which the user can then click to fill in the textfield.
    - Species: free text, limit 50 characters
    - Description: free text, limit 150 characters
    
    Once the user is satified with their input they can create the character with the "New" button. This will create a new character and store it on the local database. It will also clear all the fields so that the user can make another one. Alternatively the user can click the "Clear all" button to clear all fields without saving the character.

    Screen 04: Episode and season viewer:
    The user can view a scrollable list of all episodes along with the date it was aired and the amount of appearing characters. The season can be 
    selected with the arrows on either side of the season display, and episodes will update accordingly.
    The episode items themselves are clickable, and doing so will display images and names of all the appearing characters for the given episode
    (Characterdata is fetched from the character api endpoint.) 


API Requests
    - Retrofit
        Allows communication with the required API. Used for GET requests to the various endpoints. 

    - custom ApiOutput data class
        Custom data class to pass along the most important information from an API response. Since the exact fields fetched from the API couldnt be speficied
        this was a more practical means of passing along the most vital information throughout the program. The isSuccessful boolean determines whether the
        api call was successful or not, the output is the raw data, and the pages field contained the number of pages the api call retrieved for use in some
        functions. Before I created this class i used the Pair<Boolean, List</*given api datatype*/>>, but this resulted in unclear code (first, second, third)
        when selecting the various elements.  

    - Room
        Used for configuration and creation of local databases. I used two, one for created characters for screen 02(CreatedCharacters) and one for simplified
        locations for use in origin selection (screen 03). 

    - inner classes
        Inner classes were used to create more flexibility in naming conventions and to make the code more readable. For example, in the RetrofitInstance class
        the code is separated into three different inner classes, which is each a unique endpoint in the API. This allowed for more concise naming of class methods
        (for example, instead of a function being named "fetchAllCharactersFromApi(page : Int)", it could look like "characterApi.get(page : Int)).

    - Separating of layers (UI layer, Data layer)
        Used to separate UI code from Logic and data for more structured and distinct sectioning overall.

    - Generic typing for dataclasses(T)
        Used to create data classes which are flexible to multiple types of input depending on the datatype. Used specifically to define datatypes for lists.
        Example: "Image of ApiOutput and ApiResponse dataclasses"

    - mutableStateList
        Used when a list needs to be partially iterated over without remaking the whole thing.

    Frontend
    - Composables
        Used for UI code.

    - LazyColumn og LazyVerticalGrid
        Used for scrolling lists of items.

    - Surface > Button
        Used for onclick functionality. I prefer this to the Button composable because it is more visually flexible.

    - Column / Row
        Used for visual layout of UI-elements. 

    - MutableStateFlows and remember {}
        Used for managing changing state variables in the program. MutableStateFlow is used as standard, which the rememberSaveable being used sparingly in
        specific composables where state handling would be too impractical for integration in ViewModel.
