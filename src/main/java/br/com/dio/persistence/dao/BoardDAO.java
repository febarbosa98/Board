package br.com.dio.persistence.dao;


import br.com.dio.persistence.entity.BoardEntity;
import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardDAO {

    private final Connection connection;

    public BoardEntity insert(final BoardEntity entity) throws SQLException{
        var sql = "INSERT INTO BOARDS (name) values (?)";
        try(var stratement = connection.prepareStatement(sql)){
            stratement.setString(1,entity.getName());
            stratement.executeUpdate();
            if (stratement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }
        return entity;
    }

    public void delete(final Long id) throws SQLException{
        var sql = "DELETE FROM BOARDS WHERE id = ?;";
        try(var stratement = connection.prepareStatement(sql)){
            stratement.setLong(1, id);
            stratement.executeUpdate();
        }
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException{
        var sql = "SELECT id, name FROM BOARDS WHERE id = ?;";
        try(var stratement = connection.prepareStatement(sql)){
            stratement.setLong(1, id);
            stratement.executeQuery();
            var resultSet = stratement.getResultSet();
            if (resultSet.next()){
                var entity = new BoardEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                return Optional.of(entity);
            }
            return Optional.empty();
        }
    }

    public boolean exists(final Long id) throws SQLException{
        var sql = "SELECT 1 FROM BOARDS WHERE id = ?;";
        try(var stratement = connection.prepareStatement(sql)){
            stratement.setLong(1, id);
            stratement.executeQuery();
            return stratement.getResultSet().next();
        }
    }
}
