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

    ## TODO
    ### Critical 
    Test backend for screen04
    Implement UI for screen 04
    Implement interactivity for screen 04

    ### Important
    Polish UI for screen 02 and 01
    
    ### Later
    General refactor and cleaning up the codebase. Optimize.
    Make UI as pretty as possible.
    
    ## Screen 4 functionality
    On screen 4 you will be able to see every episode of Rick and morty. They will be
    divided into seasons. If you click on the episode you can see all of the episodes
    appearing characters (image and name). This info fetched by an API call that only
    calls if you click the episode in particular. Reason for this is setting a database
    framework for preloading all characters is too much work for now, and would require
    the creation of a whole new database system. It would also sort of make screen01 obsolete.

    