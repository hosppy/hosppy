package postgres

import (
	"context"
	"github.com/hosppy/oxcoding/ent"
	"github.com/hosppy/oxcoding/ent/account"
	"github.com/hosppy/oxcoding/internal/domain/model"
	"github.com/hosppy/oxcoding/internal/domain/repository"
	"time"
)

type AccountRepository struct {
	*ent.Client
}

var _ repository.AccountRepository = (*AccountRepository)(nil)

func toModel(a *ent.Account) *model.Account {
	return &model.Account{
		ID:          a.ID,
		Email:       a.Email,
		Name:        a.Name,
		PhoneNumber: a.PhoneNumber,
		Active:      a.Active,
		CreatedAt:   a.CreatedAt,
		Password:    &model.Password{Hashed: a.PasswordHash},
		AvatarURL:   a.AvatarURL,
	}
}

func NewAccountRepository(client *ent.Client) *AccountRepository {
	return &AccountRepository{client}
}

func (repo *AccountRepository) FindByEmail(ctx context.Context, email string) *model.Account {
	if res := repo.Account.Query().Where(account.Email(email)).FirstX(ctx); res != nil {
		return toModel(res)
	}
	return nil
}

func (repo *AccountRepository) Create(ctx context.Context, model *model.Account) (*model.Account, error) {
	create := repo.Account.
		Create().
		SetEmail(model.Email).
		SetName(model.Name).
		SetActive(model.Active).
		SetPasswordHash(model.Password.Hashed).
		SetAvatarURL(model.AvatarURL).
		SetCreatedAt(time.Now())
	if len(model.PhoneNumber) > 0 {
		create.SetPhoneNumber(model.PhoneNumber)
	}
	save, err := create.Save(ctx)
	if err != nil {
		return nil, err
	}
	return toModel(save), nil
}

func (repo *AccountRepository) Update(ctx context.Context, model *model.Account) (*model.Account, error) {
	update := repo.Account.
		UpdateOneID(model.ID).
		SetEmail(model.Email).
		SetName(model.Name).
		SetActive(model.Active).
		SetPasswordHash(model.Password.Hashed).
		SetAvatarURL(model.AvatarURL)
	if len(model.PhoneNumber) > 0 {
		update.SetPhoneNumber(model.PhoneNumber)
	}
	save, err := update.Save(ctx)
	if err != nil {
		return nil, err
	}
	return toModel(save), nil
}
