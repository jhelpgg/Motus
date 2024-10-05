# Motus

Simple version of Motus game for Android.
Aim of the game is to guess the word of 6, 7 or 8 letters in 7 tries.
At each tries, the game will tell you how many letters are at the right place and how many are in
the word but not at the right place.

## Architecture

The game is composed of several module :

- `app` : containing the UI
- `viewmodel` : containing the game view models
- `model` : containing the main model
- `data` : containing the data source
- `injector` : containing the dependency injection system
- `tools` : containing the tools used by the other modules
- `common` : containing the common structures used by the other modules

Their interaction can be schematized as follow :
![Interaction between modules](doc/ModulesInteraction.png)

## Data

The words are stored in files depends on their length.
The files are located in the `assets` folder of the `data` module.
The data role is to load in memory the word list depends on the number of letters and provide one of
the worlds
