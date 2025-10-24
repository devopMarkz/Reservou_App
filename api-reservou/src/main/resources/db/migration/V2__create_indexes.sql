-- Índices para tb_usuarios
CREATE INDEX idx_usuarios_email ON tb_usuarios(email);

-- Índices para tb_estabelecimentos
CREATE INDEX idx_estabelecimentos_usuario_dono ON tb_estabelecimentos(usuario_dono_id);
CREATE INDEX idx_estabelecimentos_cidade ON tb_estabelecimentos(cidade);
CREATE INDEX idx_estabelecimentos_estado ON tb_estabelecimentos(estado);
CREATE INDEX idx_estabelecimentos_cep ON tb_estabelecimentos(cep);
CREATE INDEX idx_estabelecimentos_nome ON tb_estabelecimentos(nome);

-- Índices para tb_quadras
CREATE INDEX idx_quadras_estabelecimento ON tb_quadras(estabelecimento_id);

-- Índices para tb_horarios
CREATE INDEX idx_horarios_quadra_ativo ON tb_horarios (quadra_id, ativo);
CREATE INDEX idx_horarios_data_inicio ON tb_horarios(data_hora_inicio);
CREATE INDEX idx_horarios_data_fim ON tb_horarios(data_hora_fim);

-- Índices para tb_pedidos (NOVO)
CREATE INDEX idx_pedidos_usuario ON tb_pedidos(usuario_id);
CREATE INDEX idx_pedidos_status ON tb_pedidos(status);
CREATE INDEX idx_pedidos_data_pedido ON tb_pedidos(data_pedido);

-- Índices para tb_reservas (AJUSTADO)
CREATE INDEX idx_reservas_pedido ON tb_reservas(pedido_id); -- Novo índice para a nova relação
CREATE INDEX idx_reservas_horario_data ON tb_reservas (horario_id, data_reserva);
CREATE INDEX idx_reservas_status ON tb_reservas(status);


-- Índices para tb_pagamentos (AJUSTADO)
CREATE INDEX idx_pagamentos_pedido ON tb_pagamentos(pedido_id); -- Novo índice para a nova relação
CREATE INDEX idx_pagamentos_tipo ON tb_pagamentos(tipo);
CREATE INDEX idx_pagamentos_status ON tb_pagamentos(status);
CREATE INDEX idx_pagamentos_data_criacao ON tb_pagamentos(data_criacao);
CREATE INDEX idx_pagamentos_data_pagamento ON tb_pagamentos(data_pagamento);

-- Índices para tb_avaliacoes
CREATE INDEX idx_avaliacoes_usuario ON tb_avaliacoes(usuario_id);
CREATE INDEX idx_avaliacoes_estabelecimento ON tb_avaliacoes(estabelecimento_id);
CREATE INDEX idx_avaliacoes_nota ON tb_avaliacoes(nota);
CREATE INDEX idx_avaliacoes_data_criacao ON tb_avaliacoes(data_criacao);

-- Índices para tb_horarios_dias
CREATE INDEX idx_horarios_dias_horario_dia ON tb_horarios_dias (horario_id, dia_semana);

-- Índices auxiliares (opcional mas útil para buscas)
CREATE INDEX idx_ru_usuario ON tb_reserva_usuarios (usuario_id);
CREATE INDEX idx_ru_reserva ON tb_reserva_usuarios (reserva_id);