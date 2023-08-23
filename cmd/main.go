package main

import (
	"github.com/hosppy/oxcoding/internal/models"
	"github.com/hosppy/oxcoding/internal/routes"
	"github.com/joho/godotenv"
	"github.com/labstack/echo/v4"
	"log/slog"
	"os"
)

func main() {
	logger := slog.New(slog.NewJSONHandler(os.Stdout, nil))
	slog.SetDefault(logger)

	if err := godotenv.Load(); err != nil {
		slog.Info("Cannot found .env file")
	}

	models.InitDatabase()
	e := echo.New()
	routes.Register(e)

	if err := e.Start(":8080"); err != nil {
		slog.Error("Start server failed", err)
		return
	}
}
