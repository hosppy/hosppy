package repository

import (
	"context"
	"github.com/hosppy/oxcoding/internal/domain/entity"
)

type AccountRepository interface {
	FindByEmail(email string) *entity.Account
	Save(ctx context.Context, entity *entity.Account) *entity.Account
	Create(ctx context.Context, entity *entity.Account) *entity.Account
	Update(ctx context.Context, entity *entity.Account) *entity.Account
}
