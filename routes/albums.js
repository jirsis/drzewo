var Promise = require('bluebird');
var debug = require('debug')('drzewo:router/albums');
var express = require('express');
var router = express.Router();
var fs = Promise.promisifyAll(require('fs'));
var gm = require('gm');
Promise.promisifyAll(gm.prototype);
var path = require('path');
var async = require('async');
var sanitizeFilename = require('sanitize-filename');

router.get('/:name', function(req, res) {
	var name = req.params.name;
	fs.readdir(__dirname+'/../gallery/img/'+name, function(err, files){
		if(err) res.status(500).send({"error": err});
		var data =[];
		files.forEach(function(e){
			data.push("http://jirsis.local:3000/img/"+name+"/"+e);
		});
		res.send(data);
	});
});


router.post('/:name', function(req, res){
	var name = req.params.name;
	var dir = __dirname+'/../gallery/img/'+name;
	var thumbnailDir= dir + "/thumbnail/";
	fs.readdir(dir, function(err, files){
		if(!fs.existsSync(thumbnailDir)){
			fs.mkdirSync(thumbnailDir,0744);
		}
		var thumbnailsCreated = [];
		files.map(function(file){
			return dir+'/'+file;
		})
		.filter(function(file){
			if(!fs.statSync(file).isDirectory()) return file;
		})
		.filter(function(file){
			if(!path.basename(file).startsWith(".")) return file;
		})
		.map(function(file){
			gm(file)
			.resize(75, 75)
			.autoOrient()
			.write(thumbnailDir+path.basename(file), function (err) {
				if (err) thumbnailsCreated.push({failed: thumbnailDir+path.basename(file)});
				else thumbnailsCreated.push({created: thumbnailDir+path.basename(file)});
				debug("writer:" + thumbnailDir+path.basename(file));
			});
		});
		res.send(thumbnailsCreated);
	});
})



router.post('/jirsis/:name', function(req, res){
	var name = req.params.name;
	var dir = __dirname+'/../gallery/img/'+name;
	var thumbnailDir= dir + "/thumbnail/";
	fs.readdir(dir, function(err, files){
		if(!fs.existsSync(thumbnailDir)){
			fs.mkdirSync(thumbnailDir,0744);
		}
		var thumbnailsCreated = [];

		files = files.map(function(file){
			return dir+'/'+file;
		})
		.filter(function(file){
			if(!fs.statSync(file).isDirectory()) return file;
		})
		.filter(function(file){
			if(!path.basename(file).startsWith(".")) return file;
		});

		Promise.map(files, function(file){
			var gm = Promise.promisifyAll(require('gm')(file));
			gm.writeAsync(thumbnailDir+path.basename(file))
			.then(
				function(){
					debug("processed: "+thumbnailDir+path.basename(file));
					thumbnailsCreated.push({created: thumbnailDir+path.basename(file)});
				},
				function(){
					debug("error");
					thumbnailsCreated.push({failed: thumbnailDir+path.basename(file)});
				}
			);
		}).then(function(){
			res.send(thumbnailsCreated);
		});

	});
})

router.post('/promise/:name', function(req, res){
	var name = sanitizeFilename(req.params.name);
	var dir = path.normalize(
		path.join(__dirname, '..', 'gallery', 'img', name)
	);
	var thumbnailDir = path.join(dir, 'thumbnail');

	getFiles(dir)
		.bind({ thumbnailDir: thumbnailDir, dir: dir })
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
	return gm(filePath)
		.writeAsync(path.join(this.thumbnailDir, file))
		.then(thumbOK.bind(this, filePath))
		.catch(thumbKO);
}

function thumbOK(file) {
	debug('processed: ', file);
	return file;
}

function thumbKO(error) {
	debug('error: ', error);
	return;
}

function returnThumbnails(files) {
	this.json({ thumbs: files });
}


module.exports = router;
