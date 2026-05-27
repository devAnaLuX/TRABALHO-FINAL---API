-- ================================================
-- SerratecFlix — Script Inicial do Banco de Dados
-- ================================================

-- ----------------
-- CATEGORIAS (10)
-- ----------------
INSERT INTO categorias (id, nome, descricao) VALUES
                                                 ('a1000000-0000-0000-0000-000000000001', 'Ação',              'Filmes e séries cheios de adrenalina, lutas e perseguições'),
                                                 ('a1000000-0000-0000-0000-000000000002', 'Ficção Científica', 'Histórias baseadas em ciência e tecnologia futurista'),
                                                 ('a1000000-0000-0000-0000-000000000003', 'Drama',             'Narrativas emocionais com foco em conflitos humanos'),
                                                 ('a1000000-0000-0000-0000-000000000004', 'Terror',            'Conteúdo de suspense e medo'),
                                                 ('a1000000-0000-0000-0000-000000000005', 'Comédia',           'Conteúdo leve e humorístico'),
                                                 ('a1000000-0000-0000-0000-000000000006', 'Animação',          'Produções animadas para todas as idades'),
                                                 ('a1000000-0000-0000-0000-000000000007', 'Crime',             'Histórias policiais, detetives e investigações'),
                                                 ('a1000000-0000-0000-0000-000000000008', 'Aventura',          'Jornadas épicas e exploração de mundos'),
                                                 ('a1000000-0000-0000-0000-000000000009', 'Romance',           'Histórias de amor e relacionamentos'),
                                                 ('a1000000-0000-0000-0000-000000000010', 'Documentário',      'Conteúdo baseado em fatos reais e investigações')
    ON CONFLICT (id) DO NOTHING;

-- ----------------
-- USUÁRIOS (10)
-- Senha: senha123 (hash BCrypt $2a$10 válido para Java)
-- ----------------
INSERT INTO usuarios (id, nome, email, username, senha, foto_perfil, data_criacao) VALUES
                                                                                       ('b1000000-0000-0000-0000-000000000001', 'Ana Lima',       'ana.lima@email.com',      'analima',      '$2a$10$JdjwiX0JvUeFWpH4QyqxbuvuelBQMCHwk7dkS5ytGMzUGWGHengcS', NULL, NOW()),
                                                                                       ('b1000000-0000-0000-0000-000000000002', 'Carlos Souza',   'carlos.souza@email.com',  'carlossouza',  '$2a$10$JdjwiX0JvUeFWpH4QyqxbuvuelBQMCHwk7dkS5ytGMzUGWGHengcS', NULL, NOW()),
                                                                                       ('b1000000-0000-0000-0000-000000000003', 'Beatriz Nunes',  'bia.nunes@email.com',     'bianunes',     '$2a$10$JdjwiX0JvUeFWpH4QyqxbuvuelBQMCHwk7dkS5ytGMzUGWGHengcS', NULL, NOW()),
                                                                                       ('b1000000-0000-0000-0000-000000000004', 'Diego Martins',  'diego.martins@email.com', 'diegomartins', '$2a$10$JdjwiX0JvUeFWpH4QyqxbuvuelBQMCHwk7dkS5ytGMzUGWGHengcS', NULL, NOW()),
                                                                                       ('b1000000-0000-0000-0000-000000000005', 'Fernanda Costa', 'fer.costa@email.com',     'fercosta',     '$2a$10$JdjwiX0JvUeFWpH4QyqxbuvuelBQMCHwk7dkS5ytGMzUGWGHengcS', NULL, NOW()),
                                                                                       ('b1000000-0000-0000-0000-000000000006', 'Gabriel Rocha',  'gabriel.rocha@email.com', 'gabrielrocha', '$2a$10$JdjwiX0JvUeFWpH4QyqxbuvuelBQMCHwk7dkS5ytGMzUGWGHengcS', NULL, NOW()),
                                                                                       ('b1000000-0000-0000-0000-000000000007', 'Helena Vieira',  'helena.v@email.com',      'helenavieira', '$2a$10$JdjwiX0JvUeFWpH4QyqxbuvuelBQMCHwk7dkS5ytGMzUGWGHengcS', NULL, NOW()),
                                                                                       ('b1000000-0000-0000-0000-000000000008', 'Igor Pereira',   'igor.p@email.com',        'igorp',        '$2a$10$JdjwiX0JvUeFWpH4QyqxbuvuelBQMCHwk7dkS5ytGMzUGWGHengcS', NULL, NOW()),
                                                                                       ('b1000000-0000-0000-0000-000000000009', 'Juliana Alves',  'juliana.alves@email.com', 'julialves',    '$2a$10$JdjwiX0JvUeFWpH4QyqxbuvuelBQMCHwk7dkS5ytGMzUGWGHengcS', NULL, NOW()),
                                                                                       ('b1000000-0000-0000-0000-000000000010', 'Lucas Ferreira', 'lucas.fer@email.com',     'lucasfer',     '$2a$10$JdjwiX0JvUeFWpH4QyqxbuvuelBQMCHwk7dkS5ytGMzUGWGHengcS', NULL, NOW())
    ON CONFLICT (id) DO NOTHING;

