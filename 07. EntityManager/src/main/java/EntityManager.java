import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class EntityManager {

    private DataSource dataSource;
    private JdbcTemplate template;

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
        this.template = new JdbcTemplate(dataSource);
    }


    //createTable("account", User.class)
    public <T> void createTable(String tableName, Class<T> entityClass) throws IllegalAccessException {
        //TODO: сгенерировать createTable на основе класса
        //create table account (id integer, firstName varchar(255), ....

        StringBuilder sqlCreate = new StringBuilder();

        sqlCreate.append("create table ")

                .append(tableName)

                .append(" (");

        Field fields[] = entityClass.getDeclaredFields();
        int length = fields.length;
        for (Field field : fields) {
            length--;
            String type = null;
            String nameOfField = field.getName();


            switch(field.getType().getSimpleName()){
                case "char":
                    type = "char(30)";
                    break;
                case "String":
                    type = "varchar(30)";
                            break;
                case "int":
                    type = "integer";
                    break;
                case "Long":
                    type = "bigint";
                    break;
                case "boolean":
                    type = "boolean";
                    break;
            }
            sqlCreate.append(nameOfField)
                    .append(" ")
                    .append(type);

            if(length != 0){
                sqlCreate.append(", ");
            }
        }
        sqlCreate.append(")");
        template.execute(sqlCreate.toString());
    }

    public void save(String tableName, Object entity) throws IllegalAccessException {
        Class<?>  classOfEntity = entity.getClass();
        //сканируем его поля, значения полей, генерируем insert into
        StringBuilder sqlSave = new StringBuilder();
        sqlSave.append("INSERT INTO ")
                .append("\"")
                .append(tableName)
                .append("\"")
                .append(" (");
        Field fields[] = classOfEntity.getDeclaredFields();
        int length = fields.length;
        for (Field field : fields) {
            length--;
            String nameOfFields = field.getName();
            sqlSave.append("\"")
                    .append(nameOfFields)
                    .append("\"");

            if(length != 0){
                sqlSave.append(", ");
            }
        }
        sqlSave.append(") ")
                .append("VALUES ")
                .append("(");
        int length1 = fields.length;
        for (Field field : fields) {
            length1--;
            field.setAccessible(true);
            Object valueOfFields = field.get(entity);
            sqlSave.append("'")
                    .append(valueOfFields.toString())
                    .append("'");

            if(length1 != 0){
                sqlSave.append(", ");
            }
        }
        sqlSave.append(")");
        template.execute(sqlSave.toString());
    }

    //User user = entityManager.findById("account", User.class, Long.class, 10L)
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue){
        //сгенерировать select
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        T user = null;

        StringBuilder sqlFindById = new StringBuilder();

        sqlFindById.append("SELECT * FROM ")
                .append("\"")
                .append(tableName)
                .append("\"")
                .append(" WHERE id = ")
                .append(idValue);

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlFindById.toString());

            Constructor<T> constructor = resultType.getConstructor();
            user = constructor.newInstance();
            Field fields[] = resultType.getDeclaredFields();
            while(resultSet.next()){
                for(Field field: fields){
                    field.setAccessible(true);
                    field.set(user, resultSet.getObject(field.getName()));
                }
            }
        }catch (SQLException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return user;
    }
}
