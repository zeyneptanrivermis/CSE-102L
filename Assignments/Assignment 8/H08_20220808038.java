import java.util.*;

public class H08_20220808038 {
    public static void main(String[] args) {
    }
}

class User {
    private int id;
    private String username;
    private String email;
    private Set<User> followers;
    private Set<User> following;
    private Set<Post> likedPosts;
    private Map<User, Queue<Message>> messages;

    public User(String username, String email) {
        this.id = hashCode();
        this.username = username;
        this.email = email;
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
        this.likedPosts = new HashSet<>();
        this.messages = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void message(User recipient, String content) {
        if (!this.messages.containsKey(recipient)) {
            this.messages.put(recipient, (Queue<Message>) new LinkedList<Message>());
        }
        if (!recipient.messages.containsKey(this)) {
            recipient.messages.put(this, (Queue<Message>) new LinkedList<Message>());
        }
        Message message = new Message(this, content);
        this.messages.get(recipient).add(message);
        recipient.messages.get(this).add(message);
        this.read(recipient);
    }



    public void read(User user) {
        Queue<Message> messageQueue = messages.get(user);
        if (messageQueue != null) {
            for (Message message : messageQueue) {
                System.out.println("From: " + message.getSender().getUsername() + ", Message: " + message.read(this));
            }
        }
    }

    public void follow(User user) {
        if (!following.add(user)) {
            following.remove(user);
            user.followers.remove(this);
        } else {
            user.followers.add(this);
        }
    }

    public void like(Post post) {
        if (!likedPosts.add(post)) {
            likedPosts.remove(post);
        } else {
            post.likedBy(this);
        }
    }

    public Post post(String content) {
        Post newPost = new Post(content);
        SocialNetwork.addPost(this, newPost);
        return newPost;
    }

    public Comment comment(Post post, String content) {
        Comment newComment = new Comment(content);
        post.commentBy(this, newComment);
        return newComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class Message {
    private boolean seen;
    private Date dateSent;
    private String content;
    private User sender;

    public Message(User sender, String content) {
        this.seen = false;
        this.dateSent = new Date();
        this.content = content;
        this.sender = sender;
    }

    public String read(User reader) {
        if (!sender.equals(reader)) {
            seen = true;
        }
        return "Sent at: " + dateSent + "\n" + content;
    }

    public boolean hasRead() {
        return seen;
    }

    public User getSender() {
        return sender;
    }
}

class Post {
    private Date datePosted;
    private String content;
    private Set<User> likes;
    private Map<User, List<Comment>> comments;

    public Post(String content) {
        this.datePosted = new Date();
        this.content = content;
        this.likes = new HashSet<>();
        this.comments = new HashMap<>();
    }

    public boolean likedBy(User user) {
        if (!likes.add(user)) {
            likes.remove(user);
            return false;
        }
        return true;
    }

    public boolean commentBy(User user, Comment comment) {
        this.comments.putIfAbsent(user, (List<Comment>) new ArrayList<Comment>());
        return this.comments.get(user).add(comment);
    }

    public String getContent() {
        System.out.println("Posted at: " + datePosted);
        return content;
    }

    public Comment getComment(User user, int index) {
        List<Comment> userComments = comments.get(user);
        if (userComments != null && index < userComments.size()) {
            return userComments.get(index);
        }
        return null;
    }

    public int getCommentCount() {
        return comments.values().stream().mapToInt(List::size).sum();
    }

    public int getCommentCountByUser(User user) {
        List<Comment> userComments = comments.get(user);
        return userComments != null ? userComments.size() : 0;
    }
}

class Comment extends Post {

    public Comment(String content) {
        super(content);
    }
}

class SocialNetwork {
    private static final Map<User, List<Post>> postsByUsers = new HashMap<>();

    public static User register(String username, String email) {
        User user = new User(username, email);
        if (postsByUsers.containsKey(user)) {
            return null;
        }
        postsByUsers.put(user, (List<Post>) new ArrayList<Post>());
        return user;
    }

    public static void addPost(User user, Post post) {
        postsByUsers.get(user).add(post);
    }

    public static Post post(User user, String content) {
        if (postsByUsers.containsKey(user)) {
            Post newPost = new Post(content);
            postsByUsers.get(user).add(newPost);
            return newPost;
        }
        return null;
    }

    public static User getUser(String email) {
        for (User user : postsByUsers.keySet()) {
            if (Objects.equals(email, user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    public static Set<Post> getFeed(User user) {
        Set<Post> feed = new HashSet<>();
        for (User followed : user.getFollowing()) {
            List<Post> posts = postsByUsers.get(followed);
            if (posts != null) {
                feed.addAll(posts);
            }
        }
        return feed;
    }

    public static Map<User, String> search(String keyword) {
        Map<User, String> result = new HashMap<>();
        for (User user : postsByUsers.keySet()) {
            if (user.getUsername().contains(keyword)) {
                result.put(user, user.getUsername());
            }
        }
        return result;
    }

    public static <K, V> Map<V, Set<K>> reverseMap(Map<K, V> map) {
        Map<V, Set<K>> reversedMap = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            reversedMap.computeIfAbsent(entry.getValue(), k -> new HashSet<>()).add(entry.getKey());
        }
        return reversedMap;
    }
}
