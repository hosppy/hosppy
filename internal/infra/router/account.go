package router

import (
	"net/http"

	"github.com/hosppy/oxcoding/internal/domain/service"
	"github.com/hosppy/oxcoding/internal/infra/api"
	"github.com/labstack/echo/v4"
)

type AccountRouter struct {
	accountService *service.AccountService
}

func NewAccountRouter(accountService *service.AccountService) *AccountRouter {
	return &AccountRouter{accountService}
}

func (r *AccountRouter) Register(c echo.Context) error {
	form := api.RegisterForm{}
	if err := c.Bind(&form); err != nil {
		return err
	}
	account, err := r.accountService.Register(form.ToCreateAccountCommand())
	if err != nil {
		return err
	}

	return c.JSON(http.StatusCreated, api.AccountCreateResp{
		ID:          account.ID,
		Name:        account.Name,
		Email:       account.Email,
		Active:      account.Active,
		CreatedAt:   account.CreatedAt,
		PhoneNumber: account.PhoneNumber,
		AvatarURL:   account.AvatarURL,
	})
}
