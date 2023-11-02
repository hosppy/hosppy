package api

type ResponseStatus struct {
	Status    string `json:"status"`
	Message string `json:"message"`
}

var (
	BadCredentialStatus = ResponseStatus{
		Status:    "BadCredential",
		Message: "invalid username or password",
	}
)
