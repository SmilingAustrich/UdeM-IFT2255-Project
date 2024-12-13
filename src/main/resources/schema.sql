--
-- PostgreSQL database dump
--

-- Dumped from database version 16.6 (Ubuntu 16.6-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.6 (Ubuntu 16.6-0ubuntu0.24.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: USER
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO "USER";

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: USER
--

COMMENT ON SCHEMA public IS '';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: candidature; Type: TABLE; Schema: public; Owner: USER
--

CREATE TABLE public.candidature (
    id bigint NOT NULL,
    message character varying(500),
    intervenant_id bigint,
    work_request_id bigint
);


ALTER TABLE public.candidature OWNER TO "USER";

--
-- Name: candidature_seq; Type: SEQUENCE; Schema: public; Owner: USER
--

CREATE SEQUENCE public.candidature_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.candidature_seq OWNER TO "USER";

--
-- Name: intervenant; Type: TABLE; Schema: public; Owner: USER
--

CREATE TABLE public.intervenant (
    id bigint NOT NULL,
    cityidcode character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    entrepreneurtype integer NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL
);


ALTER TABLE public.intervenant OWNER TO "USER";

--
-- Name: intervenant_seq; Type: SEQUENCE; Schema: public; Owner: USER
--

CREATE SEQUENCE public.intervenant_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.intervenant_seq OWNER TO "USER";

--
-- Name: project; Type: TABLE; Schema: public; Owner: USER
--

CREATE TABLE public.project (
    id bigint NOT NULL,
    end_date date,
    project_description character varying(500),
    project_name character varying(255) NOT NULL,
    project_status character varying(255) NOT NULL,
    project_type character varying(255) NOT NULL,
    start_date date,
    project_owner_id bigint NOT NULL
);


ALTER TABLE public.project OWNER TO "USER";

--
-- Name: project_seq; Type: SEQUENCE; Schema: public; Owner: USER
--

CREATE SEQUENCE public.project_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.project_seq OWNER TO "USER";

--
-- Name: resident; Type: TABLE; Schema: public; Owner: USER
--

CREATE TABLE public.resident (
    id bigint NOT NULL,
    address character varying(255) NOT NULL,
    age integer NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    requete_id bigint
);


ALTER TABLE public.resident OWNER TO "USER";

--
-- Name: resident_seq; Type: SEQUENCE; Schema: public; Owner: USER
--

CREATE SEQUENCE public.resident_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.resident_seq OWNER TO "USER";

--
-- Name: residential_work_request; Type: TABLE; Schema: public; Owner: USER
--

CREATE TABLE public.residential_work_request (
    id bigint NOT NULL,
    detailed_work_description character varying(255) NOT NULL,
    is_work_available boolean NOT NULL,
    neighbourhood character varying(255) NOT NULL,
    work_title character varying(255) NOT NULL,
    work_type character varying(255) NOT NULL,
    work_wished_start_date date NOT NULL,
    chosen_intervenant_id bigint
);


ALTER TABLE public.residential_work_request OWNER TO "USER";

--
-- Name: residential_work_request_seq; Type: SEQUENCE; Schema: public; Owner: USER
--

CREATE SEQUENCE public.residential_work_request_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.residential_work_request_seq OWNER TO "USER";

--
-- Name: candidature candidature_pkey; Type: CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.candidature
    ADD CONSTRAINT candidature_pkey PRIMARY KEY (id);


--
-- Name: intervenant intervenant_pkey; Type: CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.intervenant
    ADD CONSTRAINT intervenant_pkey PRIMARY KEY (id);


--
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- Name: resident resident_pkey; Type: CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.resident
    ADD CONSTRAINT resident_pkey PRIMARY KEY (id);


--
-- Name: residential_work_request residential_work_request_pkey; Type: CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.residential_work_request
    ADD CONSTRAINT residential_work_request_pkey PRIMARY KEY (id);


--
-- Name: resident uk_c2n3q6kq3aj9ugnfg7i5jsioy; Type: CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.resident
    ADD CONSTRAINT uk_c2n3q6kq3aj9ugnfg7i5jsioy UNIQUE (requete_id);


--
-- Name: resident uk_ea6j0tmqg8wr6b0a59qw8mxej; Type: CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.resident
    ADD CONSTRAINT uk_ea6j0tmqg8wr6b0a59qw8mxej UNIQUE (email);


--
-- Name: intervenant uk_ochwx2px97r3tjr8d43d5w7k4; Type: CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.intervenant
    ADD CONSTRAINT uk_ochwx2px97r3tjr8d43d5w7k4 UNIQUE (email);


--
-- Name: residential_work_request fk4nltupic0fp7aboqij4xnwbe5; Type: FK CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.residential_work_request
    ADD CONSTRAINT fk4nltupic0fp7aboqij4xnwbe5 FOREIGN KEY (chosen_intervenant_id) REFERENCES public.intervenant(id);


--
-- Name: candidature fkke6ku7id7r63sndnkl40jluf8; Type: FK CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.candidature
    ADD CONSTRAINT fkke6ku7id7r63sndnkl40jluf8 FOREIGN KEY (work_request_id) REFERENCES public.residential_work_request(id);


--
-- Name: project fklvwsg4fi82g2npf7sdolxiqos; Type: FK CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT fklvwsg4fi82g2npf7sdolxiqos FOREIGN KEY (project_owner_id) REFERENCES public.intervenant(id);


--
-- Name: resident fkmolwcwse2jr85q78vk6ogff6h; Type: FK CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.resident
    ADD CONSTRAINT fkmolwcwse2jr85q78vk6ogff6h FOREIGN KEY (requete_id) REFERENCES public.residential_work_request(id);


--
-- Name: candidature fkrnguvlm4tf0ouabqryr8x879c; Type: FK CONSTRAINT; Schema: public; Owner: USER
--

ALTER TABLE ONLY public.candidature
    ADD CONSTRAINT fkrnguvlm4tf0ouabqryr8x879c FOREIGN KEY (intervenant_id) REFERENCES public.intervenant(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: USER
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


--
-- PostgreSQL database dump complete
--

