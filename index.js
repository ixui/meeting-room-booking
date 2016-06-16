var express = require('express');
var http = require('http');

var app = express();
app.use(express.static('public'));

app.get('/', function (req, res) {
  res.redirect('index.html');
});

var port = process.env.PORT || 1337;
var httpServer = http.createServer(app);
httpServer.listen(port, function() {
  console.log('Running on port ' + port + '.');
});
