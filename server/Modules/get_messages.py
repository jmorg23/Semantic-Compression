import os
import json

def get_messages(user_uuid:str, conversation_uuid:str):
    convo_path = os.path.join("users", user_uuid, "conversations", conversation_uuid, "coversation.json")

    with open(convo_path, "r") as f:
        return json.load(f)["messages"]