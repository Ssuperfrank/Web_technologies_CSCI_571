var express = require('express');
var router = express.Router();
var request= require('request');

var GoogleKeyID = 'AIzaSyBPVTPSuM7gg89004kxepJI_0K-VFJFg5I';

/* GET users listing. */
router.get('/', function(req, res, next) {

  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

  var street = req.query.street;
  var city = req.query.city;
  var state = req.query.state;

  var location = req.query.location;

  var url1 = 'https://maps.googleapis.com/maps/api/geocode/json?address='+
    street+','+city+','+state+
    '&key='+GoogleKeyID;


  var url2 = 'https://maps.googleapis.com/maps/api/geocode/json?address='+
    location+
    '&key='+GoogleKeyID;

  var url;
  if(location){
    url = url2;
  }else{
    url = url1;
  }

  request(url, { json: true }, function (err, re, body) {
    if (err) {
      res.end("{}");
      return console.log(err);
    }

    if(re.statusCode === 200){
      if(body.status === "OK"){
        res.send(body);
      }
    }
    res.end("{}");
  });
});

module.exports = router;
