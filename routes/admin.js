var Promise = require('bluebird');
var debug = require('debug')('drzewo:routes/admin');
var express = require('express');
var router = express.Router();
var path = require('path');
var fsAsyn = Promise.promisifyAll(require('fs'));
var config=require('../config');

router.get('/new', function(req, res){
	debug('GET /new');
	var albums = [];
	var partialPath = path.normalize(req.query.path || '/');
	var created = req.query.created;
	var albumsPath = path.join(config.defaultAlbumPath, partialPath);
	albumsPath=path.normalize(albumsPath);
	fsAsyn.readdirAsync(albumsPath)
		.bind({dir : albumsPath, partialPath: partialPath, createdAlbum: created})
		.then(analyzeDirectory)
		.then(function(files){
			debug(JSON.stringify(files, null, 2));
			res.render('create-album', files);
		})
		.catch(function(err){
			res.send(err);
		});
});

router.post('/new', function(req,res){
	debug('POST /new');
	var albums = [];
	var partialPath = path.normalize(req.query.path || '/')	;
	var albumsPath = path.join(config.defaultAlbumPath, partialPath);
	var albumName = req.body.album;
	albumsPath=path.normalize(albumsPath);
	res.redirect('/admin/new?path='+partialPath+'&created='+albumName);
});

function analyzeDirectory(files){
	var dir = this.dir;
	var thisPath = this.partialPath;
	var createdAlbum = this.createdAlbum;
	return Promise.all([
			filterDirectories(dir, files), 
			directoryToAlbum(dir, files),
			countImages(files)
		])
		.spread(function(directories, isAlbum, count){
			return {
				isAlbum: isAlbum, 
				pwd: thisPath, 
				totalImages: count, 
				created: createdAlbum, 
				directories : directories
			};
		})
}

function countImages(files){
	return files
		.filter(function(file){
			return !file.startsWith('.');
		})
		.map(function(file){
			return file.toLowerCase().endsWith('.jpg')?1:0;
		})
		.reduce(function(anterior, actual){
			return anterior + actual;
		}, 0);
}

function directoryToAlbum(directory, files){
	return files
		.filter(function(file){
			return !file.startsWith('.');
		})
		.map(function(file){
			var cannonicalPath = path.join(directory, file);
			var filtered=fsAsyn.statSync(cannonicalPath).isFile();
			return filtered;
		})
		.reduce(function(anterior, actual){
			return anterior || actual;
		}, false);
}

function filterDirectories(dir, files) {
	return files.filter(function (file) {
		return fsAsyn.statSync(path.join(dir, file)).isDirectory()
			&& !file.startsWith('.')
	});
}


module.exports = router;
