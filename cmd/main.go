package main

import (
	"context"
	"github.com/hosppy/oxcoding/internal/config"
	"github.com/hosppy/oxcoding/internal/domain/repository"
	"github.com/hosppy/oxcoding/internal/domain/service"
	"github.com/hosppy/oxcoding/internal/infra/mail"
	"github.com/hosppy/oxcoding/internal/infra/repository/postgres"
	"github.com/hosppy/oxcoding/internal/logger"
	"github.com/hosppy/oxcoding/internal/web"
	"go.uber.org/fx"
	"log/slog"
)

func main() {
	app := fx.New(
		fx.Provide(
			logger.New,
			config.New,
			postgres.NewClient,
			fx.Annotate(postgres.NewAccountRepository, fx.As(new(repository.AccountRepository))),
			fx.Annotate(mail.NewClient, fx.As(new(service.MailService))),
			service.NewAccountService,
			web.NewAccountRouter,
		),
		fx.Invoke(web.New),
	)

	if err := app.Start(context.Background()); err != nil {
		slog.Error("start application failed", err)
	}
}
