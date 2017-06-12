var debug = require('debug')('drzewo:admin:routes/admin');
var express = require('express');
var router = express.Router();
var http = require('http');
var request = require('request');
var url = require('url');
var path = require('path');
var config = require('../config.json');

router.get('', function(request, response){
	var queryPath = path.normalize(request.query.path || '.');

	var options = {
	    host: config.api.host,
	    port: config.api.port,
	    path: '/directories?path='+queryPath,
	    method: 'GET'
	};
	http.request(options, function(res) {
		res.setEncoding('utf8');
		res.on('data', function (chunk) {
			var json = JSON.parse(chunk)
			json.created=request.query.created;
			response.render('create-album', json);
		});
	}).end();
});

router.post('', function(req, res){
	var albumName = req.body.album;
	var albumPath = path.normalize(req.query.path || '.');
	debug("-"+albumPath);
	var options = {
        uri: url.format({
            protocol: config.api.protocol,
            host: [config.api.host, config.api.port].join(':'),
            pathname: ['albums',albumName].join('/')
        }),
        method: 'POST',
        json: {"path": albumPath}
    };
    request(options, function(err, response, body){
    	if( !err && response.statusCode === 202){
        	res.redirect('?path='+albumPath+'&created='+albumName);
        }
    });
});

module.exports = router;
