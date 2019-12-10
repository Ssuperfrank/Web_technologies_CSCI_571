var express = require('express');
var router = express.Router();
var request= require('request');

var darkKEY = 'bf40c926ecd12df976812fd11a1f64e5';

/* GET users listing. */
router.get('/', function(req, res, next) {

  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

  var lat = req.query.lati;
  var lon = req.query.long;
  var url = 'https://api.darksky.net/forecast/'+
    darkKEY +'/' + lat + ','+ lon;

  request(url, { json: true }, function (err, re, body) {
    if (err) {
      res.end("{}");
      return console.log(err);
    }

    if(re.statusCode === 200){
        res.send(body);
    }

    res.end("{}");

  });
});

module.exports = router;
