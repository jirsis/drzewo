var Promise = require('bluebird');
var debug = require('debug')('drzewo:routes/albums');
var express = require('express');
var router = express.Router();
var fs = Promise.promisifyAll(require('fs'));
var gm = require('gm');
Promise.promisifyAll(gm.prototype);
var path = require('path');
var async = require('async');
var sanitizeFilename = require('sanitize-filename');

var config=require('../config');

router.get('/:name', function(req, res) {
	var name = req.params.name;
	var dir = path.join(__dirname, '..', 'gallery', 'img', name);
	getFiles(dir)
		.bind({name: name})
		.map(createUrl)
		.bind(res)
		.then(returnUrls)
});

function createUrl(file){
	var name = this.name;
	return path.join(config.urlBase, name, file);
}

function returnUrls(files){
	this.json({ photos: files });
}

router.post('/:name', function(req, res){
	var name = sanitizeFilename(req.params.name);
	var dir = path.normalize(
		path.join(__dirname, '..', 'gallery', 'img', name)
	);
	var thumbnailDir = path.join(dir, 'thumbnail');
	var urlBase = path.join(config.urlBase, name, 'thumbnail');
	debug("url: "+ urlBase);
		
	if(!fs.existsSync(thumbnailDir)){
		fs.mkdirSync(thumbnailDir,0744);	
	}

	getFiles(dir)
		.bind({ thumbnailDir: thumbnailDir, urlBase: urlBase, dir: dir })
		.map(createThumbnail)
		.bind(res)
		.then(returnThumbnails);
});

function getFiles(dir) {
	return fs.readdirAsync(dir).bind({ dir: dir }).then(filterFiles);
}

function filterFiles(files) {
	var dir = this.dir;
	return files.filter(function (file) {
		return fs.statSync(path.join(dir, file)).isFile()
			&& !file.startsWith('.')
	});
}

function createThumbnail(file) {
	var filePath = path.join(this.dir, file);
	var urlBase = this.urlBase;
	return gm(filePath)
		.resize(75,75)
		.autoOrient()
		.writeAsync(path.join(this.thumbnailDir, file))
		.then(thumbOK.bind(this, path.join(urlBase, file)))
		.catch(thumbKO);
}

function thumbOK(file) {
	debug('processed: ', file);
	return {photo: file, status: "processed"};
}

function thumbKO(file) {
	debug('error: ', file);
	return {photo: file, status: "failed"};;
}

function returnThumbnails(files) {
	this.json({ thumbs: files });
}


module.exports = router;
