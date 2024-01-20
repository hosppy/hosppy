package web

import (
	"github.com/gorilla/sessions"
	"github.com/hosppy/oxcoding/internal/domain/service"
	"github.com/hosppy/oxcoding/internal/infra/api"
	"github.com/labstack/echo-contrib/session"
	"github.com/labstack/echo/v4"
	"net/http"
	"time"
)

const SessionKey = "session"

type AuthRouter struct {
	accountService *service.AccountService
}

func NewAuthRouter(accountService *service.AccountService) *AuthRouter {
	return &AuthRouter{accountService}
}

func (r *AuthRouter) Authenticate(c echo.Context) error {
	form := new(api.LoginForm)
	if err := c.Bind(form); err != nil {
		return err
	}
	account, ok := r.accountService.UsernamePasswordAuthenticate(form.Email, form.Password)
	if !ok {
		return c.JSON(http.StatusBadRequest, api.ResponseBadCredential)
	}
	sess, err := session.Get(SessionKey, c)
	if err != nil {
		return err
	}
	sess.Options = &sessions.Options{
		Path:   "/",
		MaxAge: int(time.Hour) * 24 * 7,
	}
	sess.Values["accountId"] = account.ID
	if err = sess.Save(c.Request(), c.Response()); err != nil {
		return err
	}
	return nil
}
