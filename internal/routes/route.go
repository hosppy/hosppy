package routes

import "github.com/labstack/echo/v4"

func Register(e *echo.Echo) {
	e.GET("/accounts", GetAccounts)
}
