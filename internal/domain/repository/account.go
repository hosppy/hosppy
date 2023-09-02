package repository

import "github.com/hosppy/oxcoding/internal/domain/entity"

type AccountRepository interface {
	FindByEmail(email string) *entity.Account
	Create(account *entity.Account) *entity.Account
}
