var express = require('express');
var router = express.Router();
var request= require('request');

var GoogleKeyID = 'AIzaSyBPVTPSuM7gg89004kxepJI_0K-VFJFg5I';
var searchID = '016817846776859490566:3zc19lukrl5';
/* GET users listing. */
router.get('/', function(req, res, next) {

  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

  var state = req.query.state;

  var url = 'https://www.googleapis.com/customsearch/v1?q='+ state +'%20State%20Seal&cx=' + searchID
    +'&imgSize=huge&imgType=news&num=1&searchType=image&key=' + GoogleKeyID;

  request(url, { json: true }, function (err, re, body) {
    if (err) {
      res.end("{}");
      return console.log(err);
    }

    res.send(body);

  });
});

module.exports = router;
