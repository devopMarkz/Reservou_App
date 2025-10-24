-- TABELA DE USUÁRIOS
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

-- TABELA DE ESTABELECIMENTOS
CREATE TABLE tb_estabelecimentos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    telefone VARCHAR(20),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    url_foto VARCHAR(255),
    url_facebook VARCHAR(255),
    url_instagram VARCHAR(255),
    url_site VARCHAR(255),
    logradouro VARCHAR(100) NOT NULL,
    numero VARCHAR(50) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    cep VARCHAR(20) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    usuario_dono_id BIGINT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_dono FOREIGN KEY (usuario_dono_id) REFERENCES tb_usuarios(id)
);

-- TABELA DE QUADRAS
CREATE TABLE tb_quadras (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255),
    estabelecimento_id BIGINT NOT NULL,
    url_foto VARCHAR(255),
    link_mapa_endereco VARCHAR(255),
    informacoes_gerais TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_estabelecimento FOREIGN KEY (estabelecimento_id) REFERENCES tb_estabelecimentos(id)
);

-- TABELA DE HORÁRIOS
CREATE TABLE tb_horarios (
    id BIGSERIAL PRIMARY KEY,
    quadra_id BIGINT NOT NULL,
    data_hora_inicio TIMESTAMP NOT NULL,
    data_hora_fim TIMESTAMP NOT NULL,
    preco DECIMAL(10,2) NOT NULL DEFAULT 0,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_quadra FOREIGN KEY (quadra_id) REFERENCES tb_quadras(id)
);

-- TABELA DE HORÁRIOS DIAS
CREATE TABLE tb_horarios_dias (
    id BIGSERIAL PRIMARY KEY,
    horario_id BIGINT NOT NULL,
    dia_semana VARCHAR(10) NOT NULL,
    CONSTRAINT fk_horario FOREIGN KEY (horario_id) REFERENCES tb_horarios(id),
    CONSTRAINT idx_horario_dia UNIQUE (horario_id, dia_semana)
);

-- TABELA DE PEDIDOS
CREATE TABLE tb_pedidos (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    data_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    data_expiracao TIMESTAMP NOT NULL,
    CONSTRAINT fk_usuario_pedido FOREIGN KEY (usuario_id) REFERENCES tb_usuarios(id)
);

-- TABELA DE RESERVAS
CREATE TABLE tb_reservas (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    horario_id BIGINT NOT NULL,
    data_reserva TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    privacidade VARCHAR(50) NOT NULL,
    limite_participantes_externos INT,
    CONSTRAINT fk_pedido FOREIGN KEY (pedido_id) REFERENCES tb_pedidos(id),
    CONSTRAINT fk_horario FOREIGN KEY (horario_id) REFERENCES tb_horarios(id)
);

-- TABELA DE PAGAMENTOS (1:1 com pedido)
CREATE TABLE tb_pagamentos (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL UNIQUE,
    tipo VARCHAR(50) NOT NULL,
    status VARCHAR(50),
    valor_pago DECIMAL(10,2) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_pagamento TIMESTAMP,
    CONSTRAINT fk_pedido_pagamento FOREIGN KEY (pedido_id) REFERENCES tb_pedidos(id)
);

-- TABELA DE AVALIAÇÕES
CREATE TABLE tb_avaliacoes (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    estabelecimento_id BIGINT NOT NULL,
    nota DOUBLE PRECISION NOT NULL,
    comentario TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_avaliacao FOREIGN KEY (usuario_id) REFERENCES tb_usuarios(id),
    CONSTRAINT fk_estabelecimento_avaliacao FOREIGN KEY (estabelecimento_id) REFERENCES tb_estabelecimentos(id),
    CONSTRAINT uc_usuario_estabelecimento UNIQUE (usuario_id, estabelecimento_id)
);

-- Participantes da reserva (apenas usuários do sistema)
CREATE TABLE tb_reserva_usuarios (
    reserva_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_reserva_usuarios PRIMARY KEY (reserva_id, usuario_id),
    CONSTRAINT fk_ru_reserva FOREIGN KEY (reserva_id)
     REFERENCES tb_reservas(id) ON DELETE CASCADE,
    CONSTRAINT fk_ru_usuario FOREIGN KEY (usuario_id)
     REFERENCES tb_usuarios(id) ON DELETE CASCADE
);