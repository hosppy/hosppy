package auth

import (
	"context"
	"github.com/hosppy/oxcoding/ent"
	"github.com/hosppy/oxcoding/ent/account"
	"github.com/hosppy/oxcoding/internal/models"
	"golang.org/x/crypto/bcrypt"
	"regexp"
)

var re = regexp.MustCompile("{.*?}")

func HashPassword(password string) (string, error) {
	bytes, err := bcrypt.GenerateFromPassword([]byte(password), 14)
	return "{bcrypt}" + string(bytes), err
}

func CheckPasswordHash(password, hash string) bool {
	hash = re.ReplaceAllString(hash, "")

	err := bcrypt.CompareHashAndPassword([]byte(hash), []byte(password))
	return err == nil
}

func UsernamePasswordAuthenticate(username string, password string) (*ent.Account, bool) {
	foundAccount := models.Client.Account.
		Query().
		Where(account.Email(username)).
		FirstX(context.Background())

	if foundAccount != nil && CheckPasswordHash(password, foundAccount.PasswordHash) {
		return foundAccount, true
	}
	return nil, false
}
