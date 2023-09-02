package passwordhash

import (
	"golang.org/x/crypto/bcrypt"
	"regexp"
)

var re = regexp.MustCompile("{.*?}")

func Hash(password string) (string, error) {
	bytes, err := bcrypt.GenerateFromPassword([]byte(password), 14)
	return "{bcrypt}" + string(bytes), err
}

func Check(password, hash string) bool {
	hash = re.ReplaceAllString(hash, "")

	err := bcrypt.CompareHashAndPassword([]byte(hash), []byte(password))
	return err == nil
}