-- ----------------
-- FILMES (10)
-- ----------------
INSERT INTO filmes (id, titulo, descricao, duracao, data_lancamento, classificacao_indicativa, nota_media) VALUES
                                                                                                               ('c1000000-0000-0000-0000-000000000001', 'Interestelar',         'Um grupo de astronautas viaja por um buraco de minhoca em busca de um novo lar para a humanidade.',          169, '2014-11-07', 'DOZE',      9.5),
                                                                                                               ('c1000000-0000-0000-0000-000000000002', 'Parasita',             'Uma família pobre se infiltra na vida de uma família rica em Seul, com consequências inesperadas.',          132, '2019-05-30', 'DEZESSEIS', 9.0),
                                                                                                               ('c1000000-0000-0000-0000-000000000003', 'O Poderoso Chefão',    'A saga da família Corleone, uma das mais poderosas famílias da máfia americana.',                            175, '1972-03-24', 'DEZOITO',   9.2),
                                                                                                               ('c1000000-0000-0000-0000-000000000004', 'Matrix',               'Um programador descobre que a realidade em que vive é uma simulação criada por máquinas.',                   136, '1999-03-31', 'DEZESSEIS', 8.7),
                                                                                                               ('c1000000-0000-0000-0000-000000000005', 'Coringa',              'A origem do famoso vilão da DC Comics, explorando sua queda à loucura.',                                    122, '2019-10-04', 'DEZOITO',   8.5),
                                                                                                               ('c1000000-0000-0000-0000-000000000006', 'Toy Story',            'Os brinquedos de Andy ganham vida quando ele não está por perto, e o cowboy Woody precisa lidar com um novo rival.', 81, '1995-11-22', 'LIVRE', 8.3),
                                                                                                               ('c1000000-0000-0000-0000-000000000007', 'Clube da Luta',        'Um homem insone forma um clube de luta clandestino com o misterioso Tyler Durden.',                         139, '1999-10-15', 'DEZOITO',   8.8),
                                                                                                               ('c1000000-0000-0000-0000-000000000008', 'Coco',                 'O jovem Miguel viaja ao mundo dos mortos em busca de seu tataravô para descobrir a história de sua família.', 105, '2017-10-27', 'LIVRE',    8.4),
                                                                                                               ('c1000000-0000-0000-0000-000000000009', 'Oppenheimer',          'A história do físico J. Robert Oppenheimer e seu papel no desenvolvimento da primeira bomba atômica.',       180, '2023-07-21', 'DEZESSEIS', 8.9),
                                                                                                               ('c1000000-0000-0000-0000-000000000010', 'Vingadores: Ultimato', 'Os Vingadores se reúnem uma última vez para reverter as ações de Thanos e restaurar o universo.',            181, '2019-04-26', 'DOZE',      8.4)
    ON CONFLICT (id) DO NOTHING;

