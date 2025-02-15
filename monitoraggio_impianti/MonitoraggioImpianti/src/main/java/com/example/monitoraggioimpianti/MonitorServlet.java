package com.example.monitoraggioimpianti;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.Date;

@WebServlet("/setImpiantoStatus")
public class MonitorServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/segnalazioni_impianti";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Loads driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Driver JDBC MySQL non trovato", e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String idImpianto = request.getParameter("id_impianto");
        String idPalinsesto = request.getParameter("id_palinsesto");
        String idCartellone = request.getParameter("id_cartellone");
        String durataVisualizzazione = request.getParameter("durata");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO segnalazioni_impianti.Segnalazioni (id_impianto, id_palinsesto, id_cartellone, durata, timestamp) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, idImpianto);
            stmt.setString(2, idPalinsesto);
            stmt.setString(3, idCartellone);
            stmt.setString(4, durataVisualizzazione);
            stmt.setTimestamp(5, new Timestamp(new Date().getTime()));

            stmt.executeUpdate();
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Inserimento nel database completato con successo.");
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement stmt = null;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT i.id_impianto, " +
                         "i.descrizione, " +
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
            out.println("<table><tr><th>ID Impianto</th><th>Descrizione</th><th>Ultima Segnalazione</th><th>Stato</th></tr>");
            while (rs.next()) {
                int idImpianto = rs.getInt("id_impianto");
                String descrizione = rs.getString("descrizione");
                String ultimaSegnalazione = rs.getString("ultima_segnalazione");
                if (ultimaSegnalazione == null) {
                    ultimaSegnalazione = "---";
                }
                String attivo = rs.getString("attivo");
                String output = "<tr><td>" + idImpianto + "</td><td>" + descrizione + "</td><td>" + ultimaSegnalazione + "</td>";
                if (attivo.equalsIgnoreCase("attivo")) {
                    output += "<td class=attivo>" + attivo + "</td></tr>";
                }
                else {
                    output += "<td class=inattivo>" + attivo + "</td></tr>";
                }
                out.println(output);
            }
            out.println("</table>");

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Errore: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    */
}
