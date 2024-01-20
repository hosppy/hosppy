package web

import (
	"context"
	"github.com/gorilla/sessions"
	"github.com/hosppy/oxcoding/internal/config"
	"github.com/labstack/echo-contrib/session"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	slogecho "github.com/samber/slog-echo"
	"go.uber.org/fx"
	"log/slog"
	"net/http"
)

type Routes struct {
	fx.In

	*AccountRouter
	*AuthRouter
}

func New(lc fx.Lifecycle, routes Routes, cfg *config.Config, logger *slog.Logger) *echo.Echo {
	e := echo.New()
	e.HideBanner = true

	e.Use(slogecho.New(logger))
	e.Pre(middleware.RemoveTrailingSlashWithConfig(middleware.TrailingSlashConfig{RedirectCode: http.StatusMovedPermanently}))
	e.Use(middleware.Recover())
	e.Use(session.Middleware(sessions.NewCookieStore([]byte(cfg.SessionSecret))))

	e.POST("/authenticate", routes.AuthRouter.Authenticate)
	e.POST("/accounts", routes.AccountRouter.Register)

	lc.Append(fx.Hook{
		OnStart: func(ctx context.Context) error {
			if err := e.Start(cfg.Address); err != nil {
				return err
			}
			return nil
		},
	})
	return e
}
