package postgres

import (
	"database/sql"
	"entgo.io/ent/dialect"
	entsql "entgo.io/ent/dialect/sql"
	"github.com/hosppy/oxcoding/ent"
	"github.com/hosppy/oxcoding/internal/config"
	_ "github.com/jackc/pgx/v5/stdlib"
	"log/slog"
)

func NewClient(cfg *config.Config) *ent.Client {
	db, err := sql.Open("pgx", cfg.DSN)
	if err != nil {
		slog.Error("cannot connect database", err)
	}
	driver := entsql.OpenDB(dialect.Postgres, db)
	return ent.NewClient(ent.Driver(driver))
}
