package router

import (
	"github.com/hosppy/oxcoding/internal/domain/service"
	"github.com/hosppy/oxcoding/internal/infra/api"
	"github.com/labstack/echo/v4"
	"net/http"
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
	r.accountService.Create(form.ToAccount())
	c.Response().WriteHeader(http.StatusCreated)
	return nil
}
