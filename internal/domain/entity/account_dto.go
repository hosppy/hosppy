package entity

import (
	"golang.org/x/crypto/bcrypt"
	"time"
)

type AccountDTO struct {
	Email    string
	Name     string
	Password string
}

func (a *AccountDTO) HashPassword() string {
	bytes, err := bcrypt.GenerateFromPassword([]byte(a.Password), 14)
	if err != nil {
		panic(err)
	}
	return "{bcrypt}" + string(bytes)
}

func (a *AccountDTO) ToModel() *Account {
	return &Account{
		Email:        a.Email,
		Name:         a.Name,
		Active:       false,
		CreatedAt:    time.Time{},
		PasswordHash: a.HashPassword(),
	}
}
