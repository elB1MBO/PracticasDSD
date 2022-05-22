/**
 * Puedes actualizar un documento usando el metodo updateOne()
 * Primer parametro: objeto query
 * Segundo parametro: nuevo valor del documento
 */
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://127.0.0.1:27017/";
 
MongoClient.connect(url, function(err, db) {
   if (err) throw err;
   var dbo = db.db("mydb");
   var myquery = { address: "Valley 345" };
   var newvalues = { $set: {address: "Canyon 123" } };
   dbo.collection("customers").updateOne(myquery, newvalues, function(err, res) {
     if (err) throw err;
     console.log("1 document updated");
     db.close();
   });
});

/**
 * también se pueden actualizar solo ciertos campos que especifiquemos:
 */

