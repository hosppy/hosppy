package mail

import (
	"github.com/aliyun/alibaba-cloud-sdk-go/sdk"
	"github.com/aliyun/alibaba-cloud-sdk-go/sdk/requests"
	"github.com/hosppy/oxcoding/internal/config"
	"log/slog"
)

type Client struct {
	*sdk.Client
	cfg *config.Config
}

func NewClient(cfg *config.Config) (*Client, error) {
	client, err := sdk.NewClientWithAccessKey("cn-hangzhou", cfg.AliyunAccessKey, cfg.AliyunAccessSecret)
	if err != nil {
		return nil, err
	}
	return &Client{client, cfg}, nil
}

func (c *Client) Send(message *Message) {
	requests.NewCommonRequest()
	mailRequest := requests.NewCommonRequest()
	mailRequest.Method = "POST"
	mailRequest.ApiName = "SingleSendMail"
	mailRequest.Product = "Dm"
	mailRequest.Domain = "dm.aliyuncs.com"
	mailRequest.Scheme = "https"
	mailRequest.Version = "2015-11-23"
	mailRequest.RegionId = "cn-hangzhou"
	mailRequest.QueryParams["AccountName"] = c.cfg.MailFrom
	mailRequest.QueryParams["FromAlias"] = c.cfg.MailFromName
	mailRequest.QueryParams["AddressType"] = "1"
	mailRequest.QueryParams["ReplyToAddress"] = "false"
	mailRequest.QueryParams["ToAddress"] = message.ToAddress
	mailRequest.QueryParams["Subject"] = message.Subject
	mailRequest.QueryParams["HtmlBody"] = message.HtmlBody

	_, err := c.ProcessCommonRequest(mailRequest)
	if err != nil {
		slog.Error("cannot send mail", err)
		return
	}
}
