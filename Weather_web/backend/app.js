var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var autoCompleteRouter = require('./routes/autoComplete');
var googleLocation = require('./routes/googleAPILocation');
var weather = require('./routes/weather');
var seal = require('./routes/stateSeal');
var cityPhotos = require('./routes/searchPhotos');
var weatherByDate = require('./routes/weatherByDate');

var app = express();

app.use(express.static("./weather"));
app.get('/',(req,res)=>{
  res.sendFile("./weather/index.html",{root:__dirname});
});

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/autoComplete', autoCompleteRouter);
app.use('/googleLocation', googleLocation);
app.use('/weather', weather);
app.use('/stateSeal', seal);
app.use('/weatherByDate', weatherByDate);
app.use('/searchPhotos', cityPhotos);

module.exports = app;

var server = app.listen(8081,function () {
  console.log("loading");
});
