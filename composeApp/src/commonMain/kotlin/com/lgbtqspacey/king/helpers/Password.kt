package com.lgbtqspacey.king.helpers

/**
 * Generates a random password using `letters`, `numbers` and `symbols`
 *
 * @param size the password length. Defaults to 20.
 */
fun generatePassword(size: Int = 20): String {
    val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9') + "!@#$%&*()-_+=".asSequence()
    return List(size) { alphabet.random() }.joinToString("")
}
