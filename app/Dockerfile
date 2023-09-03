FROM node:18-alpine

COPY build /app/build/
COPY package.json /app

ENTRYPOINT ["node", "/app/build"]
