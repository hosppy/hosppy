package router

import (
	"github.com/hosppy/oxcoding/internal/domain/service"
)

type AccountRouter struct {
	accountService *service.AccountService
}

func NewAccountRouter(accountService *service.AccountService) *AccountRouter {
	return &AccountRouter{accountService}
}
