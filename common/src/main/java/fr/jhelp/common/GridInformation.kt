package fr.jhelp.common

/**
 * Read-only version of [Grid] used for transfer [Grid] description actual status to UI
 */
class GridInformation(val numberLetters: NumberLetters, val words: Array<WordInformation>)