-- Categorias dos filmes
INSERT INTO filme_categoria (filme_id, categoria_id) VALUES
                                                         ('c1000000-0000-0000-0000-000000000001', 'a1000000-0000-0000-0000-000000000002'),
                                                         ('c1000000-0000-0000-0000-000000000001', 'a1000000-0000-0000-0000-000000000003'),
                                                         ('c1000000-0000-0000-0000-000000000002', 'a1000000-0000-0000-0000-000000000003'),
                                                         ('c1000000-0000-0000-0000-000000000002', 'a1000000-0000-0000-0000-000000000007'),
                                                         ('c1000000-0000-0000-0000-000000000003', 'a1000000-0000-0000-0000-000000000007'),
                                                         ('c1000000-0000-0000-0000-000000000003', 'a1000000-0000-0000-0000-000000000003'),
                                                         ('c1000000-0000-0000-0000-000000000004', 'a1000000-0000-0000-0000-000000000002'),
                                                         ('c1000000-0000-0000-0000-000000000004', 'a1000000-0000-0000-0000-000000000001'),
                                                         ('c1000000-0000-0000-0000-000000000005', 'a1000000-0000-0000-0000-000000000003'),
                                                         ('c1000000-0000-0000-0000-000000000005', 'a1000000-0000-0000-0000-000000000007'),
                                                         ('c1000000-0000-0000-0000-000000000006', 'a1000000-0000-0000-0000-000000000006'),
                                                         ('c1000000-0000-0000-0000-000000000006', 'a1000000-0000-0000-0000-000000000008'),
                                                         ('c1000000-0000-0000-0000-000000000007', 'a1000000-0000-0000-0000-000000000003'),
                                                         ('c1000000-0000-0000-0000-000000000008', 'a1000000-0000-0000-0000-000000000006'),
                                                         ('c1000000-0000-0000-0000-000000000008', 'a1000000-0000-0000-0000-000000000008'),
                                                         ('c1000000-0000-0000-0000-000000000009', 'a1000000-0000-0000-0000-000000000003'),
                                                         ('c1000000-0000-0000-0000-000000000009', 'a1000000-0000-0000-0000-000000000010'),
                                                         ('c1000000-0000-0000-0000-000000000010', 'a1000000-0000-0000-0000-000000000001'),
                                                         ('c1000000-0000-0000-0000-000000000010', 'a1000000-0000-0000-0000-000000000008')
    ON CONFLICT DO NOTHING;

-- ----------------
-- SÉRIES (10)
-- ----------------
INSERT INTO series (id, titulo, descricao, temporadas, episodios, data_lancamento, nota_media) VALUES
                                                                                                   ('d1000000-0000-0000-0000-000000000001', 'Breaking Bad',     'Um professor de química diagnosticado com câncer começa a fabricar metanfetamina para garantir o futuro da família.',           5,  62, '2008-01-20', 9.5),
                                                                                                   ('d1000000-0000-0000-0000-000000000002', 'Game of Thrones',  'Famílias nobres disputam o Trono de Ferro nos Sete Reinos de Westeros.',                                                        8,  73, '2011-04-17', 9.2),
                                                                                                   ('d1000000-0000-0000-0000-000000000003', 'Stranger Things',  'Um grupo de crianças enfrenta forças sobrenaturais e experimentos secretos em uma pequena cidade dos anos 80.',                4,  34, '2016-07-15', 8.7),
                                                                                                   ('d1000000-0000-0000-0000-000000000004', 'The Office',       'Mockumentary que acompanha o cotidiano dos funcionários de uma filial de uma empresa de papel em Scranton, Pensilvânia.',       9, 201, '2005-03-24', 8.9),
                                                                                                   ('d1000000-0000-0000-0000-000000000005', 'Dark',             'Uma família alemã descobre uma passagem temporal que conecta gerações passadas e futuras de uma pequena cidade.',               3,  26, '2017-12-01', 8.8),
                                                                                                   ('d1000000-0000-0000-0000-000000000006', 'Chernobyl',        'Minissérie que retrata os eventos do desastre nuclear de Chernobyl em 1986 e a tentativa de encobrimento pelo governo soviético.', 1, 5, '2019-05-06', 9.4),
                                                                                                   ('d1000000-0000-0000-0000-000000000007', 'La Casa de Papel', 'Um misterioso criminoso recruta oito ladrões para realizar dois roubos sem precedentes nas casas da moeda da Espanha e da Europa.', 5, 41, '2017-05-02', 8.3),
                                                                                                   ('d1000000-0000-0000-0000-000000000008', 'Attack on Titan',  'A humanidade vive dentro de enormes muros para se proteger dos titãs, criaturas gigantescas que devoram humanos.',             4,  87, '2013-04-07', 9.1),
                                                                                                   ('d1000000-0000-0000-0000-000000000009', 'Black Mirror',     'Antologia que explora o lado sombrio da tecnologia e seus impactos na sociedade moderna.',                                      6,  27, '2011-12-04', 8.5),
                                                                                                   ('d1000000-0000-0000-0000-000000000010', 'Peaky Blinders',   'A saga da família Shelby, um clã criminoso de Birmingham, Inglaterra, no início do século XX.',                                6,  36, '2013-09-12', 8.8)
    ON CONFLICT (id) DO NOTHING;

