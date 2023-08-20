package models

import (
	"database/sql"
	"entgo.io/ent/dialect"
	entsql "entgo.io/ent/dialect/sql"
	"github.com/hosppy/oxcoding/ent"
	_ "github.com/jackc/pgx/v5/stdlib"
	"log"
	"os"
)

var Client *ent.Client

func InitConnection() {
	db, err := sql.Open("pgx", os.Getenv("DSN"))
	if err != nil {
		log.Fatal(err)
	}
	driver := entsql.OpenDB(dialect.Postgres, db)
	Client = ent.NewClient(ent.Driver(driver))
}
