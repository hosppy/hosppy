package config

import (
	"github.com/joho/godotenv"
	"log/slog"
	"os"
)

type Config struct {
	Address       string
	DSN           string
	WebDomain     string
	SigningSecret string
	SessionSecret string

	AliyunAccessKey    string
	AliyunAccessSecret string
	MailFrom           string
	MailFromName       string
}

func New(logger *slog.Logger) *Config {
	if err := godotenv.Load(); err != nil {
		logger.Info("cannot found .env file")
	}
	return &Config{
		Address:            GetOrDefault("ADDRESS", ":8080"),
		DSN:                os.Getenv("DSN"),
		WebDomain:          os.Getenv("WEB_DOMAIN"),
		SigningSecret:      os.Getenv("SIGNING_SECRET"),
		SessionSecret:      os.Getenv("SESSION_SECRET"),
		AliyunAccessKey:    os.Getenv("ALIYUN_ACCESS_KEY"),
		AliyunAccessSecret: os.Getenv("ALIYUN_ACCESS_SECRET"),
		MailFrom:           os.Getenv("MAIL_FROM"),
		MailFromName:       os.Getenv("MAIL_FROM_NAME"),
	}
}
