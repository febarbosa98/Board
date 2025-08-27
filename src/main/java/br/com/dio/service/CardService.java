package br.com.dio.service;

import br.com.dio.dto.BoardColumnInfoDTO;
import br.com.dio.dto.CardDetailsDTO;
import br.com.dio.exception.CardBlockedException;
import br.com.dio.exception.CardFinishedException;
import br.com.dio.exception.EntityNotFoundException;
import br.com.dio.persistence.dao.CardDAO;
import br.com.dio.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static br.com.dio.persistence.entity.BoardColumnKindEnum.FINAL;

@AllArgsConstructor
public class CardService {

    private final Connection connection;

    public CardEntity insert(final CardEntity entity) throws SQLException{
        try {
            var dao = new CardDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        }catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
    }

    public void moveToNextColumn(final Long cardId, final List<BoardColumnInfoDTO> boardsColumnsInfo) throws SQLException {
            try {
                var dao = new CardDAO(connection);
                var optional = dao.findById(cardId);
                var dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId)));
                if (dto.blocked()){
                    throw new CardBlockedException("o card %s está bloqueado, é necessario desbloquea-lo para mover". formatted(cardId));
                }
                var currentColumn = boardsColumnsInfo.stream().filter(bc -> bc.id().equals(dto.columnId())).findFirst().orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
                if (currentColumn.kind().equals(FINAL)){
                    throw new CardFinishedException("O card já foi finalizado");
                }
                var nextColumn = boardsColumnsInfo.stream().filter(bc -> bc.order() == currentColumn.order() + 1).findFirst().orElseThrow(() -> new IllegalStateException("O card está cancelado"));

                dao.moveToColumn(nextColumn.id(), cardId);
                connection.commit();
            }catch (SQLException ex){
                connection.rollback();
                throw ex;
            }

    }

    public void cancel(final Long cardId,final Long cancelColumnId ,final List<BoardColumnInfoDTO> boardsColumnsInfo) throws SQLException{
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId)));
            if (dto.blocked()){
                throw new CardBlockedException("o card %s está bloqueado, é necessario desbloquea-lo para mover". formatted(cardId));
            }
            var currentColumn = boardsColumnsInfo.stream().filter(bc -> bc.id().equals(dto.columnId())).findFirst().orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
            if (currentColumn.kind().equals(FINAL)){
                throw new CardFinishedException("O card já foi finalizado");
            }
            boardsColumnsInfo.stream().filter(bc -> bc.order() == currentColumn.order() + 1).findFirst().orElseThrow(() -> new IllegalStateException("O card está cancelado"));
            dao.moveToColumn(cancelColumnId, cardId);
            System.out.println("O card foi cancelado");
            connection.commit();
        }catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
    }


}
