package club.hosppy.common.crypto

import club.hosppy.common.api.ResultCode
import club.hosppy.common.error.ServiceException
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.apache.commons.lang3.StringUtils
import java.util.*
import java.util.concurrent.TimeUnit

object Sign {
    const val CLAIM_EMAIL = "email"
    const val CLAIM_USER_ID = "userId"

    /**
     * a day
     */
    private const val DURATION_MILLIS = (1000 * 60 * 60 * 24).toLong()
    private var algorithm: Algorithm? = null

    @JvmStatic
    fun generateEmailConfirmationToken(userId: String?, email: String?, secret: String): String {
        instanceAlgorithm(secret)
        return JWT.create()
            .withClaim(CLAIM_EMAIL, email)
            .withClaim(CLAIM_USER_ID, userId)
            .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2)))
            .sign(algorithm)
    }

    fun generateSessionToken(userId: String?, secret: String): String {
        if (StringUtils.isEmpty(secret)) {
            throw ServiceException(ResultCode.NO_SIGNING_TOKEN)
        }
        instanceAlgorithm(secret)
        return JWT.create()
            .withClaim(CLAIM_USER_ID, userId)
            .withExpiresAt(Date(System.currentTimeMillis() + DURATION_MILLIS))
            .sign(algorithm)
    }

    @JvmStatic
    fun verify(token: String?, secret: String): DecodedJWT {
        instanceAlgorithm(secret)
        val verifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    private fun instanceAlgorithm(secret: String) {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC512(secret)
        }
    }
}