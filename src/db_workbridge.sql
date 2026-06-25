-- ============================================================
-- BASE DE DATOS: workbridge_db
-- Descripción: Plataforma de empleo que conecta trabajadores
--              con empresas reclutadoras.
-- ============================================================

CREATE DATABASE workbridge_db;
USE workbridge_db;

-- ============================================================
-- TABLA: usuarios
-- Tabla central del sistema. Almacena todos los usuarios
-- sin importar su rol (trabajador, reclutador o admin).
-- ============================================================
CREATE TABLE usuarios (
    id CHAR(36) PRIMARY KEY,                        -- UUID único por usuario
    nombre VARCHAR(80) NOT NULL,                    -- Nombre del usuario
    apellido VARCHAR(80) NOT NULL,                  -- Apellido del usuario
    email VARCHAR(150) NOT NULL UNIQUE,             -- Correo único, se usa para login
    password_hash VARCHAR(255) NOT NULL,            -- Contraseña encriptada (nunca texto plano)
    telefono VARCHAR(20),                           -- Teléfono opcional
    departamento VARCHAR(80),                       -- Ubicación: departamento
    municipio VARCHAR(80),                          -- Ubicación: municipio
    foto_url VARCHAR(500),                          -- URL de la foto de perfil
    rol ENUM('trabajador', 'reclutador', 'admin')   -- Define qué puede hacer el usuario
        NOT NULL DEFAULT 'trabajador',              -- Por defecto es trabajador
    creado_en DATETIME DEFAULT CURRENT_TIMESTAMP,           -- Fecha de registro automática
    actualizado_en DATETIME DEFAULT CURRENT_TIMESTAMP       -- Se actualiza sola al editar
        ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================================
-- TABLA: empresas
-- Perfil de empresa vinculado a un usuario con rol reclutador.
-- Una empresa pertenece a un usuario específico.
-- ============================================================
CREATE TABLE empresas (
    id CHAR(36) PRIMARY KEY,                        -- UUID único por empresa
    usuario_id CHAR(36) NOT NULL,                   -- FK: usuario dueño de la empresa
    nombre_empresa VARCHAR(150) NOT NULL,           -- Nombre legal de la empresa
    nit VARCHAR(30) UNIQUE,                         -- NIT único (puede ser nulo si no aplica)
    sector VARCHAR(100),                            -- Rubro: tecnología, salud, educación, etc.
    descripcion TEXT,                               -- Descripción larga de la empresa
    sitio_web VARCHAR(300),                         -- URL del sitio web
    logo_url VARCHAR(500),                          -- URL del logo
    estado_verificacion ENUM(                       -- Estado de verificación por el admin
        'pendiente',                                -- Recién creada, sin revisar
        'verificada',                               -- Aprobada por admin
        'rechazada'                                 -- No aprobada
    ) NOT NULL DEFAULT 'pendiente',
    verificado_en DATETIME,                         -- Fecha en que fue verificada
    creado_en DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) -- Un usuario puede tener una empresa
);

