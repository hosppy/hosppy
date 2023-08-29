package app

import (
	"log/slog"
	"os"

	"github.com/hosppy/oxcoding/internal/domain/service"
	"github.com/hosppy/oxcoding/internal/infra/repository/postgres"
	"github.com/hosppy/oxcoding/internal/infra/router"
	"github.com/joho/godotenv"
)

type App struct {
	accountRouter *router.AccountRouter
}

func preInit() {
	logger := slog.New(slog.NewJSONHandler(os.Stdout, nil))
	slog.SetDefault(logger)

	if err := godotenv.Load(); err != nil {
		slog.Info("Cannot found .env file")
	}
}

func New() *App {
	preInit()

	db := postgres.New()
	accountRepository := db.NewAccountRepository()
	accountService := service.NewAccountService(accountRepository)
	accountRouter := router.NewAccountRouter(accountService)

	return &App{accountRouter: accountRouter}
}

func (a *App) Start() {
	e := router.New()

	e.POST("/authenticate", a.accountRouter.Authenticate)

	address, ok := os.LookupEnv("ADDRESS")
	if !ok {
		address = ":8080"
	}
	if err := e.Start(address); err != nil {
		slog.Error("Start server failed", err)
		return
	}
}
