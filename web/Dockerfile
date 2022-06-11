FROM node:16-alpine AS builder

WORKDIR /web

COPY . .

RUN npm i && npm run build

FROM nginx:alpine

COPY --from=builder /web/dist /usr/share/nginx/html

EXPOSE 80