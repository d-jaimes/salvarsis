package dao;

import vos.AbonoFuncionVos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by María del Rosario on 10/04/2017.
 */
public class AbonoFuncionDao extends DAO {
    private static final String TABLA = "ABONO_FUNCION";

    public ArrayList<AbonoFuncionVos> getAbonoFuncions() throws SQLException {
        ArrayList<AbonoFuncionVos> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA;

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        while (rs.next()) {
            AbonoFuncionVos obj = resultToAbonoFuncion(rs);
            list.add(obj);
        }
        return list;
    }

    public AbonoFuncionVos getAbonoFuncion(Long idfest, Long idusu, String tipoId, Long idFuncion, Long idLocalidad ) throws SQLException {
        AbonoFuncionVos obj = null;

        String sql = "SELECT * FROM " + TABLA + " WHERE IDFEST = " + idfest;
        sql += " AND IDUSU = " + idusu +", ";
        sql += " AND TIPOID = '" + tipoId +"', ";
        sql += " AND IDFUNCION = " + idFuncion +" ";
        sql += " AND IDLOCALIDAD = " + idLocalidad +" ";
        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        ResultSet rs = prepStmt.executeQuery();

        if (rs.next()) {
            obj = resultToAbonoFuncion(rs);
        }
        return obj;
    }

    public void addAbonoFuncion(AbonoFuncionVos pObj) throws SQLException
    {
        String sql = "INSERT INTO " + TABLA + " VALUES (";
        sql += pObj.getIdFest() + ", ";
        sql += pObj.getIdusu() + ", '";
        sql += pObj.getTipoId() + "', ";
        sql += pObj.getIdFuncion() + ", ";
        sql += pObj.getIdLocalidad() + ")";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();


    }

    public void deleteAbonoFuncion(Long idfest, Long idusu, String tipoId, Long idFuncion, Long idLocalidad) throws SQLException {
        String sql = "DELETE FROM " + TABLA;
        sql += " WHERE IDFEST = " + idfest;
        sql += " AND IDUSU = " + idusu +", ";
        sql += " AND TIPOID = '" + tipoId +"', ";
        sql += " AND IDFUNCION = " + idFuncion +" ";
        sql += " AND IDLOCALIDAD = " + idLocalidad +" ";

        PreparedStatement prepStmt = conn.prepareStatement(sql);
        resources.add(prepStmt);
        prepStmt.execute();
    }


    private AbonoFuncionVos resultToAbonoFuncion(ResultSet rs) throws SQLException {
        AbonoFuncionVos obj = new AbonoFuncionVos();
        obj.setIdLocalidad(rs.getLong("IDLOCALIDAD"));
        obj.setIdFuncion(rs.getLong("IDFUNCION"));
        obj.setIdusu(rs.getLong("IDUSU"));
        obj.setIdFest(rs.getLong("IDFEST"));
        obj.setTipoId(rs.getString("TIPOID"));
        return obj;

    }
}
