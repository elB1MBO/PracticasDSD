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
//var io = socketIO(httpServer);
//Escuchamos el evento de conexion
/* io.on("connection", (socket) => {
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
    });
}); */

//MONGODB

var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

MongoClient.connect(url, function(err, db){
    var io = socketIO(httpServer);
    httpServer.listen(8081);
    //Le decimos que escuche el puerto 8080
        
    console.log("Servidor escuchando en el puerto 8081");
    
    //Nuestra base de datos
    var dbo = db.db("CasaBD");
    
    //Crearemos una colección para el A/C y otra para las persianas
    /* dbo.createCollection("aire acondicionado", function(err, collection){
        if(err) throw err;
        console.log("Colección " + collection.collectionName + " creada.");
    });
    dbo.createCollection("persianas", function(err, collection){
        if(err) throw err;
        console.log("Colección " + collection.collectionName + " creada.");
    }); */

    //Conexión socket:
    io.sockets.on("connection", function(socket){
        console.log("Conectado.");
        
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
        });

        //Insertamos los datos que escuche en las colecciones:
        //Temperatura:
        socket.on("datos temp", function(data){
            var entrada = {temp:data[0]};
            dbo.collection("aire acondicionado").insertOne(entrada, 
            function(err, res){
                if(err) throw err;
                console.log("Se ha insertado en A/C: Temperatura: "+entrada.temp);
                //Notificamos el cambio
                io.sockets.emit("log", getTimeStamp() + " datos A/C modificados");
            });
        });

        //Estado:
        socket.on("toggle AC", function(data){
            var entrada = {estado:data};
            dbo.collection("aire acondicionado").insertOne(entrada, 
            function(err, res){
                if(err) throw err;
                console.log("Se ha insertado en A/C: Estado: "+entrada.estado);
                //Notificamos el cambio
                io.sockets.emit("log", getTimeStamp() + " -- Aire acondicionado: "+entrada.estado);
            });
        });

        //Luz:
        socket.on("datos luz", function(data){
            var entrada = {luz:data[0]};
            dbo.collection("persianas").insertOne(entrada, 
            function(err, res){
                if(err) throw err;
                console.log("Se ha insertado en Persianas: Luz: "+entrada.luz);
                //Notificamos el cambio
                io.sockets.emit("log", getTimeStamp() + " datos Persianas modificados");
            });
        });

        //Estado:
        socket.on("toggle Pers", function(data){
            var entrada = {estado:data};
            dbo.collection("persianas").insertOne(entrada, 
            function(err, res){
                if(err) throw err;
                console.log("Se ha insertado en Persianas: Estado: "+entrada.estado);
                //Notificamos el cambio
                io.sockets.emit("log", getTimeStamp() + " -- Persianas: "+entrada.estado);
            });
        });

        socket.on("aviso", function(sensor){
            var msg = "AVISO: valor de "+sensor+" ha superado el umbral.";
            console.log(msg);
            io.socket.emit("aviso", msg);
        });

        socket.on("accion agente", function(sensor){
            var msg = "";
            if(sensor === "A/C"){
                msg = "Temperatura supera el umbral: activando el aire acondicionado.";                
            }else{
                //Entiendo que activar las persianas significaría bajarlas para que entre menos luz.
                msg = "Luz supera el umbral: activando las persianas.";
            }
            console.log(msg);
            io.sockets.emit("aviso", msg);
        });

    });
    
});

//Funcion para obtener la fecha
function getTimeStamp(){
    var date       = new Date();
    var dia        = checkDate(date.getDate());
    var mes        = checkDate(date.getMonth());
    var fecha      = dia + "/" + mes + "/" + date.getFullYear();
    var horas      = checkDate(date.getHours());
    var minutos    = checkDate(date.getMinutes());
    var segundos   = checkDate(date.getSeconds());
    var hora       = horas + ":" + minutos + ":" + segundos;
    date           = fecha + " - " + hora;
  
    return date;
}

//Si la fecha es menor que 10, le añade un 0 delante
function checkDate(date){
    var f_date = date;
    if(date<10) f_date="0"+date;
    return f_date;
}


console.log("Servicio Socket.io iniciado");
