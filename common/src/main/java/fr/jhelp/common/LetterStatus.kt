package fr.jhelp.common

/**
 * Status of a letter in word proposal
 */
enum class LetterStatus
{
    EMPTY, // Letter not yet proposed
    PROPOSED, // Letter proposed
    WELL_PLACED, // Letter well placed
    MISPLACED, // Letter misplaced
    NOT_PRESENT // Letter not present
}