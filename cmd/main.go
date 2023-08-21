package main

import (
	"github.com/hosppy/oxcoding/internal/models"
	"github.com/hosppy/oxcoding/internal/routes"
	"github.com/joho/godotenv"
	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	slogecho "github.com/samber/slog-echo"
	"log/slog"
	"os"
)

func main() {
	logger := slog.New(slog.NewJSONHandler(os.Stdout, nil))
	slog.SetDefault(logger)
	err := godotenv.Load()
	if err != nil {
		slog.Info("Cannot found .env file")
	}
	models.InitDatabase()
	e := echo.New()
	e.Use(slogecho.New(logger))
	e.Use(middleware.Recover())
	routes.Register(e)
	e.Logger.Fatal(e.Start(":8080"))
}
