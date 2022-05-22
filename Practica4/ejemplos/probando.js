var http = require("http");
var url = require("url");
var fs = require("fs");
var path = require("path");
var socketio = require("socket.io");

//Variables de Mongo
var MongoClient = require("mongodb").MongoClient;
var MongoServer = require("mongodb").Server;
//Para usar abreviaciones de algunos tipos
var mimeTypes = { "html": "text/html", "jpeg": "image/jpeg", 
"jpg": "image/jpeg", "png": "image/png", "js": "text/javascript", 
"css": "text/css", "swf": "application/x-shockwave-flash"}
//Creacion del servidor
var httpServer = http.createServer(
    function(request, response){
        var uri = url.parse(request.url).pathname;
        if(uri == "/")
            uri = "/mongo-server.html";
        var fname = path.join(process.cwd(), uri);
        fs.exists(fname, function(exists){
            if (exists) {
                fs.readFile(fname, function (err, data) {
                    if (!err) {
                        var extension = path.extname(fname).split(".")[1];
                        var mimeType = mimeTypes[extension];
                        response.writeHead(200, mimeType);
                        response.write(data);
                        response.end();
                    } else {
                        response.writeHead(200, { "Content-Type": "text/plain" });
                        response.write("Error de lectura en el fichero: " + uri);
                        response.end();
                    }
                });
            } else {
                console.log("Peticion invalida: " + uri);
                response.writeHead(200, { "Content-Type": "text/plain" });
                response.write("404 Not Found\n");
                response.end();
            }
        });
    }
);

//Conexion al mongodb
MongoClient.connect("mongodb://localhost:27017", {useUnifiedTopology: true}, 
function(err, db) {
    httpServer.listen(8080);
    var io = socketio(httpServer);

    var dbo = db.db("BD-Casa");
    dbo.listCollections({name: "test"})
    .next(function(err, collinfo){
        if(collinfo){
            //La coleccion existe
            console.log("La coleccion test ya existe.");
            console.log(collinfo);
            //Conecta a Socket.io
            io.sockets.on('connection', function(){
                let chat = dbo.collections("test");
                
                //Crea una función para mandar el estado
                sendStatus = function(status){
                    socketio.emit("status", status);
                }

                //Obtiene los datos de tests de la coleccion de mongo
                chat.find().limit(100).sort({_id:1}).toArray(function(err, res){
                    if(err){
                        throw err;
                    }

                    //Emite los mensajes
                    socketio.emit('output', res);
                });
                
                //Maneja los eventos de entrada
                socketio.on('input', function(data){
                    let name = data.name;
                    let message = data.message;

                    //Comprueba el name y el mensaje
                    if(name == '' || message == ''){
                        //Envia mensaje de error
                        sendStatus("Por favor, introduce un nombre y mensaje");
                    } else {
                        //Inserta el mensaje
                        chat.insert({name: name, message: message}, function(){
                            io.socketio.emit('output', [data]);

                            //Manda el estado del objeto
                            sendStatus({
                                message: "Mensaje enviado",
                                clear: true
                            });
                        });
                    }
                });

                //Clear:
                socketio.on('clear', function(data){
                    //Borra todos los chats de la coleccion
                    chat.remove({}, function(){
                        //Emite que ha borrado
                        socketio.emit('cleared');
                    })
                })
            });
        }
        //La coleccion no existe, luego la crea:
        dbo.createCollection("test", function (err, collection) {
            io.sockets.on('connection',
                function (client) {
                    client.emit('my-address', {
                        name: "Usuario 1", message: "Hola buenas"
                    });
                    client.on('poner', function (data) {
                        collection.insert(data, { safe: true }, function (err, result) { });
                    });
                    client.on('obtener', function (data) {
                        collection.find(data).toArray(function (err, results) {
                            client.emit('obtener', results);
                        });
                    });
                }
            );
        });
        
        /* 
        if(collinfo){
            //La coleccion existe
            console.log("La coleccion test ya existe");
            console.log(collinfo);
            dbo.collection("customers").updateOne(myquery, newvalues, function(err, res) {
                if (err) throw err;
                console.log("1 document updated");
                db.close();
            });
        }else{
            //La coleccion no existe, luego la crea:
            dbo.createCollection("test", function(err, collection){
                io.sockets.on('connection', 
                function(client){
                    client.emit('my-address', {
                        id:"Usuario 1", port:client.request.connection.remotePort 
                    });
                    client.on('poner', function(data){
                        collection.insert(data, {safe:true}, function(err, result){});
                    });
                    client.on('obtener', function(data){
                        collection.find(data).toArray(function(err, results){
                            client.emit('obtener', results);
                        });
                    });
                });
            });
        } */
    }); 
});

console.log("Servicio MongoDB iniciado");