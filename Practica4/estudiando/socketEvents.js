var http = require("http");
var fs = require("fs");
//Eventos con Socket.IO
var socketIO = require("socket.io");

var httpServer = http.createServer(
    function(request, response){
        fs.readFile("archivo1.html", function(err, data){
            response.writeHead(200, {"Content-Type": "text/html"});
            response.write(data);
            response.end();
        });
    }
);
//Inicializamos una nueva instancia de socket.io con nuestro httpServer.
var io = socketIO(httpServer);
//Y escucamos en el evento "connection" para recibir sockets y escribirlo por pantalla 
io.on("connection", (socket) => {
    console.log("Usuario conectado");
    //Escribe el mensaje cuando lo reciba:
    socket.on("chat message", (msg) => {
        //console.log("Mensaje: " + msg);
        //Queremos que el mensaje se envíe a todo el mundo, asi que usaremos emit
        io.emit("chat message", msg);
    });
    //Añadimos un evento de desconexion
    socket.on("disconnect", () => {
        console.log("Usuario desconectado");
    });
});


httpServer.listen(8080);
console.log("Servicio Socket.IO iniciado");