package com.github.devopMarkz.api_reservou.horario.infrastructure;

import com.github.devopMarkz.api_reservou.horario.domain.model.Horario;
import com.github.devopMarkz.api_reservou.horario.domain.repository.HorarioRepository;
import com.github.devopMarkz.api_reservou.horario.interfaces.dto.HorarioRequestDTO;
import com.github.devopMarkz.api_reservou.horario.interfaces.dto.HorarioResponseDTO;
import com.github.devopMarkz.api_reservou.quadra.domain.repository.QuadraRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class HorarioMapper {

    @Autowired
    private QuadraRepository quadraRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quadra", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "reservado", ignore = true)
    @Mapping(target = "duracao", ignore = true)
    public abstract Horario toHorario(HorarioRequestDTO requestDTO);

    @Mapping(target = "idQuadra", expression = "java( getIdFromQuadra(horario) )")
    @Mapping(target = "reservado", expression = "java( getReservado(horario) )")
    @Mapping(target = "duracaoEmMinutos", expression = "java( getDuracaoEmMinutos(horario) )")
    public abstract HorarioResponseDTO toHorarioResponseDTO(Horario horario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quadra", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "reservado", ignore = true)
    @Mapping(target = "duracao", ignore = true)
    public abstract Horario toHorarioUpdated(HorarioRequestDTO requestDTO,
                                             @MappingTarget Horario horario);

    protected Long getIdFromQuadra(Horario horario){
        return horario.getQuadra().getId();
    }

    protected Boolean getReservado(Horario horario){
        return horarioRepository.isHorarioReservadoNoDia(horario, horario.getDataHoraInicio(), horario.getDataHoraFim());
    }

    protected Long getDuracaoEmMinutos(Horario horario){
        return horario.getDuracao().toMinutes();
    }

}
