package service

import (
	"github.com/hosppy/oxcoding/internal/domain/entity"
	"github.com/hosppy/oxcoding/internal/domain/repository"
	"github.com/hosppy/oxcoding/internal/service/passwordhash"
)

type AccountService struct {
	accountRepo repository.AccountRepository
}

func NewAccountService(accountRepository repository.AccountRepository) *AccountService {
	return &AccountService{accountRepository}
}

func (a *AccountService) UsernamePasswordAuthenticate(username string, password string) (*entity.Account, bool) {
	foundAccount := a.accountRepo.FindByEmail(username)

	if foundAccount != nil && passwordhash.Check(password, foundAccount.PasswordHash) {
		return foundAccount, true
	}
	return nil, false
}
