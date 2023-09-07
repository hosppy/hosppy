package api

import "time"

type CreateAccountResp struct {
	ID          int       `json:"id"`
	Name        string    `json:"name"`
	Email       string    `json:"email"`
	Active      bool      `json:"active"`
	CreatedAt   time.Time `json:"created_at"`
	PhoneNumber string    `json:"phone_number"`
	AvatarURL   string    `json:"avatar_url"`
}
