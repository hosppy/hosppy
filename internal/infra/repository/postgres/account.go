package postgres

import (
	"github.com/hosppy/oxcoding/ent"
	"github.com/hosppy/oxcoding/internal/domain/entity"
	"github.com/hosppy/oxcoding/internal/domain/repository"
)

type AccountRepository struct {
	*ent.Client
}

var _ repository.AccountRepository = (*AccountRepository)(nil)

func (a *AccountRepository) FindByEmail(email string) *entity.Account {
	//TODO implement me
	panic("implement me")
}
