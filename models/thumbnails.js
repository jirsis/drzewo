var mongoose  = require('mongoose');
var mongooseGM = require('mongoose-gm');
var Schema = mongoose.Schema;

var thumbnailSchema = new Schema({
		album: String,
		image: String,
	    width: Number,
		height: Number
});

thumbnailSchema.plugin(mongooseGM);

module.exports = mongoose.model('Thumbnail', thumbnailSchema);