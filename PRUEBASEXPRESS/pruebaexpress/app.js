// Importar los módulos necesarios
const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql');

// Crear una instancia de la aplicación Express
const app = express();

// Configurar el middleware para parsear JSON
app.use(bodyParser.json());

// Configurar la conexión a la base de datos MySQL
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'tpv'
});

// Establecer la conexión a la base de datos MySQL
connection.connect((err) => {
    if (err) {
        console.error('Error al conectar a la base de datos:', err);
    } else {
        console.log('Conexión exitosa a la base de datos MySQL');
    }
});

// Ruta para obtener la información de usuarios
app.get('/sync/users', (req, res) => {
    // Consultar la base de datos MySQL para obtener la información de usuarios
    const sql = 'SELECT * FROM users';
    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de usuarios' });
        } else {
            // Enviar la información de usuarios al cliente en formato JSON
            res.json(result);
        }
    });
});

// Ruta para obtener la información de artículos
app.get('/sync/articles', (req, res) => {
    // Consultar la base de datos MySQL para obtener la información de usuarios
    const sql = 'SELECT * FROM articles';
    connection.query(sql, (err, result) => {
        if (err) {
            console.error('Error al ejecutar la consulta:', err);
            res.status(500).json({ error: 'Error al obtener la información de artículos' });
        } else {
            // Enviar la información de usuarios al cliente en formato JSON
            res.json(result);
        }
    });
});

// Configurar el puerto en el que la aplicación escuchará las solicitudes
const puerto = 3000;
app.listen(puerto, () => {
    console.log(`Servidor en ejecución en http://localhost:${puerto}`);
});