-- Categorias das séries
INSERT INTO serie_categoria (serie_id, categoria_id) VALUES
                                                         ('d1000000-0000-0000-0000-000000000001', 'a1000000-0000-0000-0000-000000000007'),
                                                         ('d1000000-0000-0000-0000-000000000001', 'a1000000-0000-0000-0000-000000000003'),
                                                         ('d1000000-0000-0000-0000-000000000002', 'a1000000-0000-0000-0000-000000000001'),
                                                         ('d1000000-0000-0000-0000-000000000002', 'a1000000-0000-0000-0000-000000000008'),
                                                         ('d1000000-0000-0000-0000-000000000003', 'a1000000-0000-0000-0000-000000000004'),
                                                         ('d1000000-0000-0000-0000-000000000003', 'a1000000-0000-0000-0000-000000000002'),
                                                         ('d1000000-0000-0000-0000-000000000004', 'a1000000-0000-0000-0000-000000000005'),
                                                         ('d1000000-0000-0000-0000-000000000005', 'a1000000-0000-0000-0000-000000000002'),
                                                         ('d1000000-0000-0000-0000-000000000005', 'a1000000-0000-0000-0000-000000000003'),
                                                         ('d1000000-0000-0000-0000-000000000006', 'a1000000-0000-0000-0000-000000000003'),
                                                         ('d1000000-0000-0000-0000-000000000006', 'a1000000-0000-0000-0000-000000000010'),
                                                         ('d1000000-0000-0000-0000-000000000007', 'a1000000-0000-0000-0000-000000000007'),
                                                         ('d1000000-0000-0000-0000-000000000007', 'a1000000-0000-0000-0000-000000000001'),
                                                         ('d1000000-0000-0000-0000-000000000008', 'a1000000-0000-0000-0000-000000000001'),
                                                         ('d1000000-0000-0000-0000-000000000008', 'a1000000-0000-0000-0000-000000000006'),
                                                         ('d1000000-0000-0000-0000-000000000009', 'a1000000-0000-0000-0000-000000000002'),
                                                         ('d1000000-0000-0000-0000-000000000009', 'a1000000-0000-0000-0000-000000000004'),
                                                         ('d1000000-0000-0000-0000-000000000010', 'a1000000-0000-0000-0000-000000000007'),
                                                         ('d1000000-0000-0000-0000-000000000010', 'a1000000-0000-0000-0000-000000000003')
    ON CONFLICT DO NOTHING;

-- ----------------------
-- AVALIAÇÕES DE FILMES (10)
-- ----------------------
INSERT INTO avaliacoes_filme (id, nota, comentario, data_avaliacao, usuario_id, filme_id) VALUES
                                                                                              ('e1000000-0000-0000-0000-000000000001', 9.5, 'Uma obra-prima. A trilha sonora e os efeitos visuais são incríveis.',  NOW(), 'b1000000-0000-0000-0000-000000000001', 'c1000000-0000-0000-0000-000000000001'),
                                                                                              ('e1000000-0000-0000-0000-000000000002', 9.0, 'Parasita merece todos os Oscars que ganhou. Roteiro impecável.',       NOW(), 'b1000000-0000-0000-0000-000000000002', 'c1000000-0000-0000-0000-000000000002'),
                                                                                              ('e1000000-0000-0000-0000-000000000003', 9.8, 'O melhor filme de todos os tempos. Marlon Brando é magistral.',        NOW(), 'b1000000-0000-0000-0000-000000000003', 'c1000000-0000-0000-0000-000000000003'),
                                                                                              ('e1000000-0000-0000-0000-000000000004', 8.7, 'Matrix mudou a forma de ver filmes de ficção científica.',            NOW(), 'b1000000-0000-0000-0000-000000000004', 'c1000000-0000-0000-0000-000000000004'),
                                                                                              ('e1000000-0000-0000-0000-000000000005', 8.5, 'Joaquin Phoenix entrega uma atuação perturbadora e brilhante.',       NOW(), 'b1000000-0000-0000-0000-000000000005', 'c1000000-0000-0000-0000-000000000005'),
                                                                                              ('e1000000-0000-0000-0000-000000000006', 9.2, 'Toy Story é um clássico que emociona até hoje.',                      NOW(), 'b1000000-0000-0000-0000-000000000006', 'c1000000-0000-0000-0000-000000000006'),
                                                                                              ('e1000000-0000-0000-0000-000000000007', 9.0, 'Final chocante. Um dos melhores filmes dos anos 90.',                 NOW(), 'b1000000-0000-0000-0000-000000000007', 'c1000000-0000-0000-0000-000000000007'),
                                                                                              ('e1000000-0000-0000-0000-000000000008', 8.8, 'Coco me fez chorar muito. A mensagem sobre família é linda.',         NOW(), 'b1000000-0000-0000-0000-000000000008', 'c1000000-0000-0000-0000-000000000008'),
                                                                                              ('e1000000-0000-0000-0000-000000000009', 9.0, 'Oppenheimer é denso e brilhante. Nolan superou as expectativas.',     NOW(), 'b1000000-0000-0000-0000-000000000009', 'c1000000-0000-0000-0000-000000000009'),
                                                                                              ('e1000000-0000-0000-0000-000000000010', 8.0, 'Ultimato é emocionante. O final do Tony Stark me destruiu.',          NOW(), 'b1000000-0000-0000-0000-000000000010', 'c1000000-0000-0000-0000-000000000010')
    ON CONFLICT (id) DO NOTHING;

