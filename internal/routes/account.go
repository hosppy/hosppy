package routes

import (
	"context"
	"github.com/hosppy/oxcoding/internal/models"
	"github.com/labstack/echo/v4"
	"net/http"
)

func GetAccounts(c echo.Context) error {
	accounts := models.Client.Account.Query().AllX(context.Background())
	return c.JSON(http.StatusOK, accounts)
}

func GetProfile(c echo.Context) error {
	return nil
}
