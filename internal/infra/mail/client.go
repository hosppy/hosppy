package mail

import (
	"github.com/aliyun/alibaba-cloud-sdk-go/sdk"
	"github.com/aliyun/alibaba-cloud-sdk-go/sdk/requests"
	"log/slog"
	"os"
)

type Client struct {
	*sdk.Client
}

func NewClient() *Client {
	client, err := sdk.NewClientWithAccessKey("REGION_ID", os.Getenv("ALIYUN_ACCESS_KEY"), os.Getenv("ALIYUN_ACCESS_SECRET"))
	if err != nil {
		panic(err)
	}
	return &Client{client}
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
	mailRequest.QueryParams["AccountName"] = os.Getenv("MAIL_FROM")
	mailRequest.QueryParams["FromAlias"] = os.Getenv("MAIL_FROM_NAME")
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
