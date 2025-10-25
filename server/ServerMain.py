import json
import socket
import threading
from groq_api_key import key
from groq import Groq

client = Groq(
    api_key=key
)

model = "llama-3.3-70b-versatile"

from time import sleep

class Client:
    id :str
    connection = None
    def __init__(self, connection, id):
        self.connection = connection
        self.id = id
        self.messages = []
        pass

    def send_message(self, message):
        connection = self.connection
        json_str = json.dumps(message.__dict__)
        print(json_str)
        connection.sendall(json_str.encode())
    def loop(self):
        while True:
            connection = self.connection
            print("Waiting for data from client...")
            data = connection.recv(1024)
            if not data:
                break

            message = data.decode()
            print("Received:", message)

            response = client.chat.completions.create(
                messages=self.messages,
                model=model,
                max_tokens=2048,
                stream=True,
            )

            message = ''
            self.send_message(message, 1)
            for chunk in response:
                if chunk.choices[0].delta.content:
                    # message += chunk.choices[0].delta.content
                    token = chunk.choices[0].delta.content
                    self.send_message(Message(token, 0))
                    print(chunk.choices[0].delta.content, end="", flush=True)
            self.send_message(Message("", -1))


            # self.send_message(Message("a", 1))
            # sleep(0.01)
            # self.send_message(Message("Message from server", 0))
            # sleep(0.01)
            # self.send_message(Message("Message from server", 0))
            # sleep(0.01)
            # self.send_message(Message("Message from server", 0))
            # sleep(0.01)
            # self.send_message(Message("Message from server", 0))
            # sleep(0.01) 
            # self.send_message(Message("Message from server", 0))
            # sleep(0.01) 
            # self.send_message(Message("Message from server", 0))
            # sleep(0.01)
            # self.send_message(Message("Message from server", -1))





class ServerMain:

    def __init__(self):
        self.start_server()




    def handle_connections(self, server_socket):
        while True:
            print("Waiting for a connection...")
            connection, client_address = server_socket.accept()
            try:
                print("Connection from", client_address)
                data = json.loads(connection.recv(1024).decode())
                client_info = ClientInfo(**data)
                
                
                print("id: "+client_info.id)
                client = Client(connection,client_info.id)
                threading.Thread(target=client.loop()).start()
                
            except Exception as e:
                print("An error occurred:", e)
                connection.close()

    def start_server(self):
        # creates the server socket
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        server_address = ('100.68.170.117', 24483)
        server_socket.bind(server_address)

        # Listen for incoming connections
        server_socket.listen(1)
        print("Server is listening on", server_address)
        self.handle_connections(server_socket)

 



class Message:
    message: str
    type: int    
    def __init__(self, content, type):
        self.message = content
        self.type = type

class ClientInfo:
    id: str
    def __init__(self, id):
        self.id = id

    


if __name__ == "__main__":
    ServerMain()