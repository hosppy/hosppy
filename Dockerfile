FROM golang:1.20-alpine as builder

WORKDIR /app
COPY . .

RUN go mod download
RUN go mod verify

RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -o /server .

FROM gcr.io/distroless/static-debian11

COPY --from=builder /server .

EXPOSE 8080

ENTRYPOINT ["/server"]