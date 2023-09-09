package model

import (
	"time"
)

type Account struct {
	ID          int
	Email       string
	Name        string
	PhoneNumber string
	Active      bool
	CreatedAt   time.Time
	AvatarURL   string
	Password    *Password
}

type AccountCreateCommand struct {
	Name     string
	Email    string
	Password string
}
