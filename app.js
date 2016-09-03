var debug = require('debug')('drzewo:app');
var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var HttpStatus = require('http-status-codes');
var app = express();

var config = require('./config');

var mongoose = require('mongoose');
mongoose.connect(config.mongoHost);
mongoose.Promise = require('bluebird');
var db = mongoose.connection;
db.on('error', function(){debug('conected to database FAILED: '+ config.mongoHost)});
db.once('open', function(){debug('conected to database: '+ config.mongoHost)});

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(express.static(path.join(__dirname, 'gallery')));
// app.use(express.static(path.join(__dirname, 'node_modules/photoswipe/website')));


app.use('/', require('./routes/index'));
app.use('/albums', require('./routes/albums'));


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = HttpStatus.NOT_FOUND;
  next(err);
});

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || HttpStatus.INTERNAL_SERVER_ERROR);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || HttpStatus.INTERNAL_SERVER_ERROR);
  res.render('error', {
    message: err.message,
    error: {}
  });
});

module.exports = app;
