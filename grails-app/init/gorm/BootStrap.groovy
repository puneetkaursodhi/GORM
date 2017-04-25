package gorm

class BootStrap {

    def init = { servletContext ->
        List<User> users = createUsers()
        addFriends(users)
        logFriends(users)
        removeFriends(users)
        logFriends(users)
        User user = createAndAddBook()
        log.info "---------Before remove :: User ${user.books} : ${user.books.size()}---------------"
        removingBook(user, Book.first())
//        user.refresh()
        println "----------------------------------------"
        def books = user.books
        log.info "*********After remove :: User ${books} : ${books.size()}**********"
    }

    List<User> createUsers() {
        List<User> users = []
        (1..10).each {
            User user = new User(name: "user ${it}", email: "user+${it}@gmail.com", dob: new Date() - it, balance: 1000 * it)
            if (user.save()) {
                users.add(user)
                log.info "User ${user} saved successfully"
            } else {
                log.error "Error saving user : ${user.errors.allErrors}"
            }
        }
        users
    }


    void addFriends(List<User> users) {
        log.info("-------Adding friends---------")
        users.each { User user ->
            addFriend(user)
        }
    }

    void addFriend(User user) {
        List<User> friends = User.findAllByIdNotEqual(user.id, [max: user.id])
        friends.each { User friend ->
            user.addToFriends(friend)
        }
    }

    void logFriends(List<User> users) {
        log.info("-------------Logging friends----------")
        users.each { User user ->
            log.info "User:${user.id} :: ${user.friends.size()}"
        }
    }

    void removeFriends(List<User> users) {
        log.info("----------Removing friends--------------")
        users.each { User user ->
            removeFriend(user)
        }
    }

    void removeFriend(User user) {
        User friend = user.friends.first()
        user.removeFromFriends(friend)
    }

    User createAndAddBook() {
        log.info "-------Creating user with book---------"
        User user = new User(name: "New user")
        user.addToBooks(new Book(name: "Test book"))
        user.save()
    }

    void removingBook(User user, Book book) {
        log.info "------Removing book ${book} for user ${user}"
        user.removeFromBooks(book)
        user.save(flush: true)
    }

    def destroy = {
    }
}
