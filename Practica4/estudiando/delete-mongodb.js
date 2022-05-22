/**
 * Para borrar un record o documento en MongoDB usamos el metodo deleteOne()
 * El primer parámetro que recibe es un objeto query que define que
 * documento hay que borrar. Si el query encuentra más de un objeto, sólo 
 * borra el primero que encuentra
 */

 var MongoClient = require('mongodb').MongoClient;
 var url = "mongodb://localhost:27017/";
 
MongoClient.connect(url, function(err, db) {
   if (err) throw err;
   var dbo = db.db("mydb");
   var myquery = { address: 'Mountain 21' };
   dbo.collection("customers").deleteOne(myquery, function(err, obj) {
     if (err) throw err;
     console.log("1 document deleted");
     db.close();
   });
 });

 /**
  * Para borrar más de un documento, se usa el deleteMany()
  */

/* MongoClient.connect(url, function(err, db) {
    if (err) throw err;
    var dbo = db.db("mydb");
    //Delete all customers where the address starts with an "O":
    var myquery = { address: /^O/ };
    dbo.collection("customers").deleteMany(myquery, function(err, obj) {
      if (err) throw err;
      console.log(obj.result.n + " document(s) deleted");
      db.close();
    });
}); */