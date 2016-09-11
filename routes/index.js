var debug = require('debug')('drzewo:routes/index');
var express = require('express');
var http = require('http');
var router = express.Router();

router.get('/', function(req, response) {
  var options = {
  	host: req.hostname,
  	port: req.socket.localPort,
  	path: '/albums/example',
  	method: 'GET'
  };
  http.request(options, function(res) {
  	res.setEncoding('utf8');
	  res.on('data', function (chunk) {
		  response.render('index', JSON.parse(chunk));	   
	  });
  }).end();
});

module.exports = router;
