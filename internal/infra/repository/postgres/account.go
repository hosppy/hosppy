package postgres

import (
	"context"

	"github.com/hosppy/oxcoding/ent"
	"github.com/hosppy/oxcoding/ent/account"
	"github.com/hosppy/oxcoding/internal/domain/entity"
	"github.com/hosppy/oxcoding/internal/domain/repository"
)

type AccountRepository struct {
	*ent.Client
}

var _ repository.AccountRepository = (*AccountRepository)(nil)

func (a *AccountRepository) FindByEmail(email string) *entity.Account {
	if res := a.Account.Query().Where(account.Email(email)).FirstX(context.Background()); res != nil {
		return toEntity(res)
	}
	return nil
}

func toEntity(a *ent.Account) *entity.Account {
	return &entity.Account{
		ID:           a.ID,
		Email:        a.Email,
		Name:         a.Name,
		PhoneNumber:  a.PhoneNumber,
		Active:       a.Active,
		CreatedAt:    a.CreatedAt,
		PasswordHash: a.PasswordHash,
		AvatarURL:    a.AvatarURL,
	}
}
