# gitrank - Git Repository Rank

## About
This application fetches all GitHub repositories ranked by number of stars and displays them in an infinite scrolling list.

## Architecture and key decisions
1. The app was developed with MVVM architecture in mind, which is widely used for its decoupled properties and for the ease of maintenance/scalability.
2. Dependency injection with Koin was used as it's very lightweight, has a quick setup and eases the testing process.
3. Repositories and associated data were fetched with the use of Retrofit as it provides a quick way to do so and it's widely used today for REST API access.
4. The API utilized to provide the repositories' data was GitHub's API.
5. Pagination was used, as it's supported by GitHub's API and helps with load speed.
6. Infinite scrolling was implemented in the RecyclerView, so when the last item of the list is displayed, new repositories are loaded.
7. In order to load the repositories' owner avatar images, the library Picasso was utilized and caching was enabled so the images are reused without the need of downloading them again.
8. Unit tests were created with the help of Mockito and some other helper libraries such as Google's Truth and Mockito-Kotlin.