--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.9
-- Dumped by pg_dump version 9.6.9

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
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: client; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    first_name text,
    last_name text
);


ALTER TABLE public.client OWNER TO admin;

--
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
-- Name: doctor; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.doctor (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    first_name text,
    last_name text
);


ALTER TABLE public.doctor OWNER TO admin;

--
-- Name: doctor_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.doctor_config (
    id uuid NOT NULL,
    start_work timestamp without time zone NOT NULL,
    end_work timestamp without time zone NOT NULL,
    max_app_duration integer,
    max_app_not_fixed boolean NOT NULL,
    speciality text,
    password text NOT NULL,
    username text NOT NULL
);


ALTER TABLE public.doctor_config OWNER TO admin;

--
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
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.client VALUES ('03e08df0-b835-4fd8-aa4d-d144570e349d', 'Jeembo', 'Last');
INSERT INTO public.client VALUES ('42b66dcd-34d3-4b54-b632-a116827f9d52', 'Vadim', 'Ptizyn');
INSERT INTO public.client VALUES ('6390e3c9-2bc4-470a-bd8c-abf8416b1216', 'Sheldon', 'Cooper');
INSERT INTO public.client VALUES ('b135a2b1-4c43-4834-8395-43f1a7520a03', 'Vanya', 'Soldar');
INSERT INTO public.client VALUES ('45affeba-9f6b-4000-947c-9143210cfa5a', 'Hendricks', 'Boolean');
INSERT INTO public.client VALUES ('4eff7aad-cd7e-4759-9089-d12a88746bba', 'Dan', 'Vendetta');
INSERT INTO public.client VALUES ('024e4954-4617-47c6-bc6d-9f5275cca954', 'Franklin', 'Williams');
INSERT INTO public.client VALUES ('13bb163c-9ad1-4010-8180-b6ab413c0c95', 'Vanya', 'Sacra');
INSERT INTO public.client VALUES ('c62c140a-a1a9-4734-bc4e-b820760875d6', 'Dan', 'Orbalt');
INSERT INTO public.client VALUES ('af77a90d-ac83-4c1e-8e36-f34b7cea30e6', 'Vanya', 'Hendricks');
INSERT INTO public.client VALUES ('e9dc8cea-7cdf-48be-9a8d-571b8f96b0d8', 'Dan', 'Binary');
INSERT INTO public.client VALUES ('758ea257-c666-40a1-9190-aa671bc12a3d', 'Viola', 'Richards');


--
-- Data for Name: client_config; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.client_config VALUES ('42b66dcd-34d3-4b54-b632-a116827f9d52', 'lillinkwrk@gmail.com', 'lillink1234', false, 'lillink');
INSERT INTO public.client_config VALUES ('6390e3c9-2bc4-470a-bd8c-abf8416b1216', 'sheldon@email.com', '2106Ds1547', false, 'sheldon');
INSERT INTO public.client_config VALUES ('03e08df0-b835-4fd8-aa4d-d144570e349d', 'sammy@email.com', 'Admin123', false, 'sammy');
INSERT INTO public.client_config VALUES ('b135a2b1-4c43-4834-8395-43f1a7520a03', 'vanya@gmail.com', '2106Ds1547', false, 'vanya123');
INSERT INTO public.client_config VALUES ('024e4954-4617-47c6-bc6d-9f5275cca954', 'williams@email.com', 'Admin123', false, 'williams');
INSERT INTO public.client_config VALUES ('13bb163c-9ad1-4010-8180-b6ab413c0c95', 'sacra@email.com', 'Admin123', false, 'sacra');
INSERT INTO public.client_config VALUES ('c62c140a-a1a9-4734-bc4e-b820760875d6', 'orbalt.dan@gmail.com', '2106Ds1547', false, 'orbalt');
INSERT INTO public.client_config VALUES ('af77a90d-ac83-4c1e-8e36-f34b7cea30e6', 'hendricks@gmial.com', 'Admin123', false, 'hendricks');
INSERT INTO public.client_config VALUES ('e9dc8cea-7cdf-48be-9a8d-571b8f96b0d8', 'dan@gmail.com', '2106Ds1547', false, 'dan123');
INSERT INTO public.client_config VALUES ('758ea257-c666-40a1-9190-aa671bc12a3d', 'viola@gmail.com', '2106Ds1547', false, 'viola123');


--
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.doctor VALUES ('f3ea6eee-5cbc-4d00-8259-c8aa575b861e', 'Richard', 'Lamar');
INSERT INTO public.doctor VALUES ('52ab83cd-85f6-4e18-ba6e-0477689bac23', 'Garold', 'Nashville');
INSERT INTO public.doctor VALUES ('03576c55-5aec-4d11-a9d9-7bfeac3b1722', 'Bob', 'Robins');
INSERT INTO public.doctor VALUES ('25af0eee-ec92-43c4-84a4-fcc2e491fdfb', 'Max', 'Jenkins');


--
-- Data for Name: doctor_config; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.doctor_config VALUES ('f3ea6eee-5cbc-4d00-8259-c8aa575b861e', '2018-11-21 06:30:00.313', '2018-11-21 19:00:00.316', NULL, true, 'Ophtalmologist', 'Doctor123', 'doctor2');
INSERT INTO public.doctor_config VALUES ('52ab83cd-85f6-4e18-ba6e-0477689bac23', '2018-11-21 07:00:00.415', '2018-11-21 16:00:00.621', NULL, true, 'Neurologist', 'Doctor123', 'doctor1');
INSERT INTO public.doctor_config VALUES ('03576c55-5aec-4d11-a9d9-7bfeac3b1722', '2018-11-21 08:04:34.315', '2018-11-21 20:00:38.298', NULL, true, 'Surgeon', 'Doctor123', 'doctor3');
INSERT INTO public.doctor_config VALUES ('25af0eee-ec92-43c4-84a4-fcc2e491fdfb', '2018-11-21 10:00:00.517', '2018-11-21 18:00:00.194', NULL, true, 'Pediatrician', 'Doctor123', 'doctor4');


