/*Cada acción en un ordenador es un evento. Desde una conexión a la apertura 
de un archivo.
Los objetos en Node.js puede lanzar eventos, como readStream que lanza eventos 
cuando se abre y cierra un archivo:
*/


var fs = require("fs");
var rs = fs.createReadStream("./demofile.txt");
rs.on("open", function(){
    console.log("Archivo abierto");
});

//Modulos de eventos
var events = require("events");
//Todas las propiedades y métodso son instancias de un objeto EventEmitter
//Creamos un objeto EventsEmitter:
var eventEmitter = new events.EventEmitter();

/**
 * Se pueden asignar controladores de eventos a nuestros propios eventos con 
 * el objeto EventEmitter. 
 * Creamos una funcion que se ejecutara cuando ocurra un evento "grito"
 * 
 * Para lanzar un evento, se usa el método emit()
 */

//Creamos el controlador de evento:
var myEventHandler = function(){
    console.log("Escuché un grito.");
}

//Asigna el controlador al evento
eventEmitter.on("scream", myEventHandler);

//Lanza el evento scream
eventEmitter.emit("scream");
