FROM golang:1.21-alpine as builder

WORKDIR /web
COPY . .

RUN go mod download
RUN go mod verify

RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -ldflags "-s -w" -o /server ./cmd/main.go

FROM gcr.io/distroless/static-debian11

ENV TZ=Asia/Shanghai

COPY --from=builder /server .

EXPOSE 8080

ENTRYPOINT ["/server"]