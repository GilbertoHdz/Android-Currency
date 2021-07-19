# Currency APP

A conversion currency Application that can convert Foreign Currency using this free API: [Fixer](https://fixer.io/documentation)

## Getting Started

1. Clone the project to your local machine.
2. Open the project using Android Studio.

### Installation

Download the project and Run on physical device or virtual device with API level 23 / Android 6 and above.

## Testing

Run on terminal `./gradlew test` in order to run all unit test in parallel

### Break Down Tests

```
1. Unit test
        Using `TrampolineSchedulerProvider` as a Fake implementation of the {@link SchedulerProvider} interface.
            Uses a trampoline scheduler for all task types to allow easy testing of Rx Subscriptions.

        Testing interactors as a use case and testing each type of response
        Testing view models with each `LiveData` result as a progress, errors and display values
```

## Project Instructions features & libraries
    1. Creating `interactor` module to provide the use cases that will interact with the API.
    2. Creating `api` module to register the ApiServices and implement each endpoint with retrofit.
    3. Creating `models` module to creating the models with contains the objects provided by the API request.
    4. Creating `utils` module used to implement common and basic implementations in the whole project like extension function or helper creational classes.
    5. Creating `component` used to implement common android resources like colors, styles or extension function for android widgets.
    6. Creating `features` section to implement feature sections with specific use cases.
    7. Creating `:feature:rates` to display all rates exchange conversion
    7. Creating `:feature:symbols` to display all supported currency symbols with code names

## Built With

* [RxKotlin](https://github.com/ReactiveX/RxKotlin) - A library for composing asynchronous and event-based programs by using observable sequences.
* [Jetpack](https://developer.android.com/jetpack) Using `ViewModel` to manage the state and `Lifecycle` to manage the lifecycle
* [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
* [OkHttp](https://square.github.io/okhttp/) - Using a modern HTTP to handle the interceptors and configure the providers.
* [Dagger Hilt](https://dagger.dev/) - Using dagger as a dependency injection to access to the pool object in runtime.
* [Mockk](https://mockk.io/) - A kotlin mocking library based on Mockito used for unit test.

## Meta

Gilberto Hernández G. – [gilbertohdz.dev](https://gilbertohdz.dev/) - [@_GilbertoHdz_](https://twitter.com/_GilbertoHdz_) – ghernandez.9002@gmail.com

## License
[MIT](https://choosealicense.com/licenses/mit/)