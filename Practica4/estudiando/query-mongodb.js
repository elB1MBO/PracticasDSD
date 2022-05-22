/**
 * Cuando buscas documentos en una coleccion, puedes filtrar el resultado
 * usando un objeto query
 * El primer argumento del metodo find() es un objeto query, que se usa 
 * para limitar la búsqueda.
 */

var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var dbo = db.db("mydb");
  var query = { address: "Park Lane 38" };
  dbo.collection("customers").find(query).toArray(function(err, result) {
    if (err) throw err;
    console.log(result);
    db.close();
  });
}); 

/**
 * Se pueden escribir expresiones regulares para encontrar exactamente lo
 * que se está buscando. 
 * Las expresiones regulares solo se pueden usar para buscar strings
 * 
 * Para buscar solo los documentos donde el campo "address" empiece por "S"
 * usamos la expresion regular: /^S/
 */

 MongoClient.connect(url, function(err, db) {
    if (err) throw err;
    var dbo = db.db("mydb");
    var query = { address: /^S/ };
    dbo.collection("customers").find(query).toArray(function(err, result) {
      if (err) throw err;
      console.log(result);
      db.close();
    });
  });


