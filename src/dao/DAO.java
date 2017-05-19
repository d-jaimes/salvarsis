package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class DAO
{
   public static final String USUARIO_ORACLE = "ISIS2304B171710";

   protected List<Statement> resources;
   protected Connection conn;

   public DAO ()
   {
      resources = new ArrayList<Statement>();
   }

   public void cerrarResource()
   {
      for (Statement s: resources)
      {
         if (s instanceof PreparedStatement) {
            try {
               s.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
   }

   public void setConnection (Connection newConn)
   {
      conn = newConn;
   }

   public String dateToSting (Date fecha)
   {
      final String dateStringFormat = "yyyy-MM-dd";
      SimpleDateFormat dateFormat = new SimpleDateFormat( dateStringFormat );
      return String.format( "TO_DATE( '%s', '%s' )", dateFormat.format( fecha ), dateStringFormat );
   }
}
