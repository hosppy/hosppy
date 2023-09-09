package sign

import (
	"fmt"
	"github.com/golang-jwt/jwt"
	"time"
)

func GenerateEmailConfirmationToken(accountId int, email, secret string) (string, error) {
	token := jwt.NewWithClaims(jwt.SigningMethodHS512, jwt.MapClaims{
		"email":     email,
		"accountId": accountId,
		"exp":       time.Now().Add(24 * time.Hour),
	})
	return token.SignedString([]byte(secret))
}

func Verify(tokenString, secret string) (jwt.MapClaims, error) {
	token, err := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("unexpected signing method: %v", token.Header["alg"])
		}

		return []byte(secret), nil
	})
	if err != nil {
		return nil, err
	}
	if claims, ok := token.Claims.(jwt.MapClaims); ok && token.Valid {
		return claims, nil
	}
	return nil, fmt.Errorf("invalid token")
}
