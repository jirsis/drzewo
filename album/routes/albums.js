var debug = require('debug')('drzewo:album:routes');
var express = require('express');
var router = express.Router();
var path = require('path');
var http = require('http');
var HttpStatus = require('http-status-codes');

var config=require('../config');

router.get('', function(request, response){

    var page = request.query.page || 1;
    if (page<=0) page = 1;

    var options = {
        host: config.api.host,
        port: config.api.port,
        path: '/albums'+'?page='+page,
        method: 'GET'
    };
    http.request(options, function(res) {
        res.setEncoding('utf8');
        var data = '';
        res.on('data', function (chunk) {
            data += chunk;
        });
        res.on('end', () => {
            var json = JSON.parse(data);
            json.photoServer=
                config.api.protocol+'://'+config.api.host+':'+config.api.port;
            json.locale=request.i18n.getLocale(); 
            debug(json);
            response.render('albums', json);
        });
    }).end();


});

module.exports = router;
