var express = require('express');
var router = express.Router();
var request= require('request');

var GoogleKeyID = 'AIzaSyDNiFcGmZczlPOeDuh2dk3T0lpN2GJtvRM';
var searchID = '016817846776859490566:3zc19lukrl5';
/* GET users listing. */
router.get('/', function(req, res, next) {

  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

  var city = req.query.city;

  var url = 'https://www.googleapis.com/customsearch/v1?q='+ city +'&cx=' + searchID
    +'&imgSize=large&imgType=photo&num=8&searchType=image&key=' + GoogleKeyID;

  request(url, { json: true }, function (err, re, body) {
    if (err) {
      res.end("{}");
      return console.log(err);
    }

    res.send(body);

  });
});

module.exports = router;
