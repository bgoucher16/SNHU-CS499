#include <iostream>
#include <map>
#include <string>
#include <limits>
#include <ios>

using namespace std;

//Lines 11-17 Are the main bit being converted from this little map to a database.

map<int, pair<string, int>> clients = { 
    {1, {"Bob Jones", 1}},
    {2, {"Sarah Davis", 1}},
    {3, {"Amy Friendly", 1}},
    {4, {"Johnny Smith", 1}},
    {5, {"Carol Spears", 1}}
};

/*
Users:
_id: "auto generated"
string username: ""
string password: ""

Clients:
id: start at 1 and increment infinitely
string name: ""
int service: 1 or 2
user_id == _id
*/

string username;
string password;

//Moved both of the DisplayInfo prompts into global strings since I was getting errors otherwise
//Also better readability
string prompt1 = "Enter the number of the client that you wish to change: \n";             
string prompt2 = "Enter the client's new service choice (1 = Brokerage, 2 = Retirement): \n";

// user_id = 'ddsafdsa'

int CheckUserPermissionAccess()
{
    if (password == "1234")
    {
        return 1;
    }
    else
    {
        return 0;
    }
}

void ChangeCustomerChoice()
{
    int changeChoice, newService;

    //Diplays prompt1 asking for clients number
    cout << prompt1;
    //while the cin is not a integer it wont allow anymore information passed this point
    while(!(cin >> changeChoice)){
        //clears the previous input
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        cout << "Invalid input. " << endl;
        cout << prompt1;
    }

    //Another check to see if the users input fits the parameters we are looking for
    if (changeChoice < 1 || changeChoice > 5)
    {
        cout << "Invalid client number. Please try again." << endl;
        return;
    }

    //If those pass then continue to the next prompt
    //Displays prompt2 asking for the new service choice
    cout << prompt2 << endl;
    while (!(cin >> newService)){
        cout << "Invalid input. Please enter a number: ";
        cin.clear();
        cin.ignore(std::numeric_limits<streamsize>::max(), '\n');
        cout << prompt2 << endl;
    }
    
    if (newService != 1 && newService != 2)
    {
        cout << "Invalid service choice. Please try again." << endl;
        return;
    }

    //Finds the clients inside of the hashmap above.
    if (clients.find(changeChoice) != clients.end())
    {
        clients[changeChoice].second = newService;
        cout << "Client's service choice updated successfully." << endl;
    }
    else
    {
        cout << "Client not found." << endl;
    }
}

void DisplayInfo()
{
    cout << "\nClient's Name | Service Selected (1 = Brokerage, 2 = Retirement)" << endl;
    for (const auto &client : clients)
    {
        cout << client.first << ". " << client.second.first << " selected option " << client.second.second << endl;
    }
    cout << endl;
}

int main()
{
    int answer;
    int choice;

    cout << "Hello! Welcome to our Investment Company" << endl;
    cout << "Enter your username: ";
    //Since the username and password are strings they can be any character, 
    //only thing we would implement is a character limit to force under 20 characters or so
    cin >> username;                    
    cout << "Enter your password: ";
    cin >> password;

    answer = CheckUserPermissionAccess();

    if (answer == 1)
    {
        cout << "Access granted!" << endl;
    }
    else
    {
        cout << "Access denied!" << endl;
        return 0;
    }

    while (answer == 1)
    {
        cout << "What would you like to do?" << endl;
        cout << "1. View all clients" << endl;
        cout << "2. Change client's choice in account type" << endl;
        cout << "3. Exit" << endl;
        
        while (!(cin >> choice)){
            cout << "Invalid input. Please enter a number: ";
            cin.clear();
            cin.ignore(std::numeric_limits<streamsize>::max(), '\n');
        }

        if (choice != 1 && choice != 2 && choice != 3)
        {
            cout << "Invalid choice. Please try again." << endl;
            continue;
        }

        if (choice == 1)
        {
            DisplayInfo();
        }
        else if (choice == 2)
        {
            ChangeCustomerChoice();
        }
        else if (choice == 3)
        {
            break;
        }
    }

    return 0;
}