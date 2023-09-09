package service

import "github.com/hosppy/oxcoding/internal/domain/model"

type MailService interface {
	Send(mail *model.Mail)
}
