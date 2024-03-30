import express from 'express';
import fs, { read } from "fs";

//esto es un parseador de body json que se pasa por las peticiones post
import bodyParser from "body-parser";


const app = express();

app.use(bodyParser.json());

/**
 * Lee datos de la base de datos ficticia simulacion-db.json. readFileSync es
 * una función asíncrona que nos lee el contenido de un archivo, pero lo hace
 * como un stream de bytes, así que el valor de data tenemos que parsearlo a json
 * mediante JSON.parse(data)
 */
const readData = () => {
    try {
        const data = fs.readFileSync("./simulacion-db.json");
        return JSON.parse(data);
    } catch (error) {
        console.log(error);
    }
}

/**
 * Igual que el read, pero write (escribirá el data en el archivo de simulacion-db)
 */
const writeData = (data) => {
    try {
        fs.writeFileSync("./simulacion-db.json", JSON.stringify(data));
    } catch (error) {
        console.log(error);
    }
}

/*ENDPOINT DE GET (READ) ---------------------------------------------------- */
//Definimos un endpoint de get básico (trae todo)
app.get("/colores", (req, res) => {
    //obtiene el json de la base de datos.
    const data = readData();
    //prepara una respuesta con el nodo arrayColores (podría se otro nodo)
    //que será escuchable a través de localhost:3000/colores
    res.json(data.arrayColores);

});

//Definimos un endpoint de get para obtener color por nombre. 
//el /book/:nombreColor hace que nombreColor se pase como un parámetro de get
//a través de la url. Para obtener el valor traido por el get, hacemos req.params.nombreColor
app.get("/colores/:nombreColor", (req, res) => {
    //leemos todos los colores
    const data = readData();
    //obtenemos el filtro de identificador de color traído por el get a través de la url
    const id = req.params.nombreColor;
    //buscamos en el array el color que estamos buscando usando el find
    const color = data.arrayColores.find((color) => color.nombreColor === id);
    //preparamos la respuesta, que será visible en el navegador mediante
    //la url localhost:3000/colores/rojo (en este caso el rojo)
    res.json(color);
});




/*ENDPOINT DE POST (CREATE) ------------------------------------------------- */
//Definimos un endpoint para crear y guardar un nuevo color en el archivo simulacion-db.json.
//En este caso, al ser una "envío" (ya no estamos haciendo una petición, si no una emisión),
//se hace usando post.
app.post("/colores", (req, res) =>{
    //leemos los datos que hay actualmente en la base de datos ficticia, y la guardamos en en una variable data.
    const data = readData();
    //obtenemos los datos proporcionados en el post, que son los que queremos guardar en la base de datos
    const body = req.body;
    //creamos un nuevo objeto color con los atributos recibido en el post.
    const newColor = {
        //en este caso, guardamos todos los atributos recibido por el post en un nuevo objeto color,
        //pero bien podríamos usar solo algunos atributos e insertar otros de manera manual (como un
        //id autoincremental, etc). hay que tener en cuenta, que si hay conflicto entre dos
        //asignaciones de valor (body.id vs id puesta manualmente por ejemplo), se aplica cascada (lo
        //último asignado determina el valor)
        //los tres puntos se llama spread operator, y va a tomar los valores pasados por el post.
        ...body
    };
    //ahora, añadimos el nuevo color al nodo arrayColores de la variabla data
    //con contiene la estructura de la base de datos
    data.arrayColores.push(newColor);

    //por último, escribimos data (que ya incluye el nuevo color) en la base de datos
    writeData(data);

    //simulamos una respuesta para ver que se ha aplicado el cambio, mostrando el nuevo color
    res.json(newColor);
});

/*ENDPOINT DE PUT (UPDATE) -------------------------------------------------- */
//Definimos un endopoint de actualización de un registro existente, pasándole la id
//del registro que se va a modificar, y mandando un body con los nuevos datos
app.put("/colores/:id", (req, res) =>{
    const data = readData();    
    //obtenemos la id del color a modificar
    const id = req.params.nombreColor;
    //guardarmos los parámetros pasados por body
    const body = req.body;
    //localizamos el index del color que vamos a modificar
    const colorIndex = data.arrayColores.findIndex((color)=> color.nombreColor === id);
    //modificamos el color gracias al index previamente obtenido
    data.arrayColores[colorIndex] = {
        //primero ponemos los valores que ya hubiese
        ...data.arrayColores[colorIndex],
        //segundo, ponemos los nuevos valores (solo se modificarán los valores que
        //se hayan pasado al body)
        ...body
    }

    //por último, volvemos a escribir todos los datos en la base de datos
    writeData(data);

    //simuladmos una respuesta de confirmación
    res.json({message: "Color actualizado correctamente"});
});


/*ENDPOINT DE DELETE (DELETE) ----------------------------------------------- */
//Definimos el endpoint de delete, para borrar un registro
app.delete("/colores/:id", (req, res) =>{
    const data = readData(); 
    //obtenemos la id del color      
    const id = req.params.nombreColor;
    //obtenemos la posición del índice en el array del color a borrar
    const colorIndex = data.arrayColores.findIndex((color)=> color.nombreColor === id);
    //eliminamos el color
    data.arrayColores.splice(colorIndex, 1);
    //reescribimos todos los datos en la base de datos
    writeData(data);
    //simulamos una respuesta
    res.json({message: "Color borrado correctamente"});
});

app.listen(3000, () => {
    console.log('Server listening on port 3000');
});



