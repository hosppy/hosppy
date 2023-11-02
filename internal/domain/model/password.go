package model

import (
	"strings"

	"golang.org/x/crypto/bcrypt"
)

type Password struct {
	Raw    string
	Hashed string
}

func NewPassword(password string) (*Password, error) {
	bytes, err := bcrypt.GenerateFromPassword([]byte(password), 14)
	if err != nil {
		return nil, err
	}
	return &Password{Raw: password, Hashed: "{bcrypt}" + string(bytes)}, nil
}

func (p *Password) Check(password string) bool {
	index := strings.Index(p.Hashed, "}")
	index = max(index, 0)
	passwordHash := p.Hashed[index:]
	err := bcrypt.CompareHashAndPassword([]byte(passwordHash), []byte(password))
	return err == nil
}
