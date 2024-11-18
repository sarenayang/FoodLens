from flask import Flask, request, jsonify
import uuid
import sqlite3
import datetime

app = Flask(__name__)
app.config["DEBUG"] = True # Enable debug mode to enable hot-reloader.



# Accounts Server API
@app.route('/accounts', methods=['GET'])
def accounts():
    con = sqlite3.connect('accounts.db')
    cursor = con.execute("SELECT * from Accounts;")
    accounts = []
    for row in cursor:
        accounts.append(row)
    con.close()
    return accounts

@app.route('/accounts/findid/<name>', )
def get_id(name):
    for account in accounts():
        if name==account[1]:
            return account[0]
    raise Exception("No account with such name, please try another account name")
    

@app.route('/accounts', )
def create_account(name, email, password, friends, reviews):
    num_friends=len(friends.split(','))
    num_reviews=len(reviews.split(','))
    #for friend in friends :
        
    list_friends=' '.join(friends)
    list_reviews=' '.join(reviews)
    con = sqlite3.connect('accounts.db')
    Query="INSERT INTO Accounts (name, email, password, num_friends, num_reviews, friend_list,review_list) VALUES (?,?,?,?,?,?,?)"
    cursor = con.execute(Query, (name, email, password, num_friends, num_reviews, list_friends, list_reviews))
    con.commit()
    con.close()
    return get_id(name)

@app.route('/accounts/<int:Id>', methods=['GET'])
def get_account(Id):
    con = sqlite3.connect('accounts.db')
    query="SELECT * from Accounts WHERE ID=?;"
    cursor = con.execute(query, (str(Id),))
    account=[]
    for row in cursor:
        account.append(row)
    con.close()
    return account[0]

@app.route('/accounts/<int:Id>/name', methods=['GET'])
def get_account_name(Id):
    con = sqlite3.connect('accounts.db')
    query="SELECT name from Accounts WHERE ID=?;"
    cursor = con.execute(query, (str(Id),))
    name=[]
    for row in cursor:
        name.append(row)
    con.close()
    return name[0][0]

@app.route('/accounts/<int:Id>/email', methods=['GET'])
def get_account_email(Id):
    con = sqlite3.connect('accounts.db')
    query="SELECT email from Accounts WHERE ID=?;"
    cursor = con.execute(query, (str(Id),))
    email=[]
    for row in cursor:
        email.append(row)
    con.close()
    return email[0][0]

@app.route('/accounts/<int:Id>/password', methods=['GET'])
def get_account_pswd(Id):
    con = sqlite3.connect('accounts.db')
    query="SELECT password from Accounts WHERE ID=?;"
    cursor = con.execute(query, (str(Id),))
    pswd=[]
    for row in cursor:
        pswd.append(row)
    con.close()
    return pswd[0][0]

@app.route('/accounts/<int:Id>/friends/id', methods=['GET'])
def get_friends(Id):
    con = sqlite3.connect('accounts.db')
    query="SELECT friend_list from Accounts WHERE ID=?;"
    cursor = con.execute(query, (str(Id),))
    friend=[]
    for row in cursor:
        friend.append(row)
    con.close()
    friend_list=friend[0][0].split(',')
    return friend_list

@app.route('/accounts/<int:Id>/friends', methods=['GET'])
def get_account_friends(Id):
    friend_list=get_friends(Id)
    friend_acc=[]
    for ID in friend_list:
        friend_acc.append(get_account(ID))
    return friend_acc

#check if already friends then adds friend
def add_friend_task(Id1, Id2):
    friends=get_friends(Id1)
    already_friends=False
    for friend in friends:
            if friend==Id2:
                already_friends=True
                return
                #raise Exception("already friends")
    
    friends.append(Id2)
    
    str_friends=','.join(friends)
   
    con = sqlite3.connect('accounts.db')
    query= "UPDATE Accounts SET friend_list=? WHERE Id= ?;"
    con.execute(query, (str_friends,str(Id1)))
    con.commit()
    con.close()
    return
    
    
@app.route('/accounts/<account_id>/friends/add', methods=['GET'])
def add_friend(account_id):

    Id = request.args.get('id', '')
    
    if len(Id) > 0:
        
        add_friend_task(account_id, Id)
        add_friend_task( Id, account_id)
        
    return get_account_friends(account_id)
            
    
    
@app.route('/account/<account_id>/reviews', methods=['GET'])
def get_reviews(account_id):
    
    con = sqlite3.connect('accounts.db')
    query="SELECT review_list from Accounts WHERE ID=?;"
    cursor = con.execute(query, (str(Id),))
    reviews=[]
    for row in cursor:
        reviews.append(row)
    con.close()
    reviews_list=reviews[0][0].split(',')
    return reviews_list

@app.route('/reviews')
def reviews():
    con = sqlite3.connect('accounts.db')
    cursor=con.execute("SELECT * from Reviews")
    reviews = []
    for row in cursor:
        reviews.append(row)
    con.close()
    return reviews

    
@app.route('/accounts/<account_id>/reviews/<restaurant>/<dish>/add', methods=['GET'])
def add_review(account_id):
    grade = request.args.get('grade', '')
    if len(grade) > 0:
        create_review(grade,restaurant,dish,account)
    return

@app.route('/accounts/reviews/create', methods=['POST'])
def create_review(grade,restaurant,dish,account):
    con= sqlite3.connect('accounts.db')
    query=("INSERT INTO Reviews(Id ,date,Restaurant_id,Dish_id,grade,account_id) VALUES (NULL,?,?,?,?,?);")
    date=datetime.datetime.now()
    con.execute(query,(str(date),restaurant,dish,grade,account))
    con.commit()
    con.close()
    return

@app.route('/restaurants')
def restaurants():
    con = sqlite3.connect('accounts.db')
    cursor=con.execute("SELECT * from Reviews")
    restaurant = []
    for row in cursor:
        restaurant.append(row)
    con.close()
    return restaurant



@app.route('/dishes')
def dishes():
    con = sqlite3.connect('accounts.db')
    cursor=con.execute("SELECT * from Reviews")
    dish= []
    for row in cursor:
        dish.append(row)
    con.close()
    return dish

@app.route('/dishes/ar/id_search/<Id>')
def get_dish_file_id(Id):
    dishes=dishes()
    file="empty"
    for dish in dishes:
        if Id==dish[0]:
            file=dish[2] 
    return file
            
@app.route('/dishes/ar/name_search/<name>')
def get_dish_file_name(Id):
    dishes=dishes()
    file="empty"
    for dish in dishes:
        if name==dish[1]:
            file=dish[2] 
    return file


if __name__ == '__main__':
    
    app.run( host="0.0.0.0") 
    