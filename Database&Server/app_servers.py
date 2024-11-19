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

@app.route('/accounts/findid/<name>',  methods=['GET'])
def get_id(name):
    for account in accounts():
        if name==account[1]:
            return str(account[0])
    raise Exception("No account with such name, please try another account name")
    

@app.route('/accounts/create',  methods=['GET'])
def create_account():
    name= request.args.get('name', '')
    email=request.args.get('email','')
    password=request.args.get('password','')
    
    if (len(name) > 0) and (len(email)>0) and (len(password)>0):
        Id=create_account_task(name,email, password, [],[])
    else :
        raise Exception ("Account creation unsuccessful, please specify name email and password")
    return Id

def create_account_task(name, email, password, friends, reviews):
    
    num_friends=len(friends)
    num_reviews=len(reviews)
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
        for item in row: 
            account.append(item)
        
    con.close()
    return account

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
    if friend[0][0]=="":
        return []
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
    if len(friends)>0:
        already_friends=False
        for friend in friends:
                if friend==Id2:
                    already_friends=True
                    return
                #raise Exception("already friends")
    
        friends.append(Id2)
    else:
        friends=[Id2]
        
    str_friends=','.join(friends)
   
    con = sqlite3.connect('accounts.db')
    query= "UPDATE Accounts SET friend_list=? WHERE Id= ?;"
    con.execute(query, (str_friends,str(Id1)))
    query= "UPDATE Accounts SET num_friends=? WHERE Id= ?;"
    con.execute(query, (str(len(friends)),str(Id1)))
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
            
    
    
@app.route('/accounts/<account_id>/reviews', methods=['GET'])
def get_reviews(account_id):
    
    con = sqlite3.connect('accounts.db')
    query="SELECT review_list from Accounts WHERE ID=?;"
    cursor = con.execute(query, (str(account_id),))
    reviews=[]
    for row in cursor:
        reviews.append(row)
    con.close()
    if reviews[0][0] =="":
        return []
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

@app.route('/reviews/<review_id>/grade')
def get_review_grade(review_id):
    con = sqlite3.connect('accounts.db')
    query="SELECT grade from Reviews WHERE Id=?;"
    cursor=con.execute(query, (str(review_id),))
    grade=[]
    for row in cursor:
        grade.append(row)
    con.close()
    return str(grade[0][0])

@app.route('/reviews/<review_id>/restaurant')
def get_review_restaurant(review_id):
    con = sqlite3.connect('accounts.db')
    query="SELECT Restaurant_id from Reviews WHERE Id=?;"
    cursor=con.execute(query, (str(review_id),))
    restaurant=[]
    for row in cursor:
        restaurant.append(row)
    con.close()
    if restaurant==[]:
        raise Exception ("review is empty {}".format(review_id))
    return str(restaurant[0][0])

@app.route('/reviews/<review_id>/dish')
def get_review_dish(review_id):
    con = sqlite3.connect('accounts.db')
    query="SELECT Dish_id from Reviews WHERE Id=?;"
    cursor=con.execute(query, (str(review_id),))
    dish=[]
    for row in cursor:
        dish.append(row)
    con.close()
    if dish==[]:
        raise Exception ("dish is empty {}".format(review_id))
    return str(dish[0][0])

@app.route('/reviews/<review_id>/account')
def get_review_account(review_id):
    con = sqlite3.connect('accounts.db')
    query="SELECT account_id from Reviews WHERE Id=?;"
    cursor=con.execute(query, str(review_id))
    account=[]
    for row in cursor:
        account.append(row)
    con.close()
    return str(account[0][0])

def same_dish_review(account, dish):
    cacas=[]
    reviews=get_reviews(account)
    for review in reviews:
        cacas.append (get_review_dish(review))
        
        if str(get_review_dish(review))==str(dish):
            con = sqlite3.connect('accounts.db')
            query="DELETE  from Reviews WHERE ID=?;"
            cursor=con.execute(query, (review,))
            reviews.remove(review)
            
            str_reviews=','.join(reviews)
        
            query= "UPDATE Accounts SET review_list=? WHERE Id= ?;"
            con.execute(query, (str_reviews,str(account)))
            con.commit()
            
    

