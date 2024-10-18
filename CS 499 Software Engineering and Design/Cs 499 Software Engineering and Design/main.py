"""
Brandon Goucher
10/6/2024
Version 1.0
This is a flask application that allows users to create accounts, login, and add clients to their list.
It uses MongoDB as the database and the PyMongo library to interact with the database.
The application uses sessions to keep track of the logged-in user and their clients.
The main route (/) displays the clients associated with the logged-in user.
The login route (/login) handles user authentication and registration.
The add_client route (/add_client) allows users to add new clients to their list.
The delete_client route (/delete_client/<client_id>) allows users to delete clients from their list.
The logout route (/logout) logs out the user by clearing the session.
The 404 error handler handles any invalid URLs and redirects the user to the login page.
"""

from flask import Flask, render_template, request, redirect, url_for, session, flash
from pymongo import MongoClient
from bson.objectid import ObjectId

app = Flask(__name__)
app.secret_key = 'mysecretkey'  # Required for session management

# connecting and setting up MongoDB
client = MongoClient("mongodb://localhost:27017/")
db = client['mydatabase']

# collections (MongoDB Tables)
users_collection = db['users']
clients_collection = db['clients']

# main route that displays the users clients
@app.route('/')
def index():
    # checks to see if there is a user logged in
    # if not send them to the login page
    if 'logged_in' not in session:
        return redirect(url_for('login'))

    # assuming there is a user logged in, grab there username
    current_user = users_collection.find_one({"username": session.get('username')})
    
    if not current_user:
        flash('User not found!', 'danger')
        return redirect(url_for('login'))
    
    # grab current user_id
    user_id = current_user.get('_id')
    
    # find all of the clients that match the usersID
    clients = list(clients_collection.find({"user_id": user_id}))
    
    # connect to the main index.html file
    return render_template('index.html', clients=clients, username=current_user['username'])

# main function to generate clients id
def get_next_client_id():
    # if there are no clients, set the first client to 1
    if clients_collection.count_documents({}) == 0:
        return 1
    # otherwise it will just continue from the most recent input
    else:
        last_client = clients_collection.find().sort("client_id", -1).limit(1)
        return last_client[0]["client_id"] + 1

# Login route
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        action = request.form.get('action')
        username = request.form.get('username')
        password = request.form.get('password')

        # connects to the html login file to check if the register button was clicked
        if action == 'register':
            # checks to see if the username exists inside of the user collection
            if users_collection.find_one({"username": username}):
                flash('Username already exists!', 'danger')
                return redirect(url_for('login'))

            # insert the new user into the MongoDB 'users' collection
            users_collection.insert_one({"username": username, "password": password})
            flash('Registration successful! Please log in.', 'success')
            return redirect(url_for('login'))

        elif action == 'login':
            # checks to see if the username exists inside of the user collection
            user = users_collection.find_one({"username": username})

            if user and user['password'] == password:
                session['logged_in'] = True
                session['username'] = username  # Store the username in the session
                flash('Login successful!', 'success')
                return redirect(url_for('index'))
            else:
                flash('Invalid username or password!', 'danger')

    return render_template('login.html')

# Route to change a client's service (requires login)
@app.route('/change/<client_id>', methods=['GET', 'POST'])
def change_client(client_id):
    if 'logged_in' not in session:
        return redirect(url_for('login'))

    if request.method == 'POST':
        new_service = request.form['service']
        if new_service.isdigit() and int(new_service) in [1, 2]:
            # Update client's service in MongoDB
            clients_collection.update_one({'_id': ObjectId(client_id)}, {'$set': {'service': int(new_service)}})
            return redirect(url_for('index'))
        else:
            return "Invalid service choice. Please enter 1 or 2.", 400

    client = clients_collection.find_one({'_id': ObjectId(client_id)})
    if client:
        return render_template('change.html', client=client)
    return "Client not found", 404

# Route to add a new client (requires login)
@app.route('/add', methods=['GET', 'POST'])
def add_client():
    if 'logged_in' not in session:
        return redirect(url_for('login'))

    if request.method == 'POST':
        client_name = request.form['client_name']
        service = request.form['service']

        # Validate the form inputs
        if client_name and service.isdigit() and int(service) in [1, 2]:
            next_id = get_next_client_id()
            
            # Fetch the current user's ID
            current_user = users_collection.find_one({"username": session.get('username')})
            if not current_user:
                flash('User not found!', 'danger')
                return redirect(url_for('login'))
            
            user_id = current_user.get('_id')

            # Insert a new client into the MongoDB collection with user_id
            new_client = {'client_id': next_id, 'name': client_name, 'service': int(service), 'user_id': user_id}
            clients_collection.insert_one(new_client)
            return redirect(url_for('index'))
        else:
            return "Invalid input. Please try again.", 400

    return render_template('add.html')

# Route to delete a client (requires login)
@app.route('/delete/<client_id>', methods=['POST'])
def delete_client(client_id):
    if 'logged_in' not in session:
        return redirect(url_for('login'))

    # Delete the client from MongoDB collection
    result = clients_collection.delete_one({'_id': ObjectId(client_id)})
    if result.deleted_count > 0:
        return redirect(url_for('index'))
    return "Client not found", 404

# Logout route
@app.route('/logout')
def logout():
    session.pop('logged_in', None)
    session.pop('username', None)  # Clear the username from the session
    flash('You have been logged out.', 'info')
    return redirect(url_for('login'))

if __name__ == "__main__":
    app.run(debug=True)