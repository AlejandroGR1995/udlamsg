using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebSocketsServerDemo
{
    class Metadata
    {
        String ip, sessionId, codeUser;

        public String CodeUser
        {
            get { return codeUser; }
            set { codeUser = value; }
        }

        public String SessionId
        {
            get { return sessionId; }
            set { sessionId = value; }
        }

        public String Ip
        {
            get { return ip; }
            set { ip = value; }
        }
    }
}
