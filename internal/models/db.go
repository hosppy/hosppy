package models

import (
	"database/sql"
	"entgo.io/ent/dialect"
	entsql "entgo.io/ent/dialect/sql"
	"github.com/hosppy/oxcoding/ent"
	_ "github.com/jackc/pgx/v5/stdlib"
	"log/slog"
	"os"
)

var Client *ent.Client

func InitDatabase() {
	db, err := sql.Open("pgx", os.Getenv("DSN"))
	if err != nil {
		slog.Error("cannot connect database", err)
	}
	driver := entsql.OpenDB(dialect.Postgres, db)
	Client = ent.NewClient(ent.Driver(driver))
}
