var MongoClient = require('mongodb').MongoClient;
//Create a database named "mydb":
var url = "mongodb://localhost:27017/mydb";

/**
 * Para crear una base de datos en mongoDB, hay que crear primero un objeto
 * MongoClient, entonces espcificar una conexion URL con la direccion ip
 * correcta y el nombre la base de datos que quieres crear
 * Si la base de datos no existe, MongoDB la creará y se conectará a ella
 */

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var dbo = db.db("mydb");
  //Insertamos valores en la coleccion:
  /*NOTA: Si intentas insertar documentos en una colección que no existe, 
  entonces MongoDB creará esa colección automáticamente*/
  var myobj = {name: "Bimbo Co", address: "La Cimada"};
  dbo.collection("customers").insertOne(myobj, function(err, response){
      if(err) throw err;
      console.log("1 documento insertado");
      db.close();
  });
  
  //También podemos insertar varios documentos a la vez:
  /* var myobjects = [
    { name: 'John', address: 'Highway 71'},
    { name: 'Peter', address: 'Lowstreet 4'},
    { name: 'Amy', address: 'Apple st 652'},
    { name: 'Hannah', address: 'Mountain 21'},
    { name: 'Michael', address: 'Valley 345'},
    { name: 'Sandy', address: 'Ocean blvd 2'},
    { name: 'Betty', address: 'Green Grass 1'},
    { name: 'Richard', address: 'Sky st 331'},
    { name: 'Susan', address: 'One way 98'},
    { name: 'Vicky', address: 'Yellow Garden 2'},
    { name: 'Ben', address: 'Park Lane 38'},
    { name: 'William', address: 'Central st 954'},
    { name: 'Chuck', address: 'Main Road 989'},
    { name: 'Viola', address: 'Sideway 1633'}
  ];
  dbo.collection("customers").insertMany(myobjects, function(err, response){
    if(err) throw err;
    console.log("Número de documentos insertados: " + response.insertedCount);
    db.close();
  }); */

/**
 * NOTA: Con insertMany, se devuelve un objeto resultado. Este objeto contiene
 * información sobre como ha afectado la inserción a la base de datos..
 * Propiedades del objeto:
 * result: {ok: 1, n: 14 },
 * ops: [{name: ...} ...]
 * insertedCount: 14,
 * insertedIds: [ids de todos los docs insertados]
 * 
 * Podemos acceder a estas propiedades así:
 * console.log(response.insertedCount) ---> 14
 * 
 * Campo _id: Si no especificas el campo _id, MongoDb añadirá un id unico
 * a cada documento. Si especificas este campo, el valor debe ser único para
 * cada documento. 
 */

});