--
-- Data for Name: time_slot; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.time_slot VALUES ('45970d9a-730b-435e-b60d-5bff7bb993fb', 'c62c140a-a1a9-4734-bc4e-b820760875d6', 'f3ea6eee-5cbc-4d00-8259-c8aa575b861e', '2018-11-23 16:10:00', '2018-11-23 16:30:00');
INSERT INTO public.time_slot VALUES ('65ee0a68-2c6b-4055-a1dc-14faad3ef2e8', 'af77a90d-ac83-4c1e-8e36-f34b7cea30e6', 'f3ea6eee-5cbc-4d00-8259-c8aa575b861e', '2018-11-23 12:05:00', '2018-11-23 12:25:00');
INSERT INTO public.time_slot VALUES ('be3e850a-0bbe-4dc8-b574-001fdbbd3060', 'af77a90d-ac83-4c1e-8e36-f34b7cea30e6', '25af0eee-ec92-43c4-84a4-fcc2e491fdfb', '2018-11-23 13:40:00', '2018-11-23 13:50:00');
INSERT INTO public.time_slot VALUES ('82b1debb-04f8-46dc-a4c5-c261c5c9f149', 'af77a90d-ac83-4c1e-8e36-f34b7cea30e6', '52ab83cd-85f6-4e18-ba6e-0477689bac23', '2018-11-28 12:10:00', '2018-11-28 12:50:00');
INSERT INTO public.time_slot VALUES ('41ce3766-e9db-4efa-a016-6ecbedeeeeb3', 'e9dc8cea-7cdf-48be-9a8d-571b8f96b0d8', 'f3ea6eee-5cbc-4d00-8259-c8aa575b861e', '2018-11-23 16:30:00', '2018-11-23 16:50:00');
INSERT INTO public.time_slot VALUES ('4f760759-3984-4c5f-9553-9322fd9b0994', 'e9dc8cea-7cdf-48be-9a8d-571b8f96b0d8', '52ab83cd-85f6-4e18-ba6e-0477689bac23', '2018-11-23 15:10:00', '2018-11-23 15:25:00');
INSERT INTO public.time_slot VALUES ('01c91382-22e9-4e86-9091-ab7c871d0a41', 'e9dc8cea-7cdf-48be-9a8d-571b8f96b0d8', '03576c55-5aec-4d11-a9d9-7bfeac3b1722', '2018-11-23 15:15:00', '2018-11-23 15:45:00');
INSERT INTO public.time_slot VALUES ('843c826b-4f6f-4328-be9e-5036943e3dfc', '758ea257-c666-40a1-9190-aa671bc12a3d', '25af0eee-ec92-43c4-84a4-fcc2e491fdfb', '2018-11-23 12:05:00', '2018-11-23 12:25:00');
INSERT INTO public.time_slot VALUES ('c1deeb1d-e0e0-4c3e-a9fd-96b2e7c755e3', '758ea257-c666-40a1-9190-aa671bc12a3d', 'f3ea6eee-5cbc-4d00-8259-c8aa575b861e', '2018-11-22 12:10:00', '2018-11-22 12:30:00');


--
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- Name: doctor_config doctor_config_clone_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctor_config
    ADD CONSTRAINT doctor_config_clone_pkey PRIMARY KEY (id);


--
-- Name: doctor doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (id);


--
-- Name: time_slot time_slot_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.time_slot
    ADD CONSTRAINT time_slot_pkey PRIMARY KEY (id);


--
-- Name: client_config_email_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX client_config_email_uindex ON public.client_config USING btree (email);


--
-- Name: client_config_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX client_config_id_uindex ON public.client_config USING btree (id);


--
-- Name: client_config_username_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX client_config_username_uindex ON public.client_config USING btree (username);


--
-- Name: client_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX client_id_uindex ON public.client USING btree (id);


--
-- Name: doctor_config_clone_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX doctor_config_clone_id_uindex ON public.doctor_config USING btree (id);


--
-- Name: doctor_config_username_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX doctor_config_username_uindex ON public.doctor_config USING btree (username);


--
-- Name: doctor_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX doctor_id_uindex ON public.doctor USING btree (id);


--
-- Name: time_slot_id_uindex; Type: INDEX; Schema: public; Owner: admin
--

CREATE UNIQUE INDEX time_slot_id_uindex ON public.time_slot USING btree (id);


--
-- Name: client_config client_config_client_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_config
    ADD CONSTRAINT client_config_client_id_fk FOREIGN KEY (id) REFERENCES public.client(id) ON DELETE CASCADE;


--
-- Name: doctor_config doctor_config_clone_doctor_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctor_config
    ADD CONSTRAINT doctor_config_clone_doctor_id_fk FOREIGN KEY (id) REFERENCES public.doctor(id) ON DELETE CASCADE;


--
-- Name: time_slot time_slot_client_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.time_slot
    ADD CONSTRAINT time_slot_client_id_fk FOREIGN KEY (client_id) REFERENCES public.client(id) ON DELETE CASCADE;


--
-- Name: time_slot time_slot_doctor_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.time_slot
    ADD CONSTRAINT time_slot_doctor_id_fk FOREIGN KEY (doctor_id) REFERENCES public.doctor(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

