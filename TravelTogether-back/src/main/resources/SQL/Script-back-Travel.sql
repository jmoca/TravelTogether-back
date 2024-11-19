-- Tabla usuario
CREATE TABLE usuario (
                         id_usuario SERIAL PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL
);

-- Tabla registro
CREATE TABLE registro (
                          id_registro SERIAL PRIMARY KEY,
                          contrasena VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          fecha_registro DATE NOT NULL,
                          id_usuario INT REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla grupos
CREATE TABLE grupos (
                        id_grupo SERIAL PRIMARY KEY,
                        descripcion TEXT,
                        fecha_creacion DATE NOT NULL,
                        integrantes INT NOT NULL,
                        nombre_grupo VARCHAR(100) NOT NULL,
                        ubicacion VARCHAR(100)
);

-- Tabla amigos
CREATE TABLE amigos (
                        id SERIAL PRIMARY KEY,
                        fecha_amistad DATE NOT NULL,
                        id_usuario1 INT REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                        id_usuario2 INT REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla actividades
CREATE TABLE actividades (
                             id_actividad SERIAL PRIMARY KEY,
                             descripcion TEXT,
                             fecha_actividad DATE NOT NULL,
                             lugar VARCHAR(100),
                             multimedia VARCHAR(100),
                             nombre_actividad VARCHAR(100) NOT NULL,
                             id_grupo INT REFERENCES grupos(id_grupo) ON DELETE CASCADE,
                             id_usuario INT REFERENCES usuario(id_usuario) ON DELETE SET NULL
);

-- Tabla votos
CREATE TABLE votos (
                       id_voto SERIAL PRIMARY KEY,
                       fecha_voto DATE NOT NULL,
                       tipo_voto BOOLEAN NOT NULL,
                       id_actividad INT REFERENCES actividades(id_actividad) ON DELETE CASCADE,
                       id_usuario INT REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla itinerario
CREATE TABLE itinerario (
                            id_itinerario SERIAL PRIMARY KEY,
                            descripcion_detallada TEXT,
                            fecha_itinerario DATE NOT NULL,
                            hora_inicio TIME NOT NULL,
                            hora_fin TIME NOT NULL,
                            ubicacion VARCHAR(100),
                            id_actividad INT REFERENCES actividades(id_actividad) ON DELETE CASCADE
);

-- Tabla chat
CREATE TABLE chat (
                      id_chat SERIAL PRIMARY KEY,
                      fecha_envio DATE NOT NULL,
                      mensaje TEXT NOT NULL,
                      id_grupo INT REFERENCES grupos(id_grupo) ON DELETE CASCADE
);

-- Tabla usuarios_grupos (relación muchos a muchos entre usuario y grupos)
CREATE TABLE usuarios_grupos (
                                 grupo INT REFERENCES grupos(id_grupo) ON DELETE CASCADE,
                                 usuario INT REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                                 PRIMARY KEY (grupo, usuario)
);




-- Tabla usuario
CREATE TABLE usuario (
                         id_usuario SERIAL PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL
);

-- Tabla registro
CREATE TABLE registro (
                          id_registro SERIAL PRIMARY KEY,
                          contrasena VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          fecha_registro DATE NOT NULL,
                          id_usuario INT REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla grupos
CREATE TABLE grupos (
                        id_grupo SERIAL PRIMARY KEY,
                        descripcion TEXT,
                        fecha_creacion DATE NOT NULL,
                        integrantes INT NOT NULL,
                        nombre_grupo VARCHAR(100) NOT NULL,
                        ubicacion VARCHAR(100)
);

-- Tabla amigos
CREATE TABLE amigos (
                        id SERIAL PRIMARY KEY,
                        fecha_amistad DATE NOT NULL,
                        id_usuario1 INT REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                        id_usuario2 INT REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla actividades
CREATE TABLE actividades (
                             id_actividad SERIAL PRIMARY KEY,
                             descripcion TEXT,
                             fecha_actividad DATE NOT NULL,
                             lugar VARCHAR(100),
                             multimedia VARCHAR(100),
                             nombre_actividad VARCHAR(100) NOT NULL,
                             id_grupo INT REFERENCES grupos(id_grupo) ON DELETE CASCADE,
                             id_usuario INT REFERENCES usuario(id_usuario) ON DELETE SET NULL
);

-- Tabla votos
CREATE TABLE votos (
                       id_voto SERIAL PRIMARY KEY,
                       fecha_voto DATE NOT NULL,
                       tipo_voto BOOLEAN NOT NULL,
                       id_actividad INT REFERENCES actividades(id_actividad) ON DELETE CASCADE,
                       id_usuario INT REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla itinerario
CREATE TABLE itinerario (
                            id_itinerario SERIAL PRIMARY KEY,
                            descripcion_detallada TEXT,
                            fecha_itinerario DATE NOT NULL,
                            hora_inicio TIME NOT NULL,
                            hora_fin TIME NOT NULL,
                            ubicacion VARCHAR(100),
                            id_actividad INT REFERENCES actividades(id_actividad) ON DELETE CASCADE
);

-- Tabla chat
CREATE TABLE chat (
                      id_chat SERIAL PRIMARY KEY,
                      fecha_envio DATE NOT NULL,
                      mensaje TEXT NOT NULL,
                      id_grupo INT REFERENCES grupos(id_grupo) ON DELETE CASCADE
);

-- Tabla usuarios_grupos (relación muchos a muchos entre usuario y grupos)
CREATE TABLE usuarios_grupos (
                                 grupo INT REFERENCES grupos(id_grupo) ON DELETE CASCADE,
                                 usuario INT REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                                 PRIMARY KEY (grupo, usuario)
);




-- Tabla usuario
INSERT INTO usuario (id_usuario, nombre) VALUES
                                             (1, 'Juan Pérez'),
                                             (2, 'Ana Gómez'),
                                             (3, 'Carlos López'),
                                             (4, 'María Rodríguez'),
                                             (5, 'Luis Martínez');

-- Tabla registro
INSERT INTO registro (id_registro, contrasena, email, fecha_registro, id_usuario) VALUES
                                                                                      (1, 'pass123', 'juan.perez@example.com', '2023-01-01', 1),
                                                                                      (2, 'pass456', 'ana.gomez@example.com', '2023-01-02', 2),
                                                                                      (3, 'pass789', 'carlos.lopez@example.com', '2023-01-03', 3),
                                                                                      (4, 'pass101', 'maria.rodriguez@example.com', '2023-01-04', 4),
                                                                                      (5, 'pass102', 'luis.martinez@example.com', '2023-01-05', 5);

-- Tabla grupos
INSERT INTO grupos (id_grupo, descripcion, fecha_creacion, integrantes, nombre_grupo, id_usuario_creador) VALUES
                                                                                                              (1, 'Grupo de deportes', '2023-01-10', 10, 'Deportistas', '1'),
                                                                                                              (2, 'Grupo de estudio', '2023-01-12', 5, 'Estudiantes', '1'),
                                                                                                              (3, 'Grupo de música', '2023-01-15', 8, 'Músicos', '1'),
                                                                                                              (4, 'Grupo de arte', '2023-01-18', 6, 'Artistas', '1'),
                                                                                                              (5, 'Grupo de tecnología', '2023-01-20', 12, 'Techies', '1');

-- Tabla amigos
INSERT INTO amigos (id, fecha_amistad, id_usuario1, id_usuario2) VALUES
                                                                     (1, '2023-02-01', 1, 2),
                                                                     (2, '2023-02-05', 1, 3),
                                                                     (3, '2023-02-10', 2, 4),
                                                                     (4, '2023-02-15', 3, 5),
                                                                     (5, '2023-02-20', 4, 5);

-- Tabla actividades
INSERT INTO actividades (id_actividad, descripcion, fecha_actividad, lugar, multimedia, nombre_actividad, id_grupo, id_usuario) VALUES
                                                                                                                                    (1, 'Partido de fútbol', '2023-03-01', 'Estadio Municipal', 'futbol.jpg', 'Fútbol', 1, 1),
                                                                                                                                    (2, 'Sesión de estudio de álgebra', '2023-03-05', 'Aula 101', 'algebra.pdf', 'Estudio de Álgebra', 2, 2),
                                                                                                                                    (3, 'Concierto de rock', '2023-03-10', 'Auditorio', 'rock.mp3', 'Concierto', 3, 3),
                                                                                                                                    (4, 'Exposición de pintura', '2023-03-15', 'Galería de Arte', 'pintura.jpg', 'Pintura', 4, 4),
                                                                                                                                    (5, 'Hackathon de programación', '2023-03-20', 'Sala de conferencias', 'hackathon.mp4', 'Hackathon', 5, 5);

-- Tabla votos
INSERT INTO votos (id_voto, fecha_voto, tipo_voto, id_actividad, id_usuario) VALUES
                                                                                 (1, '2023-03-21', true, 1, 1),
                                                                                 (2, '2023-03-22', false, 2, 2),
                                                                                 (3, '2023-03-23', true, 3, 3),
                                                                                 (4, '2023-03-24', true, 4, 4),
                                                                                 (5, '2023-03-25', false, 5, 5);

-- Tabla itinerario


-- Tabla chat
INSERT INTO chat (id_chat, fecha_envio, mensaje, id_grupo) VALUES
                                                               (1, '2023-04-06', '¡Hola a todos!', 1),
                                                               (2, '2023-04-07', '¿Alguien tiene las notas?', 2),
                                                               (3, '2023-04-08', '¿Cuál es el próximo ensayo?', 3),
                                                               (4, '2023-04-09', '¿Alguien se apunta a la exposición?', 4),
                                                               (5, '2023-04-10', '¿Listos para el hackathon?', 5);

-- Tabla usuarios_grupos
INSERT INTO usuarios_grupos (grupo, usuario) VALUES
                                                 (1, 1),
                                                 (1, 2),
                                                 (2, 3),
                                                 (3, 4),
                                                 (4, 5);


SELECT COUNT(*) AS total_actividades
FROM actividades
WHERE id_grupo = 3;

SELECT id_actividad = 1,
       SUM(CASE WHEN tipo_voto = true THEN 1 ELSE 0 END) AS votos_positivos,
       SUM(CASE WHEN tipo_voto = false THEN 1 ELSE 0 END) AS votos_negativos
FROM votos
GROUP BY id_actividad;




--Contar votos

SELECT id_actividad,
       SUM(CASE WHEN tipo_voto = true THEN 1 ELSE -1 END) AS balance_votos
FROM votos
GROUP BY id_actividad;