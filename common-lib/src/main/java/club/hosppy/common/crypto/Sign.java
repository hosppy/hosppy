package club.hosppy.common.crypto;

import club.hosppy.common.api.ResultCode;
import club.hosppy.common.error.ServiceException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Sign {

    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_USER_ID = "userId";

    /**
     * a day
     */
    public static final long DURATION_MILLIS = 1000 * 60 * 60 * 24;

    private static Algorithm algorithm;

    public static String generateEmailConfirmationToken(String userId, String email, String secret) {
        instanceAlgorithm(secret);
        return JWT.create()
                .withClaim(CLAIM_EMAIL, email)
                .withClaim(CLAIM_USER_ID, userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2)))
                .sign(algorithm);
    }

    public static String generateSessionToken(String userId, String secret) {
        if (StringUtils.isEmpty(secret)) {
            throw new ServiceException(ResultCode.NO_SIGNING_TOKEN);
        }
        instanceAlgorithm(secret);
        return JWT.create()
                .withClaim(CLAIM_USER_ID, userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + DURATION_MILLIS))
                .sign(algorithm);
    }

    public static DecodedJWT verify(String token, String secret) {
        instanceAlgorithm(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    private static void instanceAlgorithm(String secret) {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC512(secret);
        }
    }
}
