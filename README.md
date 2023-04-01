## This app was created as a part of hiring process for one company

### Below are details about what we're looking to review and discuss.
## The App: Foosball Matches
#### We love foosball. Most arguments over who is the best are settled on the table, but nobody can remember the scores from weeks ago. We need a way totrack the results and for everyone to see who is the current king. Create a simple application that tracks foosball ratings. You should be able to input historical results, update results, and retrieve current rankings. Below are some sample historical cores to get you started.
##### Person,Score,Person,Score
Amos,4,Diego,5\
Amos,1,Diego,5\
Amos,2,Diego,5\
Amos,0,Diego,5\
Amos,6,Diego,5\
Amos,5,Diego,2\
Amos,4,Diego,0\
Joel,4,Diego,5\
Tim,4,Amos,5\
Tim,5,Amos,2\
Amos,3,Tim,5\
Amos,5,Tim,3\
Amos,5,Joel,4\
Joel,5,Tim,2

## Other details:
#### 1) Implement rankings view which lists users in order of their ranking (by number of games played, games won)
#### 2) In terms of the king of the game calculation the only win/didn't win matters
#### 3) Sort players by count of games, or count of wins, using rx, after that the order does not matter
#### 4) The candidate could decide a dialog could be used or a separate fragment in the content to show the list of players
#### 5) The implementation should include use of Fragments, dependency injection, and a consistent software architectural pattern. We extensively useDagger, MVVM using Jetpack, and RxJava2, and strongly recommend demonstrating them in the solution. The solution should demonstrate understanding of the thread handling, reactive patterns, the Android lifecycle, and communication between UI components including Activities, and Fragments.
#### 6) A robust data store is not required, so an in-memory collection may be used instead of a server api or a database implementation. Please add a brief comment in the code explaining the boundary where a real store may be used instead.
