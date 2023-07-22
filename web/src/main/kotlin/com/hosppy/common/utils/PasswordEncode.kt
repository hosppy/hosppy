package com.hosppy.common.utils

import at.favre.lib.crypto.bcrypt.BCrypt

fun encodePassword(password: String): String {
    return "{bcrypt}" + BCrypt.withDefaults().hashToString(12, password.toCharArray())
}