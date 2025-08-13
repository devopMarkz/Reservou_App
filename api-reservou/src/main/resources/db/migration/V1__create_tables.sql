-- Tabela "tb_usuarios"
CREATE TABLE tb_usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(50) NOT NULL DEFAULT 'ROLE_USUARIO_COMUM',
    plano VARCHAR(50) NOT NULL DEFAULT 'GRATUITO',
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    refresh_token_jti TEXT
);

-- Tabela "tb_estabelecimentos"
CREATE TABLE tb_estabelecimentos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    usuario_dono_id BIGINT NOT NULL,
    nota_media DOUBLE PRECISION NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_dono FOREIGN KEY (usuario_dono_id) REFERENCES tb_usuarios(id)
);

-- Tabela "tb_quadras"
CREATE TABLE tb_quadras (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255),
    estabelecimento_id BIGINT NOT NULL,
    CONSTRAINT fk_estabelecimento FOREIGN KEY (estabelecimento_id) REFERENCES tb_estabelecimentos(id)
);

-- Tabela "tb_horarios"
CREATE TABLE tb_horarios (
    id BIGSERIAL PRIMARY KEY,
    quadra_id BIGINT NOT NULL,
    data_hora_inicio TIMESTAMP NOT NULL,
    data_hora_fim TIMESTAMP NOT NULL,
    preco DECIMAL(10, 2) NOT NULL DEFAULT 0,
    reservado BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_quadra FOREIGN KEY (quadra_id) REFERENCES tb_quadras(id)
);

-- Tabela "tb_reservas"
CREATE TABLE tb_reservas (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    horario_id BIGINT NOT NULL,
    data_reserva TIMESTAMP NOT NULL,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuarios(id),
    CONSTRAINT fk_horario FOREIGN KEY (horario_id) REFERENCES tb_horarios(id)
);

-- Tabela "tb_pagamentos"
CREATE TABLE tb_pagamentos (
    id BIGSERIAL PRIMARY KEY,
    reserva_id BIGINT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    status VARCHAR(50),
    valor_pago DECIMAL(10, 2) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_pagamento TIMESTAMP,
    CONSTRAINT fk_reserva FOREIGN KEY (reserva_id) REFERENCES tb_reservas(id)
);

-- Tabela "tb_avaliacoes"
CREATE TABLE tb_avaliacoes (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    estabelecimento_id BIGINT NOT NULL,
    nota DOUBLE PRECISION NOT NULL,
    comentario TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_avaliacao FOREIGN KEY (usuario_id) REFERENCES tb_usuarios(id),
    CONSTRAINT fk_estabelecimento_avaliacao FOREIGN KEY (estabelecimento_id) REFERENCES tb_estabelecimentos(id)
);

-- Adicionar constraint para "estabelecimentos" (relacionamento com "usuario_dono")
ALTER TABLE tb_estabelecimentos
    ADD CONSTRAINT fk_estabelecimento_usuario FOREIGN KEY (usuario_dono_id) REFERENCES tb_usuarios(id);