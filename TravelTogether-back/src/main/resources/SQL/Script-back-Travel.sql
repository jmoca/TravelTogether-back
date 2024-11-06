-- Crear la tabla Usuarios
CREATE TABLE Usuario (
                         id_usuario SERIAL PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL
);

-- Crear la tabla Registro
CREATE TABLE Registro (
                          id_registro SERIAL PRIMARY KEY,
                          id_usuario INTEGER REFERENCES Usuario(id_usuario) ON DELETE CASCADE NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          contrasena VARCHAR(100) NOT NULL,
                          fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear la tabla Grupos
CREATE TABLE Grupos (
                        id_grupo SERIAL PRIMARY KEY,
                        nombre_grupo VARCHAR(100) NOT NULL,
                        descripcion VARCHAR(255),
                        fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        id_admin INTEGER REFERENCES Usuario(id_usuario) ON DELETE SET NULL
);

-- Crear la tabla Usuarios_Grupos
CREATE TABLE Usuarios_Grupos (
                                 id_usuario INTEGER REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
                                 id_grupo INTEGER REFERENCES Grupos(id_grupo) ON DELETE CASCADE,
                                 PRIMARY KEY (id_usuario, id_grupo)
);

-- Crear la tabla Actividades
CREATE TABLE Actividades (
                             id_actividad SERIAL PRIMARY KEY,
                             id_usuario INTEGER REFERENCES Usuario(id_usuario) ON DELETE SET NULL,
                             nombre_actividad VARCHAR(100) NOT NULL,
                             descripcion VARCHAR(255),
                             id_grupo INTEGER REFERENCES Grupos(id_grupo) ON DELETE SET NULL,
                             fecha_actividad DATE NOT NULL
);

-- Crear la tabla Chat
CREATE TABLE Chat (
                      id_mensaje SERIAL PRIMARY KEY,
                      id_grupo INTEGER REFERENCES Grupos(id_grupo) ON DELETE CASCADE,
                      id_usuario INTEGER REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
                      mensaje VARCHAR(255) NOT NULL,
                      fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear la tabla Itinerario
CREATE TABLE Itinerario (
                            id_itinerario SERIAL PRIMARY KEY,
                            id_actividad INTEGER REFERENCES Actividades(id_actividad) ON DELETE CASCADE,
                            fecha_itinerario DATE NOT NULL,
                            hora_inicio TIMESTAMP,
                            hora_fin TIMESTAMP,
                            ubicacion VARCHAR(255),
                            descripcion_detallada VARCHAR(255)
);


-- Crear la tabla Amigos
CREATE TABLE Amigos (
                        id_usuario1 INTEGER NOT NULL,
                        id_usuario2 INTEGER NOT NULL,
                        PRIMARY KEY (id_usuario1, id_usuario2),
                        CONSTRAINT fk_usuario1 FOREIGN KEY (id_usuario1) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
                        CONSTRAINT fk_usuario2 FOREIGN KEY (id_usuario2) REFERENCES Usuario(id_usuario) ON DELETE CASCADE
);
-- Crear la tabla Votos
CREATE TABLE Votos (
                       id_actividad INTEGER REFERENCES Actividades(id_actividad) ON DELETE CASCADE,
                       id_usuario INTEGER REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
                       tipo_voto BOOLEAN NOT NULL,
                       fecha_voto TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       PRIMARY KEY (id_actividad, id_usuario)
);

-- Insercion de usuarios

INSERT INTO Usuario (nombre) VALUES ('Juan Pérez');
INSERT INTO Usuario (nombre) VALUES ('María López');
INSERT INTO Usuario (nombre) VALUES ('Carlos García');


INSERT INTO travel.grupos (nombre_grupo, descripcion, integrantes, fecha_creacion, ubicacion) VALUES
                                                                                                  ('Grupo A', 'Grupo de amigos', 5, '2024-01-01', 'Madrid'),
                                                                                                  ('Grupo B', 'Grupo de trabajo', 10, '2024-02-01', 'Barcelona'),
                                                                                                  ('Grupo C', 'Grupo familiar', 8, '2024-03-01', 'Valencia');

INSERT INTO Usuarios_Grupos (usuario, grupo) VALUES (1, 1);
INSERT INTO Usuarios_Grupos (usuario, grupo) VALUES (2, 1);
INSERT INTO Usuarios_Grupos (usuario, grupo) VALUES (3, 2);

INSERT INTO Actividades (id_usuario, nombre_actividad, descripcion, id_grupo, fecha_actividad) VALUES (1, 'Reunión de Estudio', 'Reunión para revisar el material del examen.', 1, '2024-11-10');
INSERT INTO Actividades (id_usuario, nombre_actividad, descripcion, id_grupo, fecha_actividad) VALUES (2, 'Presentación de Proyecto', 'Presentación del proyecto final del curso.', 2, '2024-11-12');
INSERT INTO Actividades (id_usuario, nombre_actividad, descripcion, id_grupo, fecha_actividad) VALUES (3, 'Discusión de Libro', 'Reunión para discutir el libro leído.', 3, '2024-11-15');


