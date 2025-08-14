-- V2__create_indexes.sql

-- Índices para tb_usuarios
CREATE INDEX idx_usuarios_email ON tb_usuarios(email);

-- Índices para tb_estabelecimentos
CREATE INDEX idx_estabelecimentos_usuario_dono ON tb_estabelecimentos(usuario_dono_id);
CREATE INDEX idx_estabelecimentos_cidade ON tb_estabelecimentos(cidade);
CREATE INDEX idx_estabelecimentos_estado ON tb_estabelecimentos(estado);
CREATE INDEX idx_estabelecimentos_cep ON tb_estabelecimentos(cep);

-- Índices para tb_quadras
CREATE INDEX idx_quadras_estabelecimento ON tb_quadras(estabelecimento_id);

-- Índices para tb_horarios
CREATE INDEX idx_horarios_quadra ON tb_horarios(quadra_id);
CREATE INDEX idx_horarios_data_inicio ON tb_horarios(data_hora_inicio);
CREATE INDEX idx_horarios_data_fim ON tb_horarios(data_hora_fim);

-- Índices para tb_reservas
CREATE INDEX idx_reservas_usuario ON tb_reservas(usuario_id);
CREATE INDEX idx_reservas_horario ON tb_reservas(horario_id);
CREATE INDEX idx_reservas_data_reserva ON tb_reservas(data_reserva);

-- Índices para tb_pagamentos
CREATE INDEX idx_pagamentos_reserva ON tb_pagamentos(reserva_id);
CREATE INDEX idx_pagamentos_tipo ON tb_pagamentos(tipo);
CREATE INDEX idx_pagamentos_status ON tb_pagamentos(status);
CREATE INDEX idx_pagamentos_data_criacao ON tb_pagamentos(data_criacao);

-- Índices para tb_avaliacoes
CREATE INDEX idx_avaliacoes_usuario ON tb_avaliacoes(usuario_id);
CREATE INDEX idx_avaliacoes_estabelecimento ON tb_avaliacoes(estabelecimento_id);
CREATE INDEX idx_avaliacoes_nota ON tb_avaliacoes(nota);
CREATE INDEX idx_avaliacoes_data_criacao ON tb_avaliacoes(data_criacao);
