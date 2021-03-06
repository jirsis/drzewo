var Promise = require('bluebird');
var debug = require('debug')('drzewo:routes/albums');
var express = require('express');
var router = express.Router();
var fsAsyn = Promise.promisifyAll(require('fs'));
var fs=require('fs');
var gm = require('gm');
require('gm-buffer');
var path = require('path');
var async = require('async');
var sanitizeFilename = require('sanitize-filename');
var HttpStatus = require('http-status-codes');

var Album = require('../models/albums');
var Thumbnail = require('../models/thumbnails');

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

router.get('/:albumName/thumbnails/:image', function(req, res){
	var albumName = sanitizeFilename(req.params.albumName);
	var image = sanitizeFilename(req.params.image);
	Thumbnail.find({album: albumName, image: image}, function(err, thumbnails){
		if(err) res.status(HttpStatus.INTERNAL_SERVER_ERROR).send(err);
		var thumbnail = thumbnails.pop();
		if(!thumbnail) res.status(HttpStatus.NOT_FOUND).end();
		else {
			thumbnail.load()
				.then(function(doc){
					var attachment = doc.attachments[0];
					res.contentType(attachment.mimetype);
					res.end(new Buffer(attachment.buffer.toString(), 'base64'));
				})
				.catch(function(err){
					debug("error: "+err);
				})
				.done();
		}
	})
})

router.post('/:name', function(req, res){
	var name = sanitizeFilename(req.params.name);
	var dir = path.normalize(
		path.join(__dirname, '..', 'gallery', 'img', name)
	);
	var thumbnailDir = path.join(dir, 'thumbnail');
	var urlBase = path.join(config.urlBase, name, 'thumbnail');
	debug("url: "+ urlBase);
	fs.readdir(dir, function(err, files){
		var thumbnails = files.filter(function(file){
			return fs.statSync(path.join(dir, file)).isFile() && !file.startsWith('.');
		})
		var album = new Album({name: name, total_pictures: thumbnails.length, physical_directory: dir})
		album.save();
		thumbnails.forEach(function(file){
			var source = path.join(dir, file);
			var destination = path.join(thumbnailDir, file);
			debug("processing "+source);
			gm(source)
				.resize(75,75, '!')
				.autoOrient()
				.buffer(function(err, thumbnailBuffer){
					if(err) debug('error generating thumbnail');
					else {
						var thumbnail = new Thumbnail({album: name, image: file});
						thumbnail
							.addImage(file, thumbnailBuffer)
							.then(function(doc){
								debug("add image");
								thumbnail.save();
							})
							.catch(function(err){
								debug("error adding image");
							})
							.done();
					}
				});
		});
		res.json({status: album.total_pictures+" files"});
	})
});

function generateThumbnail(file){
	gm
		.resize(75,75, '!')
		.autoOrient()
		.write(path.join(this, file), function(err){
			if(!err) debug('weeeee');
		});
}

function getFiles(dir) {
	return fsAsyn.readdirAsync(dir).bind({ dir: dir }).then(filterFiles);
}

function filterFiles(files) {
	var dir = this.dir;
	return files.filter(function (file) {
		return fsAsyn.statSync(path.join(dir, file)).isFile()
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

function createUrl(file){
	var name = this.name;
	return path.join(config.urlBase, name, file);
}

function returnUrls(files){
	this.json({ photos: files });
}


module.exports = router;
