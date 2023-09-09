package main

import (
	"context"
	"github.com/hosppy/oxcoding/internal/config"
	"github.com/hosppy/oxcoding/internal/domain/repository"
	"github.com/hosppy/oxcoding/internal/domain/service"
	"github.com/hosppy/oxcoding/internal/infra/mail"
	"github.com/hosppy/oxcoding/internal/infra/repository/postgres"
	"github.com/hosppy/oxcoding/internal/infra/router"
	"github.com/hosppy/oxcoding/internal/logger"
	"go.uber.org/fx"
	"log/slog"
)

func main() {
	app := fx.New(
		fx.Provide(
			logger.New,
			config.New,
			fx.Annotate(mail.NewClient, fx.As(new(service.MailService))),
			postgres.NewClient,
			fx.Annotate(
				postgres.NewAccountRepository,
				fx.As(new(repository.AccountRepository)),
			),
			service.NewAccountService,
			router.NewAccountRouter,
		),
		fx.Invoke(router.New),
	)

	if err := app.Start(context.Background()); err != nil {
		slog.Error("start application failed", err)
	}
}
