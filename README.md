# Motus

Simple version of Motus game for Android.
Aim of the game is to guess the word of 6, 7 or 8 letters in 7 tries.
At each tries, the game will tell you how many letters are at the right place and how many are in
the word but not at the right place.

Menu:

* [Architecture](#architecture) : Explains the architecture
* [Data](#data) : Describes the `data` module content
* [Model](#model) : Describes the `model` module content
* [ViewModel](#viewmodel) : Describes the `viewmodel` module content
* [App](#app) : Describes the `app` module content

Note
> The dummy implementation exists to help for create mocks in tests and for inject something in
> previews for compose

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
List of accepted words can be found in files :

* [List of 6 letters words](data/src/main/assets/words/words_6_letters)
* [List of 7 letters words](data/src/main/assets/words/words_7_letters)
* [List of 8 letters words](data/src/main/assets/words/words_8_letters)

## Model

The model contains the work logic:

* It use data to have the words list
* It collect user proposition
* It compares difference with current word and proposed one
* It reacts to game settings change
* It shares to view model information they need for pilot the UI

## ViewModel

Contains the models linked to view to draw:

* They react to model change for the information they manage.
* They may reformat the information received for the UI component
* They react to user interaction to the UI by internal action and/or send action happened to the
  model

## App

Contains the UI show:

* It reacts to view model change to refresh the UI
* It collects user interaction and transfer it to view model to decide what to do
