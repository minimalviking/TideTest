# README

Due to time limitations I have made several compromises listed below:

* Not handling next pages of results from the API
* No pull to refresh
* Ugly handling of activity recreation: if activity gets recreated during the request, the request is resent (could have been solved by using a retained fragment or a singleton)
* Not handling location updates - location is retrieved when the app starts and then isn't updated
* Lack of tests
* Using MVP architecture only in HomeActivity - fragments' logic is so simple that it would be an overkill to add MVP there
