using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SuperWebSocket;
using SuperSocket.SocketBase;
using System.Web.Script.Serialization;
using WebSocketsServerDemo.Controllers;

namespace WebSocketsServerDemo
{
    class Server
    {
        private WebSocketServer appServer;
        private Controller _controller = new Controller();
        private List<Metadata> _userOnline = new List<Metadata>();

        #region CONFIGURACIÓN DEL SERVIDOR
        public void Setup()
        {
            Console.WriteLine();

            appServer = new WebSocketServer();

            if (!appServer.Setup(2012)) //Setup with listening port
            {
                Console.ReadKey();
                return;
            }

            appServer.NewSessionConnected   += new SessionHandler<WebSocketSession>(NewSessionConnected);
            appServer.SessionClosed         += new SessionHandler<WebSocketSession, CloseReason>(SessionClosed);
            appServer.NewMessageReceived    += new SessionHandler<WebSocketSession, string>(NewMessageReceived);

            Console.WriteLine();
        }

        
        public void Start()
        {
            if (!appServer.Start())
            {
                Console.WriteLine("Failed to start!");
                Console.ReadKey();
                return;
            }

            Console.WriteLine("El servidor se ha iniciado satisfactoriamente!Pulse cualquier tecla para ver las opciones.");
            Console.ReadKey();

            ShowAvailableOptions(); 

            char keyStroked;

            while (true)
            {
                keyStroked = Console.ReadKey().KeyChar;

                if (keyStroked.Equals('q'))
                {
                    Stop();
                    return;
                }
                
                ShowAvailableOptions();
                continue;
            }
        }

        public void Stop()
        {
            appServer.Stop();

            Console.WriteLine();
            Console.WriteLine("The server was stopped!");
        }

        public void ShowAvailableOptions()
        {
            Console.WriteLine();
            Console.WriteLine("Press 'q' key to stop the server.");
        }

        #endregion 

        private void NewMessageReceived(WebSocketSession session, string message)
        {
            dynamic obj = ConvertJsonObject(message);

            string type = obj.type;

            switch (type)
            {
                case "connected":

                    Metadata data = new Metadata{ CodeUser=obj.setCodeEmisor,
                                                  SessionId=session.SessionID.ToString(),
                                                  Ip = session.RemoteEndPoint.ToString()
                                                };
                 
                    _userOnline.Add(data);
                    enviarMensaje(session, message);

                    break;
                case "messsage":
                    Metadata _receiverUser = _userOnline.Find(x => x.CodeUser.Contains(obj.userEmisor));
                    enviarMensaje(session, message);

                    break;
            }

            Console.WriteLine(obj.nick + ": " + obj.send);
            
        }

        private void NewSessionConnected(WebSocketSession session)
        {
            Console.WriteLine();
            Console.WriteLine("Usuario Conectado: " + appServer.SessionCount);

        }

        private void SessionClosed(WebSocketSession session, CloseReason value)
        {
            Metadata _useroffline = _userOnline.Find(x => x.SessionId.Contains(session.SessionID.ToString()));
            
            _userOnline.Remove(_useroffline);

            Console.WriteLine();
            Console.WriteLine("Usuario Desconectado: " + appServer.SessionCount);
        }

        private void enviarMensaje(WebSocketSession session,string json) {

            foreach (WebSocketSession sessions in appServer.GetAllSessions())
            {
                if (session != sessions)
                    sessions.Send(json);
            }
        }

        #region ConvertJsonObject
        private dynamic ConvertJsonObject(String json)
        {
            
            var serializer = new JavaScriptSerializer();
            serializer.RegisterConverters(new[] { new DynamicJsonConverter() });
            dynamic obj = serializer.Deserialize(json, typeof(object));
            return obj;
        }
        #endregion
    }
}