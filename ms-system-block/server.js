const http = require('http');
const express = require('express');
const bodyParser = require("body-parser");
const e = require('express');

const hostname = '127.0.0.1';
const port = 3000;

const router = express.Router();
const app = express();

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

var systems = [];

router.post('/status', (request, response) => {
    const system = request.body;

    const index = systems.findIndex(s => s.id = system.id);

    if (index === -1) {
        systems.push(system);
        response.send(system)
    } else {
        response.send(systems[index])
        systems = systems.filter(s => s.status === 'blocked');
    }
})

router.get('/systems', (request, response) => {
    response.send(systems);
})

router.post('/unblock', (request, response) => {
    if (request.body.secret !== 'some_secret_string') {
        response.status(403).send('Invalid secret');
    } else {
        index = systems.findIndex(s => s.id = request.body.id);

        if (index === -1) {
            response.status(400).send('There is no system with this id');
        } else {
            systems[index].status = 'unblocked';
            response.send(systems[index]);
        }
    }
})

app.use(router);


let server = app.listen(5005, function () {
    console.log('Server is listening on port 5005')
});
