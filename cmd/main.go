package main

import (
	"github.com/hosppy/oxcoding/internal/models"
	"github.com/hosppy/oxcoding/internal/routes"
	"github.com/joho/godotenv"
	"github.com/labstack/echo/v4"
	"log"
)

func main() {
	err := godotenv.Load()
	if err != nil {
		log.Fatal("Error loading .env file")
	}
	models.InitDatabase()
	e := echo.New()
	routes.Register(e)
	e.Logger.Fatal(e.Start(":8080"))
}