-- ----------------------
-- AVALIAÇÕES DE SÉRIES (10)
-- ----------------------
INSERT INTO avaliacoes_serie (id, nota, comentario, data_avaliacao, usuario_id, serie_id) VALUES
                                                                                              ('f1000000-0000-0000-0000-000000000001', 10.0, 'Breaking Bad é perfeição absoluta. Cada episódio é melhor que o anterior.',   NOW(), 'b1000000-0000-0000-0000-000000000001', 'd1000000-0000-0000-0000-000000000001'),
                                                                                              ('f1000000-0000-0000-0000-000000000002',  9.0, 'Game of Thrones tinha um potencial enorme. As primeiras temporadas são 10.',  NOW(), 'b1000000-0000-0000-0000-000000000002', 'd1000000-0000-0000-0000-000000000002'),
                                                                                              ('f1000000-0000-0000-0000-000000000003',  8.7, 'Stranger Things é nostalgia pura. A Eleven é incrível.',                     NOW(), 'b1000000-0000-0000-0000-000000000003', 'd1000000-0000-0000-0000-000000000003'),
                                                                                              ('f1000000-0000-0000-0000-000000000004',  9.5, 'The Office nunca envelhece. Michael Scott é o melhor personagem da TV.',     NOW(), 'b1000000-0000-0000-0000-000000000004', 'd1000000-0000-0000-0000-000000000004'),
                                                                                              ('f1000000-0000-0000-0000-000000000005',  9.2, 'Dark é a melhor série alemã que já assisti. Complexo e fascinante.',         NOW(), 'b1000000-0000-0000-0000-000000000005', 'd1000000-0000-0000-0000-000000000005'),
                                                                                              ('f1000000-0000-0000-0000-000000000006',  9.8, 'Chernobyl é devastador e imprescindível. Melhor minissérie de todos os tempos.', NOW(), 'b1000000-0000-0000-0000-000000000006', 'd1000000-0000-0000-0000-000000000006'),
                                                                                              ('f1000000-0000-0000-0000-000000000007',  8.0, 'La Casa de Papel começou incrível mas foi perdendo força nas últimas temporadas.', NOW(), 'b1000000-0000-0000-0000-000000000007', 'd1000000-0000-0000-0000-000000000007'),
                                                                                              ('f1000000-0000-0000-0000-000000000008',  9.5, 'Attack on Titan tem o melhor final de anime já feito.',                     NOW(), 'b1000000-0000-0000-0000-000000000008', 'd1000000-0000-0000-0000-000000000008'),
                                                                                              ('f1000000-0000-0000-0000-000000000009',  8.5, 'Black Mirror me faz repensar minha relação com a tecnologia.',              NOW(), 'b1000000-0000-0000-0000-000000000009', 'd1000000-0000-0000-0000-000000000009'),
                                                                                              ('f1000000-0000-0000-0000-000000000010',  9.0, 'Peaky Blinders tem uma estética e trilha sonora únicas. Tommy Shelby é icônico.', NOW(), 'b1000000-0000-0000-0000-000000000010', 'd1000000-0000-0000-0000-000000000010')
    ON CONFLICT (id) DO NOTHING;

