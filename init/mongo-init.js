print("\n\n--------------------INIT DATA--------------------");

db = db.getSiblingDB("spring-social");

db.createUser({
	user: "barroit",
	pwd: "barroit",
	roles: [ { role: "readWrite", db: "spring-social" } ]
});

db.createCollection("user");
db.user.createIndex({ "username": 1 }, { unique: true });

const user_result = db.user.insertMany([
    { username: "alice", email: "alice@email.com" },
    { username: "bob", email: "bob@email.com" },
    { username: "charlie", email: "charlie@email.com" },
    { username: "fiona", email: "fiona@email.com" }
]);

print("--------------------INSERTED USERS--------------------");

const alice_id = user_result.insertedIds[0].toString();
const bob_id = user_result.insertedIds[1].toString();

db.createCollection("post");
db.posts.createIndex({ "user_id": 1 });
db.post.createIndex({ "slug": 1 }, { unique: true });

const post_result = db.post.insertMany([
    { slug: "post-1", title: "Title 1", content: "Alice's Post", user_id: alice_id },
    { slug: "post-2", title: "Title 2", content: "Bob's Post.", user_id: bob_id }
]);

print("--------------------INSERTED POSTS--------------------");

const charlie_id = user_result.insertedIds[2].toString();
const fiona_id = user_result.insertedIds[3].toString();

const alice_post_id = post_result.insertedIds[0].toString();
const bob_post_id = post_result.insertedIds[1].toString();

db.createCollection("comment");
db.comment.createIndex({ "user_id": 1, "post_id": 1 });

db.comment.insertMany([
    {
        content: "A comment by Bob on Alice's post",
        user_id: bob_id,
        post_id: alice_post_id
    },
    {
        content: "A comment by Alice on Bob's post",
        user_id: alice_id,
        post_id: bob_post_id
    },
    {
        content: "A comment by Charlie on Bob's post",
        user_id: charlie_id,
        post_id: bob_post_id
    },
    {
        content: "A comment by Fiona on Bob's post",
        user_id: fiona_id,
        post_id: bob_post_id
    }
]);

print("--------------------INSERTED COMMENTS--------------------");

print("--------------------FINISHED INIT--------------------\n\n");
