var debug = require('debug')('drzewo:album:app');
var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var HttpStatus = require('http-status-codes');
var app = express();
var i18n = require('i18n-x');

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');
app.locals.pretty = true;

app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(i18n({
  locales: ['es', 'pl']
}));

app.use(express.static(path.join(__dirname, 'static')));

app.use('/', require('./routes/albums'));


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
