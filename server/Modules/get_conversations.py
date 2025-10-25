import uuid
import os
import json

def get_conversations(user_uuid):
    try:
        user_conversations = os.listdir(f"server/users/{user_uuid}/conversations/")

        ''' {
            "conversation_uuid_1": "conversation_name_1",
            "conversation_uuid_2": "conversation_name_2",
            ...
        }
        '''
        for i in range(len(user_conversations)):
            with open(f"server/users/{user_uuid}/conversations/{user_conversations[i]}/metadata.json", "r") as f:
                try:
                    metadata = json.load(f)
                except FileNotFoundError:
                    pass
                user_conversations[i] = {
                    "conversation_uuid": user_conversations[i],
                    "conversation_name": metadata["conversation_name"]
                }
    except FileNotFoundError:
        user_conversations = []
        os.makedirs(f"server/users/{user_uuid}/conversations/", exist_ok=True)
    return user_conversations