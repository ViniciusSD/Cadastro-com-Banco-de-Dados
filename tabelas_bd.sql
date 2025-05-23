PGDMP  (    0                 |            postgres    16.3    16.3     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    5    postgres    DATABASE        CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE postgres;
                postgres    false            �           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    4793                        3079    16384 	   adminpack 	   EXTENSION     A   CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;
    DROP EXTENSION adminpack;
                   false            �           0    0    EXTENSION adminpack    COMMENT     M   COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';
                        false    2            �            1259    16400 	   circuitos    TABLE     �   CREATE TABLE public.circuitos (
    id integer,
    pista character varying(100),
    pais character varying(50),
    cidade character varying(100),
    extensao integer,
    voltas integer,
    record character varying(30)
);
    DROP TABLE public.circuitos;
       public         heap    postgres    false            �            1259    16403    equipes    TABLE     �   CREATE TABLE public.equipes (
    id integer,
    nome character varying(100),
    chefe character varying(100),
    sede character varying(100),
    ano integer,
    carro character varying(50),
    vitorias integer
);
    DROP TABLE public.equipes;
       public         heap    postgres    false            �            1259    16407    pilotos    TABLE     �   CREATE TABLE public.pilotos (
    id integer,
    nome character varying(100),
    nacionalidade character varying(100),
    equipe character varying(100),
    numero integer,
    pontos integer
);
    DROP TABLE public.pilotos;
       public         heap    postgres    false            �          0    16400 	   circuitos 
   TABLE DATA           V   COPY public.circuitos (id, pista, pais, cidade, extensao, voltas, record) FROM stdin;
    public          postgres    false    216   �       �          0    16403    equipes 
   TABLE DATA           N   COPY public.equipes (id, nome, chefe, sede, ano, carro, vitorias) FROM stdin;
    public          postgres    false    217   �       �          0    16407    pilotos 
   TABLE DATA           R   COPY public.pilotos (id, nome, nacionalidade, equipe, numero, pontos) FROM stdin;
    public          postgres    false    218   �       �      x������ � �      �      x������ � �      �      x������ � �     