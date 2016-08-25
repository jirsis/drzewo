var debug = require('debug')('drzewo:router/albums');
var express = require('express');
var router = express.Router();
var fs = require('fs');
var exif = require('exif').ExifImage;
var gm = require('gm');
var path = require('path');

router.get('/:name', function(req, res) {
	var name = req.params.name;
	fs.readdir(__dirname+'/../gallery/img/'+name, function(err, files){
		if(err) res.status(500).send({"error": err});	
		var data =[];
		files.forEach(function(e){
			// var imagen = {};
			// imagen.href = "http://localhost:3000/img/"+name+"/"+e;
			// imagen.type = 'image/jpeg';
			// imagen.thumbnail = "http://localhost:3000/thumbnail/"+name+"/"+e;
			// data.push(imagen);
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
			new exif({ image : file }, function (error, exifData) {
				if (error) debug('Error: '+error.message);
				else {
					debug("+ "+file+": "+exifData.image.Orientation); 
					gm(file)
					.resize(75, 75)
					.autoOrient()
					.write(thumbnailDir+path.basename(file), function (err) {
						if (err) thumbnailsCreated.push({failed: thumbnailDir+path.basename(file)});
						else thumbnailsCreated.push({created: thumbnailDir+path.basename(file)});
						//res.write({created: thumbnailDir+path.basename(file)});
						debug("writer:" + thumbnailDir+path.basename(file));
					});
				}
			});				
		})
		.reduce(function(){
			res.send(thumbnailsCreated);	
		});
	});
})

module.exports = router;