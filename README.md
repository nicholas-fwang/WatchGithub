# WatchGithub
An android MVP structure APP with RxJava, Dagger2 and GitHub v3 API.

# References

https://github.com/googlesamples/android-architecture
<pre>
- References to branch to todo-mvp, dev-todo-mvp-rxjava and todo-mvp-dagger
- Concept of data flow through cache, local and remote.
</pre>
<br />
https://github.com/frogermcs/GithubClient
<pre>
- Use GitHub API with retrofit.
- Concept of dagger @Subcomponent.
</pre>

# Features
- The sample of Android MVP architecture using Dagger2 and RxJava.
- The GitHub API requests are restricted to 60 per a hour.
- Use 2 POJO model(User, Repository) classes with each 2 storages(local-remote & cache-remote).

# Demo Video
[![ScreenShot](https://github.com/fisache/fisache/blob/master/watchgithub_image/watchGithubVideo.png)](https://youtu.be/uVTSfSXajT4) <br />
with Mobizen

# Dependencies

Android Support Packages <br />
<a href="https://github.com/google/dagger">Dagger2</a> <br />
<a href="https://github.com/ReactiveX/RxJava">RxJava</a> <br />
<a href="https://github.com/ReactiveX/RxAndroid">RxAndroid</a> <br />
<a href="https://github.com/JakeWharton/RxBinding">RxBinding</a> <br />
<a href="https://github.com/JakeWharton/butterknife">butterknife</a> <br />

<a href="https://github.com/square/picasso">picasso</a> <br />
<a href="https://github.com/square/retrofit">retrofit</a> <br />
<a href="https://github.com/square/sqlbrite">sqlbrite</a> <br />

<a href="https://github.com/mockito/mockito">mockito</a> <br />
<a href="https://developer.android.com/training/testing/ui-testing/espresso-testing.html">espresso</a> <br />

adapter-rxjava, converter-gson, timber and guava <br />

# Architecture
- WatchGithub MVP Architecture <br />
![ScreenShot](https://github.com/fisache/fisache/blob/master/watchgithub_image/WatchGithub_MVP_Architecture.png)

- Dagger Object Graph
![ScreenShot](https://github.com/fisache/fisache/blob/master/watchgithub_image/WatchGithub_objectgraph.png)

# Activity
![ScreenShot](https://github.com/fisache/fisache/blob/master/watchgithub_image/user.png)
<pre>
[Local & Remote Storage]

Create  : - User can add new Github User clicking floating button.
          if user is not valid, snackbar is shown with warning.
          
Read    : - When app launched, all of users saved in local storage(SQLite) are updated with remote storage(GitHub API).
          - Aleary launched, users data is brought from local storage
          - Users are filterd into All User, Popular(have followers over 150), and User or Organization.
          - Also, you can search as typing the user login id.

Update/Delete  : - You can write user description(To find out who). It will place at the hint 'Go to setting..'.
                  The way writing is to click the set icon (looks like gear)
                  then, detail activity is launched, you can
                  update user's description
                  also delete user in local storage.
</pre>

![ScreenShot](https://github.com/fisache/fisache/blob/master/watchgithub_image/repository.png)

<pre>
[Cache & Remote Storage]

Create :  - User's repositories is cached by replacement policy of <a href="https://en.wikipedia.org/wiki/Cache_algorithms#LRU">LRU</a>.
          - When The capacity of cache is sufficient, 
          cache will save repositories data with user login id and requested last page number

Read :   - When view user's repositories, first of all try to read from cache.
          - If not placed in cache, request to GitHub server and save to cache.
          - When You scroll down over viewed repositories, request next repositories to GitHub server and save to cache.
          - Filtering is similar to User filter (ALL, Popular(starred over 500), 
          ORIGIN or FORK and Recently pushed(before 3days).

Update :  - Update is performed when requests repositories when the repositories are not placed in cache.

Delete :  - Cache is implemented with LinkedHashMap of Java, and follows the replacement policy of <a href="https://en.wikipedia.org/wiki/Cache_algorithms#LRU">LRU</a>.
          So, <a href="https://en.wikipedia.org/wiki/Cache_algorithms#LRU">Least Rencently Used</a> data is removed when save new data to cache in full capacity.
</pre>
# Articles
1. <a href="https://fisache.github.io/Introduction-WatchGithub/">Introduction to `WatchGithub`</a> <br />
2. Construct MVP pattern with `Dagger2` <br />
3. Use `sqlbrite` using `RxJava` <br />
4. Use `retrofit` with `RxJava` <br />
5. Implement <a href="https://en.wikipedia.org/wiki/Cache_algorithms#LRU">LRU Cache</a> with `LinkedHashMap` and `RxJava` <br />
6. Implement filter with `RxJava` and `sqlbrite` <br />
7. Implement filter with `RxJava` and Cache <br />
8. Implement search with `RxBinding` and `sqlbrite` <br />
9. Implement scroll down when viewed data are end using `retrofit` and `recyclerView onScroll` <br />
9. Connect to local data(`sqlbrite`) with remote data(`retrofit`) using `RxJava` <br />
10. Connect to cache with remote data(`retrofit`) using `RxJava` <br />
11. TODO : androidTest and Test with espresso, junit and mockito

# Test
TODO
