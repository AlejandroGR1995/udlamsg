﻿using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebSocketsServerDemo
{
    class connection
    {
        private string getConnectionString()
            {
                return ConfigurationManager.ConnectionStrings["ControlPersonal"].ConnectionString;
            }

            public DataSet getDataMariaDB(string sql)
            {
                DataSet dataSet = new DataSet();
                using (MySqlConnection connection = new MySqlConnection(getConnectionString()))
                {
                    connection.Open();
                    using (MySqlDataAdapter adapter = new MySqlDataAdapter(sql, connection))
                    {
                        adapter.Fill(dataSet);
                    }
                    connection.Close();
                    connection.Dispose();
                }
                return dataSet;
            }

            public bool sendSetDataMariaDB(string sql)
            {
                using (MySqlConnection connection = new MySqlConnection(getConnectionString()))
                {
                    using (MySqlCommand command = new MySqlCommand(sql))
                    {
                        command.Connection = connection;
                        connection.Open();

                        try
                        {
                            if (command.ExecuteNonQuery() > 0)
                            {
                                return true;
                            }
                        }
                        catch (System.Exception e)
                        {
                            writeConnectionLogs("La sentencia sql:" + sql + "<------->" + " presenta el siguiente error: " + e.Message);
                        }
                    }
                    connection.Close();
                    connection.Dispose();
                }
                return false;
            }

            public bool sendSetDataTransaction(List<string> sql)
            {
                using (MySqlConnection connection = new MySqlConnection(getConnectionString()))
                {
                    using (MySqlCommand command = connection.CreateCommand())
                    {
                        connection.Open();
                        using (MySqlTransaction transaction = connection.BeginTransaction(IsolationLevel.ReadCommitted))
                        {
                            command.Transaction = transaction;
                            try
                            {
                                int ammountsql = sql.Count;
                                int correctsql = 0;

                                for (int i = 0; i < sql.Count; i++)
                                {
                                    command.CommandText = sql[i].ToString();
                                    if (command.ExecuteNonQuery() > 0)
                                    {
                                        correctsql = correctsql + 1;
                                    }
                                }

                                if (ammountsql == correctsql)
                                {
                                    transaction.Commit();
                                    return true;
                                }
                                else
                                {
                                    transaction.Rollback();
                                }
                            }
                            catch (System.Exception e)
                            {
                                transaction.Rollback();
                                writeConnectionLogs("La lista de sentencias sql:" + sql + "<------->" + " presenta el siguiente error: " + e.Message);
                            }
                        }
                        connection.Close();
                        connection.Dispose();
                    }
                }
                return false;
            }

            #region Write Logs
            private void writeConnectionLogs(string mensaje)
            {
                string mydocpath = Environment.GetFolderPath(Environment.SpecialFolder.MyDocuments);
                using (StreamWriter sw = new StreamWriter(mydocpath + @"\logs.txt"))
                {
                    sw.WriteLine(mensaje);
                }
            }
            #endregion

        }
 }
