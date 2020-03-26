-- Table: public.usuario

-- DROP TABLE public.usuario;

CREATE TABLE public.usuario
(
    id integer NOT NULL DEFAULT nextval('usuario_id_seq'::regclass),
    nome character varying(60) COLLATE pg_catalog."default" NOT NULL,
    login character varying(60) COLLATE pg_catalog."default" NOT NULL,
    senha character varying(30) COLLATE pg_catalog."default" NOT NULL,
    "dataAniversario" date,
    ativo boolean NOT NULL,
    CONSTRAINT "Usuario_pkey" PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.usuario
    OWNER to topicos;