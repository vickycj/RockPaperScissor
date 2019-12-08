# Rock Paper Scissor

Architecture Design Pattern - MVVM (Model View ViewModel) 

Tech Stack
-----------

- Programming language    - **Kotlin**
- NetWork API             - **RetroFit**
- Dependancy Injection    - **Dagger 2 for Kotlin**
- Offline Storing         - **Room ORM (Arch components)**
- Streams                 - **RxAndroid and LiveData**
- Architecture Components - **ViewModel, LiveData, Android JetPAck Components**
- Testing Framework       - **Junit, Mockito , Expresso, AndroidJunit4**


App Structure
-------------
App module - Application UI Logic , designs and navigation

GameCore Module - Game core logic, exported the app module with abstraction

The application is divided into two modules. The application/Presentation layer is where the UI design is implemented,
whereas the gamecore module is android library which contains the game core logic. This way we can abstract/extract the game logic
and use it in other apps by adding it as dependancy.