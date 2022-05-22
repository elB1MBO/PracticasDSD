/**
 * El metodo sort() sirve para ordenar el resultado en orden ascendiente o descendiente
 * el método sort() tiene un parñametro, un objeto que define el orden.
 * El valor de los parámetros es:
 * {name: 1} --> orden ascendiente por el nombre (A-Z)
 * {name: -1} --> orden descendiente (Z-A)
 */

var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var dbo = db.db("mydb");
  var mysort = { name: -1 };
  dbo.collection("customers").find().sort(mysort).toArray(function(err, result) {
    if (err) throw err;
    console.log(result);
    db.close();
  });
}); 