#Feedback Question

#### Before doing the test, how much time did you think would take you to complete the assignment ?

I estimated the assignment as a 1 day and half effort. 
1/2 day of specking :

- learning how the Youtube DataApi works
- finding and designing the solution 

1 day for the implementation


#### Home time did you take to complete the assignment ?

I spend 1 day and 3/4 for all the works. I take a little more time in the specking part. 

#### With more time, how would you improve your solution ?

I would handle more mistakes coming from the apis to provide a better experience to the user. I would add a presenter to do a better separation between the api and data logic's versus display logic. I would adapte more the AlphabetIndex librairy's to match letter with accent to it. I would try to find the better number for the maximum playlist download number. I would also add mock server to enable tests for api call. 

#### On a scale of 1 to 10 (10 being the hardest), How hard was this assignment ?

It was not very hard juste a little long to be sure to handle well all data.  I will put a 5. 

#### On a scale of 1 to 10 ( 10 being the best), What rating would you give to your achitecture?

I will put a 7, it's will be better to have a presenter ( MVP architecture) to have a better separation between the data logic and the display logic. In this challenge, it was overkill to add a MVP architecture. If the sould evolve, the recycler view should be in a fragment to be more reusable. 


## Decision

I use retrofit with rx to handle more easily the different call tread. I use a recyclerview instead of a listview because it's more faster and powefull, it reuse the views in the list. To help user navigate in the list, I use the library [AlphabetIndex](https://android-arsenal.com/details/1/5277). Even if the list is super big, the library helps the user find easily the playlist he wants. I also add a limit of playlist download of 500 playlists to avoid blocking the interaction of the user. When a user scroll down to the end of the list an other 500 playlists will be download. 


The Google key is in the gradle file.
The Facebook id is in the string file. 