var express = require('express');
var router = express.Router();
var request= require('request');

var autoKeyID = 'AIzaSyDNiFcGmZczlPOeDuh2dk3T0lpN2GJtvRM';

/* GET users listing. */
router.get('/', function(req, res, next) {

  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

  var input = req.query.input;

  var url = 'https://maps.googleapis.com/maps/api/place/autocomplete/json?input='
    + input + '&types=(cities)&language=en&key='
    + autoKeyID;

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
