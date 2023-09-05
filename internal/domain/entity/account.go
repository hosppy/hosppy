package entity

import (
	"golang.org/x/crypto/bcrypt"
	"regexp"
	"time"
)

type Account struct {
	ID           int
	Email        string
	Name         string
	PhoneNumber  string
	Active       bool
	CreatedAt    time.Time
	PasswordHash string
	AvatarURL    string
}

func (a *Account) CheckPassword(password string) bool {
	var re = regexp.MustCompile("{.*?}")
	hash := re.ReplaceAllString(a.PasswordHash, "")

	err := bcrypt.CompareHashAndPassword([]byte(hash), []byte(password))
	return err == nil
}
