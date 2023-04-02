FROM node:latest as builder

RUN mkdir -p /demo-angular

WORKDIR /demo-angular

COPY . .

RUN npm install

CMD ["npm", "start"]