-- ============================================================
-- TABLA: perfiles_trabajador
-- Perfil profesional de un usuario trabajador.
-- Cada usuario solo puede tener UN perfil (UNIQUE en usuario_id).
-- ============================================================
CREATE TABLE perfiles_trabajador (
    id CHAR(36) PRIMARY KEY,                        -- UUID del perfil
    usuario_id CHAR(36) NOT NULL UNIQUE,            -- FK único: 1 usuario = 1 perfil
    resumen TEXT,                                   -- Descripción profesional del trabajador
    nivel_experiencia ENUM(                         -- Nivel profesional del trabajador
        'sin_experiencia',
        'junior',
        'semi_senior',
        'senior'
    ) NOT NULL,
    modalidad_preferida ENUM(                       -- Cómo prefiere trabajar
        'presencial',
        'remoto',
        'hibrido'
    ),
    disponible TINYINT(1) NOT NULL DEFAULT 1,       -- 1 = disponible, 0 = no disponible
    actualizado_en DATETIME DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- ============================================================
-- TABLA: habilidades
-- Catálogo global de habilidades disponibles en la plataforma.
-- Se usa tanto para perfiles de trabajadores como para vacantes.
-- ============================================================
CREATE TABLE habilidades (
    id CHAR(36) PRIMARY KEY,                        -- UUID de la habilidad
    nombre VARCHAR(100) NOT NULL UNIQUE,            -- Ej: "Python", "Photoshop", "SQL"
    categoria VARCHAR(80)                           -- Agrupa: "Programación", "Diseño", etc.
);

-- ============================================================
-- TABLA: perfil_habilidades
-- Tabla intermedia que relaciona perfiles de trabajador
-- con sus habilidades. Un perfil puede tener muchas
-- habilidades y cada una tiene un nivel definido.
-- ============================================================
CREATE TABLE perfil_habilidades (
    perfil_id CHAR(36) NOT NULL,                    -- FK: perfil del trabajador
    habilidad_id CHAR(36) NOT NULL,                 -- FK: habilidad del catálogo
    nivel ENUM('basico', 'intermedio', 'avanzado')  -- Nivel del trabajador en esa habilidad
        NOT NULL,
    PRIMARY KEY (perfil_id, habilidad_id),          -- Clave compuesta: no se repite la combinación
    FOREIGN KEY (perfil_id) REFERENCES perfiles_trabajador(id),
    FOREIGN KEY (habilidad_id) REFERENCES habilidades(id)
);

-- ============================================================
-- TABLA: vacantes
-- Ofertas de trabajo publicadas por empresas.
-- Cada vacante pertenece a una empresa y fue creada
-- por un usuario reclutador específico.
-- ============================================================
CREATE TABLE vacantes (
    id CHAR(36) PRIMARY KEY,                        -- UUID de la vacante
    empresa_id CHAR(36) NOT NULL,                   -- FK: empresa que publica
    creado_por CHAR(36) NOT NULL,                   -- FK: usuario que creó la vacante
    titulo VARCHAR(150) NOT NULL,                   -- Nombre del puesto
    descripcion TEXT,                               -- Detalle de responsabilidades
    modalidad ENUM(                                 -- Forma de trabajo ofrecida
        'presencial',
        'remoto',
        'hibrido'
    ) NOT NULL,
    departamento VARCHAR(80),                       -- Ubicación de la vacante
    municipio VARCHAR(80),
    salario_min DECIMAL(10,2),                      -- Rango salarial mínimo (opcional)
    salario_max DECIMAL(10,2),                      -- Rango salarial máximo (opcional)
    nivel_experiencia ENUM(                         -- Nivel requerido para el puesto
        'sin_experiencia',
        'junior',
        'semi_senior',
        'senior'
    ),
    estado ENUM(                                    -- Estado actual de la vacante
        'activa',                                   -- Recibiendo postulaciones
        'pausada',                                  -- Temporalmente no disponible
        'cerrada',                                  -- Proceso terminado
        'expirada'                                  -- Pasó la fecha límite
    ) NOT NULL DEFAULT 'activa',
    publicado_en DATETIME DEFAULT CURRENT_TIMESTAMP,-- Fecha de publicación automática
    expira_en DATETIME,                             -- Fecha límite para postular
    FOREIGN KEY (empresa_id) REFERENCES empresas(id),
    FOREIGN KEY (creado_por) REFERENCES usuarios(id)
);

-- ============================================================
-- TABLA: vacante_habilidades
-- Tabla intermedia que define qué habilidades requiere
-- cada vacante. Permite marcar si son obligatorias o deseables.
-- ============================================================
CREATE TABLE vacante_habilidades (
    vacante_id CHAR(36) NOT NULL,                   -- FK: vacante
    habilidad_id CHAR(36) NOT NULL,                 -- FK: habilidad requerida
    requerida TINYINT(1) NOT NULL DEFAULT 1,        -- 1 = obligatoria, 0 = deseable
    PRIMARY KEY (vacante_id, habilidad_id),         -- No se repite la misma habilidad en la misma vacante
    FOREIGN KEY (vacante_id) REFERENCES vacantes(id),
    FOREIGN KEY (habilidad_id) REFERENCES habilidades(id)
);

-- ============================================================
-- TABLA: postulaciones
-- Registro de cuando un trabajador aplica a una vacante.
-- La constraint UNIQUE evita que el mismo trabajador
-- se postule dos veces a la misma vacante.
-- ============================================================
CREATE TABLE postulaciones (
    id CHAR(36) PRIMARY KEY,                        -- UUID de la postulación
    vacante_id CHAR(36) NOT NULL,                   -- FK: vacante a la que aplica
    perfil_id CHAR(36) NOT NULL,                    -- FK: perfil del trabajador
    estado ENUM(                                    -- Estado del proceso de selección
        'enviada',                                  -- Recién postulado
        'en_revision',                              -- Reclutador la está revisando
        'aceptada',                                 -- Seleccionado
        'rechazada'                                 -- No seleccionado
    ) NOT NULL DEFAULT 'enviada',
    carta_presentacion TEXT,                        -- Mensaje opcional del trabajador
    cv_url VARCHAR(500),                            -- Enlace al CV adjunto
    postulado_en DATETIME DEFAULT CURRENT_TIMESTAMP,
    actualizado_en DATETIME DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_postulacion UNIQUE (vacante_id, perfil_id), -- 1 postulación por vacante por perfil
    FOREIGN KEY (vacante_id) REFERENCES vacantes(id),
    FOREIGN KEY (perfil_id) REFERENCES perfiles_trabajador(id)
);

-- ============================================================
-- TABLA: entrevistas
-- Entrevistas agendadas a partir de una postulación aceptada.
-- Una postulación puede generar una o más entrevistas.
-- ============================================================
CREATE TABLE entrevistas (
    id CHAR(36) PRIMARY KEY,                        -- UUID de la entrevista
    postulacion_id CHAR(36) NOT NULL,               -- FK: postulación relacionada
    fecha_hora DATETIME NOT NULL,                   -- Cuándo es la entrevista
    modalidad ENUM('presencial', 'en_linea') NOT NULL, -- Forma de la entrevista
    enlace_reunion VARCHAR(500),                    -- URL de videollamada si es en línea
    notas TEXT,                                     -- Notas internas del reclutador
    estado ENUM(                                    -- Estado de la entrevista
        'agendada',                                 -- Programada pero no realizada
        'completada',                               -- Ya se realizó
        'cancelada'                                 -- Fue cancelada
    ) NOT NULL DEFAULT 'agendada',
    FOREIGN KEY (postulacion_id) REFERENCES postulaciones(id)
);

-- ============================================================
-- TABLA: documentos
-- Archivos subidos por los usuarios: CVs, certificados, etc.
-- Un usuario puede tener múltiples documentos.
-- ============================================================
CREATE TABLE documentos (
    id CHAR(36) PRIMARY KEY,                        -- UUID del documento
    usuario_id CHAR(36) NOT NULL,                   -- FK: usuario dueño del documento
    tipo ENUM(                                      -- Tipo de documento
        'cv',
        'carta_recomendacion',
        'certificado',
        'otro'
    ) NOT NULL,
    nombre_archivo VARCHAR(255) NOT NULL,           -- Nombre original del archivo
    url VARCHAR(500) NOT NULL,                      -- Enlace de acceso al archivo
    subido_en DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- ============================================================
-- TABLA: notificaciones
-- Alertas generadas por el sistema para informar al usuario
-- sobre eventos relevantes: postulaciones, entrevistas, etc.
-- ============================================================
CREATE TABLE notificaciones (
    id CHAR(36) PRIMARY KEY,                        -- UUID de la notificación
    usuario_id CHAR(36) NOT NULL,                   -- FK: usuario que recibe la notificación
    tipo ENUM(                                      -- Tipo de evento que la generó
        'nueva_postulacion',
        'postulacion_aceptada',
        'postulacion_rechazada',
        'entrevista_agendada',
        'vacante_relevante',
        'empresa_verificada',
        'mensaje'
    ) NOT NULL,
    titulo VARCHAR(150) NOT NULL,                   -- Título corto de la notificación
    mensaje TEXT,                                   -- Detalle de la notificación
    leida TINYINT(1) NOT NULL DEFAULT 0,            -- 0 = no leída, 1 = leída
    referencia_id CHAR(36),                         -- ID del elemento relacionado (sin FK para flexibilidad)
    creado_en DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- ============================================================
-- TABLA: publicaciones
-- Contenido publicado por usuarios, similar a una red social.
-- Permite posts, artículos y anuncios de empleo.
-- ============================================================
CREATE TABLE publicaciones (
    id CHAR(36) PRIMARY KEY,                        -- UUID de la publicación
    usuario_id CHAR(36) NOT NULL,                   -- FK: usuario que publica
    contenido TEXT NOT NULL,                        -- Texto de la publicación
    imagen_url VARCHAR(500),                        -- Imagen opcional adjunta
    tipo ENUM('post', 'articulo', 'anuncio')        -- Tipo de publicación
        NOT NULL DEFAULT 'post',
    creado_en DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);