CREATE TEXT SEARCH DICTIONARY pl_ispell (
    TEMPLATE = ispell,
    DictFile = polish,
    AffFile = polish
--     StopWords = polish
    );;;

CREATE TEXT SEARCH DICTIONARY pl_simple (
    TEMPLATE = pg_catalog.simple
--     StopWords = polish
    );;;
CREATE TEXT SEARCH CONFIGURATION public.polish ( COPY = pg_catalog.english );;;


ALTER TEXT SEARCH CONFIGURATION public.polish
    ALTER MAPPING FOR asciiword, asciihword, hword_asciipart, word, hword, hword_part, numword
        WITH pl_simple;;;

--pełna ze słownikiem
CREATE TEXT SEARCH CONFIGURATION public.polish_ispell ( COPY = pg_catalog.english );;;

ALTER TEXT SEARCH CONFIGURATION public.polish_ispell
    ALTER MAPPING FOR asciiword, asciihword, hword_asciipart, word, hword, hword_part, numword
        WITH pl_ispell, pl_simple;;;

create table synonimy (
    syn_id bigserial primary key,
    syn_tags text[],
    syn_from text,
    syn_to text,
    syn_from_ts tsquery generated always as (websearch_to_tsquery('polish_ispell', syn_from)) stored,
    syn_to_ts tsquery generated always as (websearch_to_tsquery('polish_ispell', syn_to)) stored);
