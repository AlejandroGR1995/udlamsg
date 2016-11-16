using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WebSocketsServerDemo
{
    class TemplateData
    {
        String send = @" data =
                                {
                                    'type': ':send:',
                                    'group': ':group_id:',
                                    'code' : ':code_id:',
                                    'nick' : ':nick_data:',
                                    'send' : ':send_data:',
                                    'file' : ':file_data:'
                                }   
                             ";
        
    }
}
