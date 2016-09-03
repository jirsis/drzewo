var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var albumsSchema = new Schema({
	    name: {
	    	type: String,
	    	unique: true,
	    	index: true
	    },
	    physical_directory: String,
	    total_pictures: Number
});

module.exports = mongoose.model('Album', albumsSchema);