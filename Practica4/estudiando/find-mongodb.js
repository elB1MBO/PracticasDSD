var MongoClient = require("mongodb").MongoClient;
var url = "mongodb://localhost:27017/";
/**
 * firstOne devuelve el primer caso en la seleccion
 * El primer parámetro de findOne es el objeto a buscar. En el ejemplo se 
 * usa un objeto vacío, por lo que selecciona todos los documentos de 
 * la colección, aunque findOne devuelve solo el primero que encuentra
 */


MongoClient.connect(url, function(err, db){
    if(err) throw err;
    var dbo = db.db("mydb");
    dbo.collection("customers").findOne({}, function(err, result){
        if(err) throw err;
        console.log(result.name);
        db.close();
    });
});

/**
 * Luego está el método find, que devuelve todos los documentos que coinciden
 * Si usamos como parámetro de búsqueda un objeto vacío, entonces devolverá
 * todos los documentos de la colección
 */

/* MongoClient.connect(url, function(err, db){
    if(err) throw err;
    var dbo = db.db("mydb");
    dbo.collection("customers").find({}).toArray(function(err, result){
        if(err) throw err;
        console.log(result);
        db.close();
    });
}); */

/**
 * El segundo parámetro de find es el objeto proyección, que describe qué campos
 * hay que incluir en el resultado.
 * Este parámetro es opcional, y si se omite, se incluirán todos los campos
 * en el resultado. Si ponemos una propiedad a 0, entonces la ignorara
 * 
 * No podemos especificar 0 y 1 en el mismo objeto (excepto si uno de los campos
 * es el _id). Si especificamos un campo con el valor 0, todos los otros
 * campos tendrán el valor 1, y viceversa.
 * 
 * Si quieres omitir el campo _id, hay que especificarlo
 */

MongoClient.connect(url, function(err, db){
    if(err) throw err;
    var dbo = db.db("mydb");
    dbo.collection("customers").find({}, {
        projection: {_id: 0} //es lo mismo que poner: {_id: 0, name: 1, address: 1}
    }).toArray(function(err, result){
        if(err) throw err;
        console.log(result);
        //Se puede especificar uno de los documentos del resultado con una de sus propiedades:
        console.log(result[2].address);
        db.close();
    });
});

