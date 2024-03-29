package router

import (
	"github.com/gorilla/sessions"
	"github.com/hosppy/oxcoding/internal/infra/api"
	"github.com/labstack/echo-contrib/session"
	"github.com/labstack/echo/v4"
	"net/http"
)

const SessionKey = "session"

func (r *AccountRouter) Authenticate(c echo.Context) error {
	form := new(api.LoginForm)
	if err := c.Bind(form); err != nil {
		return err
	}
	account, ok := r.accountService.UsernamePasswordAuthenticate(form.Email, form.Password)
	if !ok {
		return c.JSON(http.StatusBadRequest, api.BadCredentialStatus)
	}
	sess, err := session.Get(SessionKey, c)
	if err != nil {
		return err
	}
	sess.Options = &sessions.Options{
		Path:   "/",
		MaxAge: 86400 * 7,
	}
	sess.Values["accountId"] = account.ID
	if err = sess.Save(c.Request(), c.Response()); err != nil {
		return err
	}
	return nil
}
