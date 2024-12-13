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
-- Data for Name: intervenant; Type: TABLE DATA; Schema: public; Owner: USER
--

INSERT INTO public.intervenant VALUES (1, '12345678', 'intervenant@prototype.com', 1, 'Jane', 'Doe', 'password456');
INSERT INTO public.intervenant VALUES (2, '12345678', 'test', 1, 'Mark', 'Taylor', 'test');
INSERT INTO public.intervenant VALUES (3, '12345678', 'mark.taylor@example.com', 1, 'Mark', 'Taylor', 'test1');
INSERT INTO public.intervenant VALUES (4, '87654321', 'emma.wilson@example.com', 2, 'Emma', 'Wilson', 'test2');
INSERT INTO public.intervenant VALUES (5, '13579246', 'luke.johnson@example.com', 3, 'Luke', 'Johnson', 'test3');
INSERT INTO public.intervenant VALUES (6, '24681357', 'sophia.white@example.com', 4, 'Sophia', 'White', 'test4');
INSERT INTO public.intervenant VALUES (7, '31415926', 'james.brown@example.com', 5, 'James', 'Brown', 'test5');
INSERT INTO public.intervenant VALUES (8, '98765432', 'olivia.martin@example.com', 6, 'Olivia', 'Martin', 'test6');


--
-- Data for Name: residential_work_request; Type: TABLE DATA; Schema: public; Owner: USER
--

INSERT INTO public.residential_work_request VALUES (1, 'Réparer la toiture endommagée', true, 'Vieux-Montréal', 'Réparation de la toiture', 'Construction', '2024-11-20', NULL);
INSERT INTO public.residential_work_request VALUES (2, 'Refaire la clôture du jardin', true, 'Plateau-Mont-Royal', 'Réfection de la clôture', 'Aménagement paysager', '2024-11-25', NULL);


--
-- Data for Name: candidature; Type: TABLE DATA; Schema: public; Owner: USER
--



--
-- Data for Name: project; Type: TABLE DATA; Schema: public; Owner: USER
--



--
-- Data for Name: resident; Type: TABLE DATA; Schema: public; Owner: USER
--

INSERT INTO public.resident VALUES (1, '123 Rue de la Paix, Montréal', 25, 'resident@prototype.com', 'John', 'Smiths', 'password123', '514-555-1234', NULL);
INSERT INTO public.resident VALUES (2, '1 Rue de la Paix, Montréal', 25, 'test', 'John', 'Doe', 'test', '514-555-0001', NULL);
INSERT INTO public.resident VALUES (3, '1 Rue de la Paix, Montréal', 25, 'john.doe@example.com', 'John', 'Doe', 'password1', '514-555-0001', NULL);
INSERT INTO public.resident VALUES (4, '2 Rue de la Paix, Montréal', 30, 'jane.smith@example.com', 'Jane', 'Smith', 'password2', '514-555-0002', NULL);
INSERT INTO public.resident VALUES (5, '3 Rue de la Paix, Montréal', 28, 'alice.brown@example.com', 'Alice', 'Brown', 'password3', '514-555-0003', NULL);
INSERT INTO public.resident VALUES (6, '4 Rue de la Paix, Montréal', 35, 'robert.miller@example.com', 'Robert', 'Miller', 'password4', '514-555-0004', NULL);
INSERT INTO public.resident VALUES (7, '5 Rue de la Paix, Montréal', 27, 'emily.davis@example.com', 'Emily', 'Davis', 'password5', '514-555-0005', NULL);
INSERT INTO public.resident VALUES (8, '6 Rue de la Paix, Montréal', 32, 'david.wilson@example.com', 'David', 'Wilson', 'password6', '514-555-0006', NULL);


--
-- Name: candidature_seq; Type: SEQUENCE SET; Schema: public; Owner: USER
--

SELECT pg_catalog.setval('public.candidature_seq', 1, false);


--
-- Name: intervenant_seq; Type: SEQUENCE SET; Schema: public; Owner: USER
--

SELECT pg_catalog.setval('public.intervenant_seq', 1, true);


--
-- Name: project_seq; Type: SEQUENCE SET; Schema: public; Owner: USER
--

SELECT pg_catalog.setval('public.project_seq', 1, false);


--
-- Name: resident_seq; Type: SEQUENCE SET; Schema: public; Owner: USER
--

SELECT pg_catalog.setval('public.resident_seq', 1, true);


--
-- Name: residential_work_request_seq; Type: SEQUENCE SET; Schema: public; Owner: USER
--

SELECT pg_catalog.setval('public.residential_work_request_seq', 1, true);


--
-- PostgreSQL database dump complete
--

