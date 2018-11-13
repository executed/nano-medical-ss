--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.9
-- Dumped by pg_dump version 9.6.9

-- Started on 2018-11-13 21:44:41 EET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE medical_center;
--
-- TOC entry 2215 (class 1262 OID 16387)
-- Name: medical_center; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE medical_center WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';


ALTER DATABASE medical_center OWNER TO postgres;

\connect medical_center

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12431)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2218 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 2 (class 3079 OID 16449)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2219 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 186 (class 1259 OID 16466)
-- Name: client; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    first_name text,
    last_name text
);


ALTER TABLE public.client OWNER TO admin;

--
-- TOC entry 190 (class 1259 OID 24604)
-- Name: client_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_config (
    id uuid NOT NULL,
    email character varying(100) NOT NULL,
    password text NOT NULL,
    admin boolean NOT NULL,
    username text
);


ALTER TABLE public.client_config OWNER TO admin;

--
-- TOC entry 187 (class 1259 OID 16476)
-- Name: doctor; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.doctor (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    first_name text,
    last_name text
);


ALTER TABLE public.doctor OWNER TO admin;

--
-- TOC entry 188 (class 1259 OID 24576)
-- Name: doctor_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.doctor_config (
    id uuid NOT NULL,
    start_work timestamp without time zone NOT NULL,
    end_work timestamp without time zone NOT NULL,
    max_app_duration integer,
    max_app_not_fixed boolean NOT NULL
);


ALTER TABLE public.doctor_config OWNER TO admin;

--
-- TOC entry 189 (class 1259 OID 24587)
-- Name: time_slot; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.time_slot (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    client_id uuid NOT NULL,
    doctor_id uuid NOT NULL,
    start_time timestamp without time zone NOT NULL,
    end_time timestamp without time zone NOT NULL
);


ALTER TABLE public.time_slot OWNER TO admin;

--
-- TOC entry 2076 (class 2606 OID 16474)
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- TOC entry 2082 (class 2606 OID 24580)
-- Name: doctor_config doctor_config_clone_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctor_config
    ADD CONSTRAINT doctor_config_clone_pkey PRIMARY KEY (id);


--
-- TOC entry 2079 (class 2606 OID 16484)
-- Name: doctor doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (id);


--
-- TOC entry 2085 (class 2606 OID 24592)
-- Name: time_slot time_slot_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.time_slot
    ADD CONSTRAINT time_slot_pkey PRIMARY KEY (id);


--
-- TOC entry 2086 (class 1259 OID 24616)
-- Name: client_config_email_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX client_config_email_uindex ON public.client_config USING btree (email);


--
-- TOC entry 2087 (class 1259 OID 24615)
-- Name: client_config_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX client_config_id_uindex ON public.client_config USING btree (id);


--
-- TOC entry 2088 (class 1259 OID 24617)
-- Name: client_config_username_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX client_config_username_uindex ON public.client_config USING btree (username);


--
-- TOC entry 2074 (class 1259 OID 16475)
-- Name: client_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX client_id_uindex ON public.client USING btree (id);


--
-- TOC entry 2080 (class 1259 OID 24586)
-- Name: doctor_config_clone_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX doctor_config_clone_id_uindex ON public.doctor_config USING btree (id);


--
-- TOC entry 2077 (class 1259 OID 16485)
-- Name: doctor_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX doctor_id_uindex ON public.doctor USING btree (id);


--
-- TOC entry 2083 (class 1259 OID 24603)
-- Name: time_slot_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX time_slot_id_uindex ON public.time_slot USING btree (id);


--
-- TOC entry 2092 (class 2606 OID 24610)
-- Name: client_config client_config_client_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_config
    ADD CONSTRAINT client_config_client_id_fk FOREIGN KEY (id) REFERENCES public.client(id) ON DELETE CASCADE;


--
-- TOC entry 2089 (class 2606 OID 24581)
-- Name: doctor_config doctor_config_clone_doctor_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctor_config
    ADD CONSTRAINT doctor_config_clone_doctor_id_fk FOREIGN KEY (id) REFERENCES public.doctor(id) ON DELETE CASCADE;


--
-- TOC entry 2091 (class 2606 OID 24598)
-- Name: time_slot time_slot_client_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.time_slot
    ADD CONSTRAINT time_slot_client_id_fk FOREIGN KEY (client_id) REFERENCES public.client(id) ON DELETE CASCADE;


--
-- TOC entry 2090 (class 2606 OID 24593)
-- Name: time_slot time_slot_doctor_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.time_slot
    ADD CONSTRAINT time_slot_doctor_id_fk FOREIGN KEY (doctor_id) REFERENCES public.doctor(id) ON DELETE CASCADE;


--
-- TOC entry 2217 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-11-13 21:44:41 EET

--
-- PostgreSQL database dump complete
--

