"""
Brandon Goucher
10/6/2024
Version 1.0
Used purely for testing the database to make sure that it was connecting properly
"""

from pymongo import MongoClient

# Local MongoDB
client = MongoClient('mongodb://localhost:27017/')
db = client['mydatabase']
db.test.users.insert_one({'username': 'John Doe', 'password': 'sup'})
users_collection = db['users']
for user in users_collection.find():
    print(user)

# MongoDB Atlas Example
# client = MongoClient('mongodb+srv://<username>:<password>@cluster0.mongodb.net/<your-db>?retryWrites=true&w=majority')

# Test connection
try:
    # Check the server status
    print(client.server_info())
    print("Connected to MongoDB!")
    
except Exception as e:
    print("Error connecting to MongoDB:", e)