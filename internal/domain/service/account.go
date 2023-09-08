package service

import (
	"context"
	"github.com/hosppy/oxcoding/internal/domain/model"
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

func (a *AccountService) UsernamePasswordAuthenticate(username string, password string) (*model.Account, bool) {
	existingAccount := a.accountRepository.FindByEmail(context.Background(), username)
	if existingAccount != nil && existingAccount.Password.Check(password) {
		return existingAccount, true
	}
	return nil, false
}

func (a *AccountService) Create(name string, email string, password string) (*model.Account, error) {
	ctx := context.Background()
	existingAccount := a.accountRepository.FindByEmail(ctx, email)
	hashedPassword, err := model.NewPassword(password)
	if err != nil {
		return nil, err
	}

	// TODO send mail
	if existingAccount == nil {
		account := &model.Account{Name: name, Email: email, Password: hashedPassword}
		newAccount := a.accountRepository.Save(ctx, account)
		return newAccount, nil
	}

	// create a new account
	existingAccount.Password = hashedPassword
	a.accountRepository.Save(context.Background(), existingAccount)
	return existingAccount, nil
}
