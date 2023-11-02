package service

import (
	"bytes"
	"context"
	"log/slog"
	"text/template"

	"github.com/hosppy/oxcoding/internal/config"
	"github.com/hosppy/oxcoding/internal/domain/command"
	"github.com/hosppy/oxcoding/internal/domain/model"
	"github.com/hosppy/oxcoding/internal/domain/repository"
	"github.com/hosppy/oxcoding/internal/sign"
)

type AccountService struct {
	accountRepository repository.AccountRepository
	mailService       MailService
	cfg               *config.Config
}

func NewAccountService(accountRepository repository.AccountRepository, mailService MailService, cfg *config.Config) *AccountService {
	return &AccountService{accountRepository, mailService, cfg}
}

func (a *AccountService) UsernamePasswordAuthenticate(username string, password string) (*model.Account, bool) {
	existingAccount := a.accountRepository.FindByEmail(context.Background(), username)
	if existingAccount != nil && existingAccount.Password.Check(password) {
		return existingAccount, true
	}
	return nil, false
}

func (a *AccountService) Register(command *command.CreateAccountCommand) (*model.Account, error) {
	account, err := a.Create(context.Background(), command)
	if err != nil {
		return nil, err
	}
	go a.SendActivateMail(account)
	return account, err
}
func (a *AccountService) Create(ctx context.Context, command *command.CreateAccountCommand) (*model.Account, error) {
	existingAccount := a.accountRepository.FindByEmail(ctx, command.Email)
	hashedPassword, err := model.NewPassword(command.Password)
	if err != nil {
		return nil, err
	}

	if existingAccount == nil {
		// create a new account
		account := &model.Account{Name: command.Name, Email: command.Email, Password: hashedPassword}
		newAccount := a.accountRepository.Save(ctx, account)
		return newAccount, nil
	}

	// update password
	existingAccount.Password = hashedPassword
	a.accountRepository.Save(context.Background(), existingAccount)
	return existingAccount, nil
}

func (a *AccountService) SendActivateMail(account *model.Account) {
	html := `
<div>
    <p>Hi {{.Name}}, and welcome to Oxcoding!</p><a href="{{.Link}}">Please click here to finish setting up your account.</a>
</div>
<br>
<br>
<div>If you have trouble clicking on the link, please copy and paste this link into your browser: <br/>
    <a href="{{.Link}}">{{.Link}}</a>
</div>`
	tmpl, err := template.New("mail").Parse(html)
	if err != nil {
		slog.Error("parse activate mail template error", err)
		return
	}
	token, err := sign.GenerateEmailConfirmationToken(account.ID, account.Email, a.cfg.SigningSecret)
	if err != nil {
		slog.Error("cannot generate email confirmation token", err)
		return
	}
	var buf bytes.Buffer
	err = tmpl.Execute(&buf, map[string]string{
		"Name": account.Name,
		"Link": a.cfg.WebDomain + "/activate/" + token,
	})
	if err != nil {
		slog.Error("output activate mail template error", err)
	}
	a.mailService.Send(&model.Mail{
		To:      account.Email,
		Subject: "Activate your Oxcoding account",
		Html:    buf.String(),
	})
}
