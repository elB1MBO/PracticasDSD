var http = require("http");
var fs = require("fs");
//Paquete para palabras mayusculas
var uc = require("upper-case"); 

var httpServer = http.createServer(
    function(request, response){
        fs.readFile("archivo1.html", function(err, data){
            response.writeHead(200, {"Content-Type": "text/html"});
            response.write(uc.upperCase("Hola mundo"));
            response.end();
        });
    }
);

httpServer.listen(8080);
console.log("Servicio Socket.IO iniciado");