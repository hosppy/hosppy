package service

import (
	"github.com/hosppy/oxcoding/internal/domain/entity"
	"github.com/hosppy/oxcoding/internal/domain/repository"
	"github.com/hosppy/oxcoding/internal/infra/mail"
	"github.com/hosppy/oxcoding/internal/service/passwordhash"
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

	if foundAccount != nil && passwordhash.Check(password, foundAccount.PasswordHash) {
		return foundAccount, true
	}
	return nil, false
}

func (a *AccountService) Create(account *entity.Account) *entity.Account {
	foundAccount := a.accountRepository.FindByEmail(account.Email)
	// TODO send mail
	if foundAccount == nil {
		newAccount := a.accountRepository.Create(account)
		return newAccount
	}
	return foundAccount
}
