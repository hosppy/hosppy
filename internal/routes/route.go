package routes

import (
	"encoding/gob"
	"github.com/gorilla/sessions"
	"github.com/labstack/echo-contrib/session"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	slogecho "github.com/samber/slog-echo"
	"log/slog"
	"net/http"
)

func Register(e *echo.Echo) {
	e.Use(slogecho.New(slog.Default()))
	e.Pre(middleware.RemoveTrailingSlashWithConfig(middleware.TrailingSlashConfig{RedirectCode: http.StatusMovedPermanently}))
	e.Use(middleware.Recover())
	e.Use(session.Middleware(sessions.NewCookieStore([]byte("secret"))))

	e.GET("/accounts", GetAccounts)
	e.GET("/profile", GetProfile)

	gob.Register(map[string]interface{}{})
	e.POST("/authenticate", Authenticate)
}
