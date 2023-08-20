package schema

import (
	"entgo.io/ent"
	"entgo.io/ent/dialect/entsql"
	"entgo.io/ent/schema"
	"entgo.io/ent/schema/field"
	"time"
)

// Account holds the schema definition for the Account entity.
type Account struct {
	ent.Schema
}

// Fields of the Account.
func (Account) Fields() []ent.Field {
	return []ent.Field{
		field.Int("id").Positive(),
		field.String("email").Unique(),
		field.String("name").Default(""),
		field.String("phone_number").Unique().Nillable(),
		field.Bool("active").Default(false),
		field.Time("created_at").Default(time.Now()).Immutable(),
		field.String("password_hash").Optional().Default(""),
		field.String("avatar_url").Optional().Default(""),
	}
}

// Edges of the Account.
func (Account) Edges() []ent.Edge {
	return nil
}

// Annotations of the Account.
func (Account) Annotations() []schema.Annotation {
	return []schema.Annotation{
		entsql.Annotation{Table: "account"},
	}
}
