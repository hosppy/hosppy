package api

import "github.com/hosppy/oxcoding/internal/domain/entity"

type LoginForm struct {
	Email      string `json:"email"`
	Password   string `json:"password"`
	RememberMe bool   `json:"remember_me"`
}

type RegisterForm struct {
	Password string `json:"password"`
	Email    string `json:"email"`
	Name     string `json:"name"`
}

func (form RegisterForm) ToAccount() *entity.Account {
	return &entity.Account{
		Email:    form.Email,
		Password: form.Password,
		Name:     form.Name,
	}
}
