package service

import (
	"context"
	"github.com/hosppy/oxcoding/internal/domain/entity"
	"github.com/hosppy/oxcoding/internal/domain/repository"
	"github.com/hosppy/oxcoding/internal/infra/mail"
)

type AccountService struct {
	accountRepository repository.AccountRepository
	mailClient        *mail.Client
}

func NewAccountService(accountRepository repository.AccountRepository, mailClient *mail.Client) *AccountService {
	return &AccountService{accountRepository, mailClient}
}

func (a *AccountService) UsernamePasswordAuthenticate(username string, password string) (*entity.Account, bool) {
	foundAccount := a.accountRepository.FindByEmail(username)

	if foundAccount != nil && foundAccount.CheckPassword(password) {
		return foundAccount, true
	}
	return nil, false
}

func (a *AccountService) Create(account *entity.AccountDTO) *entity.Account {
	foundAccount := a.accountRepository.FindByEmail(account.Email)
	accountSave := account.ToModel()
	// TODO send mail
	if foundAccount == nil {
		newAccount := a.accountRepository.Save(context.Background(), accountSave)
		return newAccount
	}
	foundAccount.PasswordHash = accountSave.PasswordHash
	a.accountRepository.Save(context.Background(), foundAccount)
	return foundAccount
}
