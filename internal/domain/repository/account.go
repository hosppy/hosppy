package repository

import (
	"context"
	"github.com/hosppy/oxcoding/internal/domain/model"
)

type AccountRepository interface {
	FindByEmail(ctx context.Context, email string) *model.Account
	Save(ctx context.Context, model *model.Account) *model.Account
	Create(ctx context.Context, model *model.Account) *model.Account
	Update(ctx context.Context, model *model.Account) *model.Account
}
