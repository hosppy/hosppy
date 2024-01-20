package api

import (
	"github.com/hosppy/oxcoding/internal/domain/command"
)

type LoginForm struct {
	Email      string `json:"email"`
	Password   string `json:"password"`
	RememberMe bool   `json:"remember_me"`
}

type RegisterForm struct {
	Name     string `json:"name"`
	Email    string `json:"email"`
	Password string `json:"password"`
}

func (form *RegisterForm) ToCreateAccountCommand() *command.CreateAccountCommand {
	return &command.CreateAccountCommand{
		Name:     form.Name,
		Email:    form.Email,
		Password: form.Password,
	}
}
