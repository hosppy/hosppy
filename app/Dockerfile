FROM node:16-alpine as builder

WORKDIR /app

COPY .npmrc package.json package-lock.json ./

RUN npm ci

COPY . .

RUN npm run build

FROM node:16-alpine

COPY --from=builder /app/build /app
COPY --from=builder /app/package.json /app

ENTRYPOINT ["node", "/app"]
