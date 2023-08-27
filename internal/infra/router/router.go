package router

import (
	"github.com/gorilla/sessions"
	"github.com/labstack/echo-contrib/session"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	slogecho "github.com/samber/slog-echo"
	"log/slog"
	"net/http"
	"os"
)

func New() *echo.Echo {
	e := echo.New()

	e.Use(slogecho.New(slog.Default()))
	e.Pre(middleware.RemoveTrailingSlashWithConfig(middleware.TrailingSlashConfig{RedirectCode: http.StatusMovedPermanently}))
	e.Use(middleware.Recover())
	e.Use(session.Middleware(sessions.NewCookieStore([]byte(os.Getenv("SESSION_SECRET")))))

	return e
}