-- ----------------------
-- LISTAS DE FAVORITOS (10)
-- ----------------------
INSERT INTO listas_favoritos (id, nome_lista, privada, data_criacao, usuario_id) VALUES
                                                                                     ('71000000-0000-0000-0000-000000000001', 'Meus Favoritos',        false, NOW(), 'b1000000-0000-0000-0000-000000000001'),
                                                                                     ('71000000-0000-0000-0000-000000000002', 'Assistir Depois',       false, NOW(), 'b1000000-0000-0000-0000-000000000002'),
                                                                                     ('71000000-0000-0000-0000-000000000003', 'Top Ficção Científica', true,  NOW(), 'b1000000-0000-0000-0000-000000000003'),
                                                                                     ('71000000-0000-0000-0000-000000000004', 'Clássicos do Cinema',   false, NOW(), 'b1000000-0000-0000-0000-000000000004'),
                                                                                     ('71000000-0000-0000-0000-000000000005', 'Séries para Maratonar', true,  NOW(), 'b1000000-0000-0000-0000-000000000005'),
                                                                                     ('71000000-0000-0000-0000-000000000006', 'Animações Favoritas',   false, NOW(), 'b1000000-0000-0000-0000-000000000006'),
                                                                                     ('71000000-0000-0000-0000-000000000007', 'Crimes e Suspense',     false, NOW(), 'b1000000-0000-0000-0000-000000000007'),
                                                                                     ('71000000-0000-0000-0000-000000000008', 'Para Ver com a Família',false, NOW(), 'b1000000-0000-0000-0000-000000000008'),
                                                                                     ('71000000-0000-0000-0000-000000000009', 'Indicados ao Oscar',    true,  NOW(), 'b1000000-0000-0000-0000-000000000009'),
                                                                                     ('71000000-0000-0000-0000-000000000010', 'Lista Secreta',         true,  NOW(), 'b1000000-0000-0000-0000-000000000010')
    ON CONFLICT (id) DO NOTHING;

-- Filmes nas listas
INSERT INTO lista_filme (lista_id, filme_id) VALUES
                                                 ('71000000-0000-0000-0000-000000000001', 'c1000000-0000-0000-0000-000000000001'),
                                                 ('71000000-0000-0000-0000-000000000001', 'c1000000-0000-0000-0000-000000000009'),
                                                 ('71000000-0000-0000-0000-000000000002', 'c1000000-0000-0000-0000-000000000004'),
                                                 ('71000000-0000-0000-0000-000000000003', 'c1000000-0000-0000-0000-000000000004'),
                                                 ('71000000-0000-0000-0000-000000000004', 'c1000000-0000-0000-0000-000000000003'),
                                                 ('71000000-0000-0000-0000-000000000004', 'c1000000-0000-0000-0000-000000000007'),
                                                 ('71000000-0000-0000-0000-000000000006', 'c1000000-0000-0000-0000-000000000006'),
                                                 ('71000000-0000-0000-0000-000000000006', 'c1000000-0000-0000-0000-000000000008'),
                                                 ('71000000-0000-0000-0000-000000000007', 'c1000000-0000-0000-0000-000000000002'),
                                                 ('71000000-0000-0000-0000-000000000009', 'c1000000-0000-0000-0000-000000000002')
    ON CONFLICT DO NOTHING;

-- Séries nas listas
INSERT INTO lista_serie (lista_id, serie_id) VALUES
                                                 ('71000000-0000-0000-0000-000000000001', 'd1000000-0000-0000-0000-000000000001'),
                                                 ('71000000-0000-0000-0000-000000000002', 'd1000000-0000-0000-0000-000000000002'),
                                                 ('71000000-0000-0000-0000-000000000003', 'd1000000-0000-0000-0000-000000000009'),
                                                 ('71000000-0000-0000-0000-000000000005', 'd1000000-0000-0000-0000-000000000001'),
                                                 ('71000000-0000-0000-0000-000000000005', 'd1000000-0000-0000-0000-000000000005'),
                                                 ('71000000-0000-0000-0000-000000000007', 'd1000000-0000-0000-0000-000000000007'),
                                                 ('71000000-0000-0000-0000-000000000007', 'd1000000-0000-0000-0000-000000000010'),
                                                 ('71000000-0000-0000-0000-000000000008', 'd1000000-0000-0000-0000-000000000003'),
                                                 ('71000000-0000-0000-0000-000000000009', 'd1000000-0000-0000-0000-000000000006'),
                                                 ('71000000-0000-0000-0000-000000000010', 'd1000000-0000-0000-0000-000000000008')
    ON CONFLICT DO NOTHING;