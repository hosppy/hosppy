package postgres

import (
	"database/sql"
	"entgo.io/ent/dialect"
	entsql "entgo.io/ent/dialect/sql"
	"github.com/hosppy/oxcoding/ent"
	_ "github.com/jackc/pgx/v5/stdlib"
	"log/slog"
	"os"
)

type DB struct {
	*ent.Client
}

func New() *DB {
	db, err := sql.Open("pgx", os.Getenv("DSN"))
	if err != nil {
		slog.Error("cannot connect database", err)
	}
	driver := entsql.OpenDB(dialect.Postgres, db)
	client := ent.NewClient(ent.Driver(driver))

	return &DB{client}
}

func (db *DB) NewAccountRepository() *AccountRepository {
	return &AccountRepository{db.Client}
}
