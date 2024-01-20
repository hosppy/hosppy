package api

type Response struct {
	Error   string `json:"error"`
	Message string `json:"message"`
}

var (
	ResponseBadCredential = Response{
		Error:   "BadCredential",
		Message: "invalid username or password",
	}
)
