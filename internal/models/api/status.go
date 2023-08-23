package api

type ResponseStatus struct {
	Code    string `json:"code"`
	Message string `json:"message"`
}

var (
	BadCredentialStatus = ResponseStatus{
		Code:    "BadCredential",
		Message: "invalid username or password",
	}
)
