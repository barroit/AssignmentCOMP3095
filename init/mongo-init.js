print("START");

db = db.getSiblingDB("user-service");

db.createUser({
	user: "barroit",
	password: "barroit",
	roles: [ { role: "readWrite", db: "product-service" } ]
});

db.createCollection("user");


print("END");
