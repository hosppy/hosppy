package api

import "github.com/hosppy/oxcoding/internal/domain/model"

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

func (form *RegisterForm) ToAccountCreateCommand() *model.AccountCreateCommand {
	return &model.AccountCreateCommand{
		Name:     form.Name,
		Email:    form.Email,
		Password: form.Password,
	}
}
