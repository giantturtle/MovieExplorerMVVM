# MovieExplorerMVVM

This folder contains the source code for the simple open source android client for TheMovieDatabase API, built using (Google recomended) MVVM app architecture design pattern, reactive programming with LiveData, repository pattern, Room Persistence Library.
Features: Pagination, caching search results and option to add bookmarks.
Code is packaged by feature. The "data" package contains local database model classes( "database" package ), web service ("webservice" package) wich models the structure of GitHub api response and repository ("DataRepository.java") which is used as a single source of truth.
"UI" package contains VIEWS and VIEW MODELS code for each screen in the app ("mainscreen", "savedscreen", "detailsscreen" packages). VIEW MODEL don't hold any references to the VIEW  or the MODEL classes in data package so the code is modular and it is easy to change the screens and add features.
LiveData is used to communicate between app layers. Any changes in the MODEL layer are propagated via instances of LiveData subclasses to VIEW MODEL and then to the VIEW layer.

In Utils class there is Configuretion interface in wich you can configure default search term, results per page and for how long should results be kept in local cache, before trying to update with new ones.
There is also MyApplication class which is used by LeakCanary for memory leak detection (only in debug version of the app).

### Getting Started
App has basic functionality such as searching movies, viewing details, adding bookmarksis, but it is still unfinished, so feel free to contribute, refactor, whatever...
Check out branch `master` to start.
