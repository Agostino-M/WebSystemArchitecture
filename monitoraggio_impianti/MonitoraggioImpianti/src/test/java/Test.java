import java.sql.*;

public class Test {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/segnalazioni_impianti";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement stmt = null;

        String sql = "SELECT i.id_impianto, " +
                     "MAX(s.timestamp) AS ultima_segnalazione, " +
                     "CASE " +
                     "    WHEN MAX(s.timestamp) > NOW() - INTERVAL 2 MINUTE THEN 'attivo' " +
                     "    ELSE 'non attivo' " +
                     "    END AS attivo " +
                     "FROM Impianti i " +
                     "    LEFT JOIN Segnalazioni s ON i.id_impianto = s.id_impianto " +
                     "GROUP BY i.id_impianto " +
                     "ORDER BY i.id_impianto; ";

        stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id_impianto");
            String name = rs.getString("attivo");
            System.out.println("ID: " + id + ", Descrizione: " + name + "<br>");
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}