@app.route('/accounts/<account_id>/reviews/<restaurant>/<dish>/add', methods=['GET'])
def add_review(account_id,restaurant,dish):
    grade = request.args.get('grade', '')
    
    
    if len(grade) > 0:
        same_dish_review(account_id,dish)
        ID=create_review(grade,restaurant,dish,account_id)
        
        reviews=get_reviews(account_id)
        reviews.insert(0,str(ID))
        str_reviews=','.join(reviews)
        con = sqlite3.connect('accounts.db')
        query= "UPDATE Accounts SET review_list=? WHERE Id= ?;"
        con.execute(query, (str_reviews,str(account_id)))
        query= "UPDATE Accounts SET num_reviews=? WHERE Id= ?;"
        con.execute(query, (str(len(reviews)),str(account_id)))
        con.commit()
        con.close()
        return get_reviews(account_id)
    raise Exception("Please insert grade")

def get_review_id(date):
    last_date=""
    for review in reviews():
        last_date=review[1]
        if date.strip()==review[1].strip():
            return str(review[0])
    raise Exception("No review with such date time {} {}".format(date,last_date))
    
def create_review(grade,restaurant,dish,account):
    con= sqlite3.connect('accounts.db')
    query=("INSERT INTO Reviews(date,Restaurant_id,Dish_id,grade,account_id) VALUES (?,?,?,?,?);")
    date=str(datetime.datetime.now())
    con.execute(query,(date,restaurant,dish,grade,account))
    con.commit()
    con.close()
    return get_review_id(date)

@app.route('/restaurants')
def restaurants():
    con = sqlite3.connect('accounts.db')
    cursor=con.execute("SELECT * from Restaurants")
    restaurant = []
    for row in cursor:
        restaurant.append(row)
    con.close()
    return restaurant

@app.route('/restaurants/findid/<name>', methods=['GET'])
def restaurant_find_id(name):
    for restaurant in restaurants():
        if restaurant[1]==name:
            return str(restaurant[0])
    raise Exception("No restaurant with such name, please try another restaurant name")

@app.route('/restaurants/<rest_id>/dishes', methods=['GET'])
def get_restaurant_dishes(rest_id):
    con = sqlite3.connect('accounts.db')
    query="SELECT menu from Restaurants WHERE ID=?;"
    cursor = con.execute(query, (str(rest_id),))
    dishes=[]
    for row in cursor:
        dishes.append(row)
    con.close()
    dishes_list=dishes[0][0].split(',')
    return dishes_list


@app.route('/dishes')
def dishes():
    con = sqlite3.connect('accounts.db')
    cursor=con.execute("SELECT * from Dishes")
    dish= []
    for row in cursor:
        dish.append(row)
    con.close()
    return dish

@app.route('/dishes/<Id>', methods=['GET'])
def get_dish(Id):
    con = sqlite3.connect('accounts.db')
    query="SELECT * from Dishes WHERE ID=?;"
    cursor = con.execute(query, (str(Id),))
    dish=[]
    for row in cursor:
        dish.append(row)
    con.close()
    return list(dish[0])
    

@app.route('/dishes/findid/<name>', methods=['GET'])
def dishes_find_id(name):
    for dish in dishes():
        if name==dish[1]:
            return str(dish[0])
    raise Exception("No dish with such name, please try another dish name")



@app.route('/dishes/ar/id_search/<Id>', methods=['GET'])
def get_dish_file_id(Id):
    
    for dish in dishes():
        if Id==str(dish[0]):
            file=dish[2]
            if file == None:
                return "empty"
            return file
        
    raise Exception("No dish with such id :{}, please try another id".format(Id))
            
@app.route('/dishes/ar/name_search/<name>', methods=['GET'])
def get_dish_file_name(name):
    for dish in dishes():
        if name==dish[1]:
            
            file=dish[2] 
            if file == None:
                return "empty"
            return file
    raise Exception("No dish with such name, please try another dish name")

if __name__ == '__main__':
    same_dish_review(8,2)
    app.run( host="0.0.0.0") 
    