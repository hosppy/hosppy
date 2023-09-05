package postgres

import (
	"context"
	"github.com/hosppy/oxcoding/ent"
	"github.com/hosppy/oxcoding/ent/account"
	"github.com/hosppy/oxcoding/internal/domain/entity"
	"github.com/hosppy/oxcoding/internal/domain/repository"
	"time"
)

type AccountRepository struct {
	*ent.Client
}

var _ repository.AccountRepository = (*AccountRepository)(nil)

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

func NewAccountRepository(client *ent.Client) *AccountRepository {
	return &AccountRepository{client}
}

func (repo *AccountRepository) FindByEmail(email string) *entity.Account {
	if res := repo.Account.Query().Where(account.Email(email)).FirstX(context.Background()); res != nil {
		return toEntity(res)
	}
	return nil
}

func (repo *AccountRepository) Create(ctx context.Context, entity *entity.Account) *entity.Account {
	create := repo.Account.
		Create().
		SetEmail(entity.Email).
		SetName(entity.Name).
		SetActive(entity.Active).
		SetPasswordHash(entity.PasswordHash).
		SetAvatarURL(entity.AvatarURL).
		SetCreatedAt(time.Now())
	if len(entity.PhoneNumber) > 0 {
		create.SetPhoneNumber(entity.PhoneNumber)
	}
	return toEntity(create.SaveX(ctx))
}

func (repo *AccountRepository) Update(ctx context.Context, entity *entity.Account) *entity.Account {
	update := repo.Account.
		UpdateOneID(entity.ID).
		SetEmail(entity.Email).
		SetName(entity.Name).
		SetActive(entity.Active).
		SetPasswordHash(entity.PasswordHash).
		SetAvatarURL(entity.AvatarURL)
	if len(entity.PhoneNumber) > 0 {
		update.SetPhoneNumber(entity.PhoneNumber)
	}
	return toEntity(update.SaveX(ctx))
}

func (repo *AccountRepository) Save(ctx context.Context, entity *entity.Account) *entity.Account {
	if entity.ID > 0 {
		return repo.Update(ctx, entity)
	}
	return repo.Create(ctx, entity)
}
