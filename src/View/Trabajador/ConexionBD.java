package View.Trabajador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {


    // Singleton
    private static ConexionBD instancia;


    private Connection connection;


    private final String URL =
            "jdbc:mysql://localhost:3306/workbridge_db?serverTimezone=UTC";


    private final String USER =
            "root";


    private final String PASSWORD =
            "admin";



    private ConexionBD(){

        conectar();

    }




    private void conectar(){


        try{


            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );


            connection =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );


            System.out.println(
                    "Conexión exitosa a MySQL"
            );


        }catch(
                ClassNotFoundException |
                SQLException e
        ){

            System.out.println(
                    "Error conexión: "
                    +e.getMessage()
            );

        }

    }




    public static ConexionBD getInstancia(){


        if(instancia == null){

            instancia =
                    new ConexionBD();

        }


        return instancia;

    }




    public Connection getConnection(){


        try{


            if(connection == null
                    || connection.isClosed()){


                conectar();

            }


        }catch(SQLException e){


            e.printStackTrace();

        }


        return connection;

    }


}