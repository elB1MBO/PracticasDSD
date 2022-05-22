var http = require("http");
var url = require("url");
var fs = require("fs");


httpServer = http.createServer(
    function(request, response){
        var q = url.parse(request.url, true);
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

httpServer.listen(8080);
console.log("Servicio URL iniciado");