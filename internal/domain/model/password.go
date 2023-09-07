package model

import (
	"golang.org/x/crypto/bcrypt"
	"strings"
)

type Password struct {
	Raw    string
	Hashed string
}

func NewPassword(password string) *Password {
	return &Password{Raw: password}
}

func NewHashedPassword(password string) (*Password, error) {
	bytes, err := bcrypt.GenerateFromPassword([]byte(password), 14)
	if err != nil {
		return nil, err
	}
	return &Password{Raw: password, Hashed: "{bcrypt}" + string(bytes)}, nil
}

func (p *Password) IsValid() bool {
	return p.Check(p.Raw)
}

func (p *Password) Check(password string) bool {
	index := strings.Index(password, "}")
	passwordHash := p.Hashed[index:]
	err := bcrypt.CompareHashAndPassword([]byte(passwordHash), []byte(p.Raw))
	return err == nil
}
