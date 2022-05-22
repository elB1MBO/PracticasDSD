var http = require("http");
var socketIO = require("socket.io");
//El módulo FS nos permite trabajar con el sistema de archivos d enuestro ordenador
//Con esto, podremos leer, crear, actualizar, borrar y renombrar archivos.
var fs = require("fs");
//Este modulo convierte una dirección web en partes legibles
//var url = require("url");

//Con el método createServer, convertimos nuestro ordenador en un servidor HTTP
var httpServer = http.createServer(
    function(request, response){
        //Con parse se devuelve un objeto URL con cada parte de la direccion como propiedad
        var q = require("url").parse(request.url, true);
        //Guardamos el nombre del objeto URL (pathname)
        var filename = "." + q.pathname;
        fs.readFile(filename, function(err, data){
            if(err){
                response.writeHead(404, {"Content-Type": "text/html"});
                return response.end("404 Not Found");
            }
            response.writeHead(200, {"Content-Type": "text/html"});
			response.write(data);
			return response.end();
        });
    }
);

//Creamos una instancia de un objeto Socket.io
var io = socketIO(httpServer);
//Escuchamos el evento de conexion
io.on("connection", (socket) => {
    //console.log("Usuario conectado");
    //Cambia la temperatura cuando recibe:
    socket.on("change temp", (temp) => {
        console.log("Nueva temperatura: " + temp);
        io.emit("change temp", temp);
    });
    //Cambia la luz cuando recibe:
    socket.on("change luz", (luz) => {
        console.log("Nueva luz: " + luz);
        io.emit("change luz", luz);
    });

    //Dispositivos:
    //Aire acondicionado:
    socket.on("toggle AC", (estado) => {
        console.log("Estado Aire acondicionado: " + estado);
        io.emit("toggle AC", estado);
    });

    //Persiana:
    socket.on("toggle Pers", (estado) => {
        console.log("Estado Persianas: " + estado);
        io.emit("toggle Pers", estado);
    });

    //Evento de desconexion
    socket.on("disconnect", () => {
        console.log("Usuario desconectado");
    })
})

//MONGODB

var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

MongoClient.connect(url, function(err, db){
    var io = socketIO(httpServer);
    
    var dbo = db.db("CasaBD");
    var sensorTemp = {name: "Sensor Temp", temperatura: "20º"};
    
});

//Le decimos que escuche el puerto 8080
httpServer.listen(8080);
console.log("Servicio Socket.io iniciado");
