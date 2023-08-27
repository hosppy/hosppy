package entity

import "time"

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
