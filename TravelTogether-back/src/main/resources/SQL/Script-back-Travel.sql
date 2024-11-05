-- Crear la tabla Usuarios
CREATE TABLE Usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100)
    
);

-- Crear la tabla Registro
CREATE TABLE Registro (
    id_registro SERIAL PRIMARY KEY,
    id_usuario INT,
    email VARCHAR(100) UNIQUE,
    contrasena VARCHAR(100),
    fecha_registro DATE,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);


-- Crear la tabla Grupos
CREATE TABLE Grupos (
    id_grupo SERIAL PRIMARY KEY,
    integrantes INT,
    nombre_grupo VARCHAR(100),
    descripcion VARCHAR(255),
    fecha_creacion DATE,
    id_admin INT REFERENCES Usuarios(id_usuario) NOT NULL, 
    FOREIGN KEY (integrantes) REFERENCES Usuarios(id_usuario)
);

-- Crear la tabla Usuarios_Grupos
CREATE TABLE Usuarios_Grupos (
    id_usuario INT,
    id_grupo INT,
    PRIMARY KEY (id_usuario, id_grupo),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_grupo) REFERENCES Grupos(id_grupo)
);

-- Crear la tabla Actividades
CREATE TABLE Actividades (
    id_actividad SERIAL PRIMARY KEY,
    id_usuario INT,
    nombre_actividad VARCHAR(100),
    descripcion VARCHAR(255),
    id_grupo INT,
    fecha_actividad DATE,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_grupo) REFERENCES Grupos(id_grupo)
);

-- Crear la tabla Chat
CREATE TABLE Chat (
    id_mensaje SERIAL PRIMARY KEY,
    id_grupo INT,
    id_usuario INT,
    mensaje VARCHAR(255),
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_grupo) REFERENCES Grupos(id_grupo),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Crear la tabla Itinerario
CREATE TABLE Itinerario (
    id_itinerario SERIAL PRIMARY KEY,
    id_actividad INT,
    fecha_itinerario DATE,
    hora_inicio TIME,
    hora_fin TIME,
    ubicacion VARCHAR(255),
    descripcion_detallada VARCHAR(255),
    FOREIGN KEY (id_actividad) REFERENCES Actividades(id_actividad)
);

-- Crear la tabla Amigos
CREATE TABLE Amigos (
    id_usuario1 INT REFERENCES Usuarios(id_usuario) ON DELETE CASCADE,
    id_usuario2 INT REFERENCES Usuarios(id_usuario) ON DELETE CASCADE,
    PRIMARY KEY (id_usuario1, id_usuario2),
    CHECK (id_usuario1 <> id_usuario2) 
);

-- Crear la tabla Votos
CREATE TABLE Votos (
    id_actividad INT REFERENCES Actividades(id_actividad) ON DELETE CASCADE,
    id_usuario INT REFERENCES Usuarios(id_usuario) ON DELETE CASCADE,
    tipo_voto BOOLEAN,
    fecha_voto TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_actividad, id_usuario)
);

