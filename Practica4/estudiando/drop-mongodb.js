/**
 * Puedes borrar una coleccion usando el metodo drop()
 * El método drop() recibe como parametro una funcion con el objeto de error
 * y el parametro de resultado, que devuelve true si la coleccion se 
 * ha borrado correctamente. Si no, devuelve falso
 */

var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var dbo = db.db("mydb");
  /**
   * Podemos usar tambien:
   * dbo.dropCollection("customers", function(err, delOK))
   */
  dbo.collection("customers").drop(function(err, delOK) {
    if (err) throw err;
    if (delOK) console.log("Collection deleted");
    db.close();
  });
}); 