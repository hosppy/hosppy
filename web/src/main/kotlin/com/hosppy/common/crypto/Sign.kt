package com.hosppy.common.crypto

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.time.Instant
import java.time.temporal.ChronoUnit

class Sign(secret: String) {

    private var algorithm = Algorithm.HMAC512(secret)

    fun generateEmailConfirmationToken(userId: Int, email: String): String {
        val expiresAt = Instant.now().plus(1, ChronoUnit.DAYS)
        return JWT.create()
            .withClaim(CLAIM_EMAIL, email)
            .withClaim(CLAIM_USER_ID, userId)
            .withExpiresAt(expiresAt)
            .sign(algorithm)
    }

    fun verify(token: String): DecodedJWT {
        val verifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    companion object {
        const val CLAIM_USER_ID = "userId"
        const val CLAIM_EMAIL = "email"
    }
}